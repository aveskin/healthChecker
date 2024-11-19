package ru.aveskin.healthdatamicroservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.aveskin.common.MetricType;

import java.math.BigDecimal;

@Data
@Schema(description = "Добавление нового показателя здоровья")
public class HealthMetricUpdateRequestDto {

    @Schema(description = "id показателя задоровья", example = "1")
    @NotNull(message = "id показателя задоровья не может быть пустыми")
    private Long id;

    @Schema(description = "Значение показателя здоровья", example = "125")
    @NotNull(message = "Показатель здоровья не может быть пустыми")
    private BigDecimal metricValue;
}
