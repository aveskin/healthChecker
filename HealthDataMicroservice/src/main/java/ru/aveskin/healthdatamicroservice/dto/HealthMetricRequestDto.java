package ru.aveskin.healthdatamicroservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.aveskin.common.MetricType;

import java.math.BigDecimal;

@Data
@Schema(description = "Добавление нового показателя здоровья")
public class HealthMetricRequestDto {

    @Schema(description = "id пользователя", example = "1")
    @NotNull(message = "id пользователя не может быть пустыми")
    private Long userId;

    @Schema(description = "Показатель здоровья", example = "WEIGHT," +
            "    BLOOD_PRESSURE_LOW," +
            "    BLOOD_PRESSURE_HIGH," +
            "    PULSE," +
            "    TEMPERATURE")
    @NotNull(message = "Показатель здоровья не может быть пустыми")
    private MetricType metricType;

    @Schema(description = "Значение показателя здоровья", example = "125")
    @NotNull(message = "Показатель здоровья не может быть пустыми")
    private BigDecimal metricValue;
}
