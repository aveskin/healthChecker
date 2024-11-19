package ru.aveskin.healthdatamicroservice.controler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.aveskin.common.MetricType;
import ru.aveskin.healthdatamicroservice.controller.HealthDataController;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricRequestDto;
import ru.aveskin.healthdatamicroservice.entity.HealthMetric;
import ru.aveskin.healthdatamicroservice.service.HealthMetricService;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HealthDataControllerTest {
    @Mock
    private HealthMetricService healthMetricService;

    @InjectMocks
    HealthDataController controller;

    @Test
    @DisplayName("GET /api/healthmetrics/{userId} возвращает HTTP-ответ со статусом 200 OK и списком метрик")
    void getHealthMetrics_ReturnsValidResponseEntity() {
        // given
        Long userId = 1L;
        BigDecimal bdTemperature = new BigDecimal("36.3");
        HealthMetric metric1 = new HealthMetric();
        metric1.setUserId(userId);
        metric1.setId(1L);
        metric1.setMetricType(MetricType.TEMPERATURE);
        metric1.setMetricValue(bdTemperature);

        BigDecimal bdWeight = new BigDecimal("136.3");
        HealthMetric metric2 = new HealthMetric();
        metric2.setUserId(userId);
        metric2.setId(2L);
        metric2.setMetricType(MetricType.WEIGHT);
        metric2.setMetricValue(bdWeight);


        var metrics = List.of(metric1, metric2);
        doReturn(metrics).when(this.healthMetricService).getHealthMetricsByUserId(userId);

        // when
        var responseEntity = controller.getHealthMetrics(userId);

        // then
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(metrics, responseEntity.getBody());
    }

    @Test
    @DisplayName("GET /api/healthmetrics/add возвращает HTTP-ответ со статусом 201 и созданым объетом метрики")
    void addHealthMetric_PayloadIsValid_ReturnsValidResponseEntity() {
        // given
        Long userId = 1L;
        BigDecimal bdTemperature = new BigDecimal("36.3");

        final HealthMetric metric = mock(HealthMetric.class);
        final HealthMetricRequestDto requestDTO = mock(HealthMetricRequestDto.class);

        requestDTO.setUserId(userId);
        requestDTO.setMetricType(MetricType.TEMPERATURE);
        requestDTO.setMetricValue(bdTemperature);

        when(healthMetricService.addHealthMetric(requestDTO)).thenReturn(metric);

        // when
        var responseEntity = controller.addHealthMetric(requestDTO);

        // then
        Mockito.verify(healthMetricService).addHealthMetric(requestDTO);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


}
