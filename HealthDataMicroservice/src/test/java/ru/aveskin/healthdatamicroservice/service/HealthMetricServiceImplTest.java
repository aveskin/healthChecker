package ru.aveskin.healthdatamicroservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import ru.aveskin.common.MetricCreatedEvent;
import ru.aveskin.common.MetricType;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricRequestDto;
import ru.aveskin.healthdatamicroservice.dto.UserContext;
import ru.aveskin.healthdatamicroservice.entity.HealthMetric;
import ru.aveskin.healthdatamicroservice.repository.HealthMetricRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HealthMetricServiceImplTest {

    @InjectMocks
    private HealthMetricServiceImpl healthMetricService;

    @Mock
    private HealthMetricRepository healthMetricRepository;

    @Mock
    private KafkaTemplate<String, MetricCreatedEvent> kafkaTemplate;

    @Mock
    private UserContext userContext;


    @Test
    void getHealthMetricsByUserId() {
        BigDecimal bdTemperature = new BigDecimal(36.3d);
        HealthMetric metric1 = new HealthMetric();
        metric1.setUserId(1L);
        metric1.setId(1L);
        metric1.setMetricType(MetricType.TEMPERATURE);
        metric1.setMetricValue(bdTemperature);

        BigDecimal bdWeight = new BigDecimal(136.3d);
        HealthMetric metric2 = new HealthMetric();
        metric2.setUserId(1L);
        metric2.setId(2L);
        metric2.setMetricType(MetricType.WEIGHT);
        metric2.setMetricValue(bdWeight);

        List<HealthMetric> expectedMetrics = Arrays.asList(metric1, metric2);

        Mockito.when(healthMetricRepository.findAllByUserId(1L)).thenReturn(expectedMetrics);
        List<HealthMetric> actualMetrics = healthMetricService.getHealthMetricsByUserId(1L);
        Assertions.assertEquals(actualMetrics, expectedMetrics);
    }

    @Test
    void addHealthMetric() {
//        HealthMetricRequestDto request = new HealthMetricRequestDto();
//        BigDecimal pulse = new BigDecimal(72);
//        request.setUserId(1L);
//        request.setMetricType(MetricType.PULSE);
//        request.setMetricValue(pulse);
//
//
//        HealthMetric savedMetric = new HealthMetric();
//        savedMetric.setId(1L);
//        savedMetric.setUserId(request.getUserId());
//        savedMetric.setMetricType(request.getMetricType());
//        savedMetric.setMetricValue(request.getMetricValue());
//        savedMetric.setTimestamp(LocalDateTime.now());
//
//
//        // Настройка поведения mock-репозитория
//        Mockito.when(healthMetricRepository.save(any(HealthMetric.class))).thenReturn(savedMetric);
//
//        // Настройка поведения mock-kafkaTemplate
//        // Создание CompletableFuture для имитации асинхронного поведения
//        CompletableFuture<SendResult<String, MetricCreatedEvent>> future = new CompletableFuture<>();
//        Mockito.when(kafkaTemplate.send(anyString(), any(MetricCreatedEvent.class))).thenReturn(future);
//
//        // Настройка поведения mock-userContext
//        Mockito.when(userContext.getEmail()).thenReturn("test@example.com");
//
//
//        // Act
//        HealthMetric actualMetric = healthMetricService.addHealthMetric(request);
//
//        // Завершение CompletableFuture, чтобы имитировать успешную отправку события
//        MetricCreatedEvent sentEvent = new MetricCreatedEvent(savedMetric.getId(), savedMetric.getUserId(),
//                userContext.getEmail(), savedMetric.getMetricType(), savedMetric.getMetricValue(), savedMetric.getTimestamp());
//        future.complete(new SendResult<>(null, null));
//
//
//        // Assert
//        Assertions.assertEquals(savedMetric, actualMetric);
//        Mockito.verify(healthMetricRepository, Mockito.times(1)).save(any(HealthMetric.class));
    }


}
