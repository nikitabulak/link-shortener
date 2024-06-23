package ru.bulak.linkshortener.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationError {

    private String field;
    private String message;
}
