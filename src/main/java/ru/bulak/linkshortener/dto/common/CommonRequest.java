package ru.bulak.linkshortener.dto.common;

import lombok.Data;

@Data
public class CommonRequest<T> {
    private T body;
}



