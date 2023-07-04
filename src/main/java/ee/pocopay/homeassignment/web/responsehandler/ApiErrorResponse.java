package ee.pocopay.homeassignment.web.responsehandler;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiErrorResponse(LocalDateTime timestamp, String message) {
}
