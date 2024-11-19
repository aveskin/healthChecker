package ru.aveskin.healthdatamicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aveskin.common.MetricCreatedEvent;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricDelRequestDto;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricRequestDto;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricUpdateRequestDto;
import ru.aveskin.healthdatamicroservice.dto.UserContext;
import ru.aveskin.healthdatamicroservice.entity.HealthMetric;
import ru.aveskin.healthdatamicroservice.repository.HealthMetricRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthMetricServiceImpl implements HealthMetricService {
    private final HealthMetricRepository healthMetricRepository;
    private final KafkaTemplate<String, MetricCreatedEvent> kafkaTemplate;
    private final UserContext userContext;

    @Override
    @Transactional(readOnly = true)
    public List<HealthMetric> getHealthMetricsByUserId(Long userId) {
        return healthMetricRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public HealthMetric addHealthMetric(HealthMetricRequestDto request) {

        HealthMetric healthMetricNew = new HealthMetric();
        healthMetricNew.setUserId(request.getUserId());
        healthMetricNew.setMetricType(request.getMetricType());
        healthMetricNew.setMetricValue(request.getMetricValue());
        healthMetricNew.setTimestamp(LocalDateTime.now());

        HealthMetric healthMetric = healthMetricRepository.save(healthMetricNew);

        String metricId = healthMetric.getId().toString();

        MetricCreatedEvent metricCreatedEvent =
                new MetricCreatedEvent(
                        healthMetric.getId(),
                        healthMetric.getUserId(),
                        userContext.getEmail(),
                        healthMetric.getMetricType(),
                        healthMetric.getMetricValue(),
                        healthMetric.getTimestamp());

        //При асинхронной обрабоотке
        CompletableFuture<SendResult<String, MetricCreatedEvent>> future =
                kafkaTemplate.send("metric-created-events-topic", metricId, metricCreatedEvent);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to send message: {}", exception.getMessage());
            } else {
                log.info("Message sent successfully: {}", result.getRecordMetadata());
            }
        });

        return healthMetric;
    }

    @Override
    @Transactional
    @CacheEvict(value = "healthMetrics::getHealthMetricById", key = "#request.id")
    public void deleteHealthMetric(HealthMetricDelRequestDto request) {
        Optional<HealthMetric> healthMetric = Optional.ofNullable(healthMetricRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("metric is not found")));

    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "HealthMetricService::getHealthMetricById", key = "#metricId")
    public HealthMetric getHealthMetricById(Long metricId) {
        Optional<HealthMetric> healthMetric = healthMetricRepository.findById(metricId);
        return healthMetric.orElseThrow(() -> new ResourceNotFoundException("metric is not found"));
    }

    @Override
    @Transactional
    @CachePut(value = "healthMetrics::getHealthMetricById", key = "#request.id")
    public HealthMetric updateHealthMetric(HealthMetricUpdateRequestDto request) {
        Optional<HealthMetric> healthMetric = healthMetricRepository.findById(request.getId());
        if (healthMetric.isEmpty()) {
            throw new ResourceNotFoundException("metric is not found");
        }
        HealthMetric updatedMetric = healthMetric.get();
        updatedMetric.setMetricValue(request.getMetricValue());
        healthMetricRepository.save(updatedMetric);

        return updatedMetric;
    }
}
