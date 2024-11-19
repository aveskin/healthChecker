package ru.aveskin.healthdatamicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.aveskin.healthdatamicroservice.entity.HealthMetric;

import java.util.List;

public interface HealthMetricRepository extends CrudRepository<HealthMetric, Long> {

    List<HealthMetric> findAllByUserId(Long userId);
}
