package ru.bulak.linkshortener.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bulak.linkshortener.dto.common.CommonResponse;
import ru.bulak.linkshortener.dto.common.ValidationError;
import ru.bulak.linkshortener.exception.NotFoundException;
import ru.bulak.linkshortener.exception.NotFoundPageException;

import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class LinkShortenerExceptionHandler {

    private final String notFoundPage;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ValidationError> validationErrors = fieldErrors.stream()
                .map(fieldError -> ValidationError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .toList();

        log.error("Ошибка валидации запрова: {}", validationErrors, e);

        return CommonResponse.builder()
                .errorMessage("Ошибка валидации")
                .validationErrors(validationErrors)
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleInvalidFormatException(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException ife) {
            String errMessage = "Ошибка валидации, указан некорректный формат для следующего значения: " + ife.getValue();

            log.error(errMessage, e);

            return CommonResponse.builder()
                    .errorMessage(errMessage)
                    .build();
        }

        return handleException(e);
    }

    @ExceptionHandler(NotFoundPageException.class)
    public ResponseEntity<String> handleNotFoundPageException(NotFoundException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_HTML)
                .body(notFoundPage);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e) {
        log.error("Непредвиденная ошибка: {}", e.getMessage(), e);

        return CommonResponse.builder()
                .errorMessage("Непредвиденная ошибка: " + e.getMessage())
                .build();
    }
}