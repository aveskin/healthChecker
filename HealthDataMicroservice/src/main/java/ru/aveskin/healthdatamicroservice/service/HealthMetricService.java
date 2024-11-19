package ru.aveskin.healthdatamicroservice.service;

import jakarta.validation.Valid;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricDelRequestDto;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricRequestDto;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricUpdateRequestDto;
import ru.aveskin.healthdatamicroservice.entity.HealthMetric;

import java.util.List;

public interface HealthMetricService {
    List<HealthMetric> getHealthMetricsByUserId(Long userId);

    HealthMetric addHealthMetric(HealthMetricRequestDto request);

    void deleteHealthMetric(HealthMetricDelRequestDto request);

    HealthMetric getHealthMetricById(Long metricId);

    HealthMetric updateHealthMetric(HealthMetricUpdateRequestDto request);
}
