package ru.aveskin.common;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MetricCreatedEvent {

    private Long id;

    private Long userId;

    private String userEmail;

    private MetricType metricType;

    private BigDecimal metricValue;

    private LocalDateTime timestamp;

    public MetricCreatedEvent() {
    }

    public MetricCreatedEvent(Long id, Long userId, String userEmail, MetricType metricType, BigDecimal metricValue, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.metricType = metricType;
        this.metricValue = metricValue;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public BigDecimal getMetricValue() {
        return metricValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public void setMetricValue(BigDecimal metricValue) {
        this.metricValue = metricValue;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
