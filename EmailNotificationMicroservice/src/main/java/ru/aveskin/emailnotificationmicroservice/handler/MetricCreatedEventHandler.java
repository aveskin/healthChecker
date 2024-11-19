package ru.aveskin.emailnotificationmicroservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import ru.aveskin.common.MetricCreatedEvent;
import ru.aveskin.common.MetricType;
import ru.aveskin.emailnotificationmicroservice.service.EmailService;

import java.math.BigDecimal;

@Component
@KafkaListener(topics = "metric-created-events-topic")
@Slf4j
@RequiredArgsConstructor
public class MetricCreatedEventHandler {
    private final EmailService emailService;

    @KafkaHandler
    public void handle(MetricCreatedEvent metricCreatedEvent) {
        String email = metricCreatedEvent.getUserEmail();

        log.info("Received event: {}", metricCreatedEvent.getMetricType());
        BigDecimal criticalTemperature = new BigDecimal(37);

        if (metricCreatedEvent.getMetricType() == MetricType.TEMPERATURE &&
                metricCreatedEvent.getMetricValue().compareTo(criticalTemperature) >= 0) {

            try {
                emailService.sendMessage(email, "High temperature", "You have to visit a doctor!");
                log.info("Email was sent to: {}", email);
            } catch (MessagingException e) {
                log.error("Error while sending out email..{}", (Object) e.getStackTrace());
            }

        }

    }

}
