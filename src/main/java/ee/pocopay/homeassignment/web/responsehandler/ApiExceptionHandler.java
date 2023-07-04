package ee.pocopay.homeassignment.web.responsehandler;

import ee.pocopay.homeassignment.domain.BusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ApiErrorResponse> handleInternalException(Exception e) {
        return createResponse(e, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception e) {
        String exceptionId = UUID.randomUUID().toString();
        log.error(exceptionId, e);
        return createResponse(e, "Tehniline viga " + exceptionId);
    }

    public ResponseEntity<ApiErrorResponse> createResponse(Exception e, String responseMessage) {
        String exceptionId = UUID.randomUUID().toString();
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), responseMessage);
        log.error(exceptionId, e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
