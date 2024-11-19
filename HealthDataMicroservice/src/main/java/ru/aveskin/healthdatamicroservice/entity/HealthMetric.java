package ru.aveskin.healthdatamicroservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.aveskin.common.MetricType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "metrics_schema", name = "t_health_metric")
@Getter
@Setter
public class HealthMetric implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "metric_type", nullable = false)
    private MetricType metricType;

    @Column(name = "metric_value", nullable = false)
    private BigDecimal metricValue;

    @Column(name = "date_time",nullable = false)
    private LocalDateTime timestamp;
}