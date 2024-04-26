package palaczjustyna.library.bookOrder.domain;

import java.time.LocalDateTime;

public record SummaryOrderDTO(Integer id, LocalDateTime date,  String status,  String paymentType, Integer clientId) {
}
