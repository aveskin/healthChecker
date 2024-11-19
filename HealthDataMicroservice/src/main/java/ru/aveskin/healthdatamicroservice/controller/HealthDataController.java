package ru.aveskin.healthdatamicroservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricDelRequestDto;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricRequestDto;
import ru.aveskin.healthdatamicroservice.dto.HealthMetricUpdateRequestDto;
import ru.aveskin.healthdatamicroservice.entity.HealthMetric;
import ru.aveskin.healthdatamicroservice.service.HealthMetricService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/healthmetrics")
@RequiredArgsConstructor
@Tag(name = "Сервис показателей здоровья")
@Slf4j
public class HealthDataController {
    private final HealthMetricService healthMetricService;


    @Operation(summary = "Получает историю всех показателей здоровья для конкретного пользователя")
    @GetMapping("metrics/{userId}")
    public ResponseEntity<List<HealthMetric>> getHealthMetrics(@PathVariable Long userId) {
        var healthMetrics = healthMetricService.getHealthMetricsByUserId(userId);
        return new ResponseEntity<>(healthMetrics, HttpStatus.OK);
    }

    @Operation(summary = "Получает показатель здоровья по id показателя")
    @GetMapping("metric/{metricId}")
    public ResponseEntity<HealthMetric> getHealthMetricById(@PathVariable Long metricId) {
        HealthMetric healthMetric = healthMetricService.getHealthMetricById(metricId);
        return new ResponseEntity<>(healthMetric, HttpStatus.OK);
    }


    @Operation(summary = "Добавляет новый показатель здоровья для пользователя")
    @PostMapping("/add")
    public ResponseEntity<HealthMetric> addHealthMetric(@RequestBody @Valid HealthMetricRequestDto request) {
        HealthMetric createdMetric = healthMetricService.addHealthMetric(request);
        log.info("показатель с id= "+ createdMetric.getId() + "добавлен: " + LocalDateTime.now() );
        return new ResponseEntity<>(createdMetric, HttpStatus.CREATED);
    }

    @Operation(summary = "Изменяет показатель здоровья для пользователя по id показателя")
    @DeleteMapping("/update")
    public ResponseEntity<HealthMetric> updateHealthMetric(@RequestBody @Valid HealthMetricUpdateRequestDto request) {
        HealthMetric updatedMetric = healthMetricService.updateHealthMetric(request);
        log.info("показатель с id= "+ request.getId() + "изменен: " + LocalDateTime.now() );
        return new ResponseEntity<>(updatedMetric, HttpStatus.OK);
    }

    @Operation(summary = "Удаляет показатель здоровья для пользователя по id показателя")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteHealthMetric(@RequestBody @Valid HealthMetricDelRequestDto request) {
        healthMetricService.deleteHealthMetric(request);
        log.info("показатель с id= "+ request.getId() + "удален: " + LocalDateTime.now() );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
