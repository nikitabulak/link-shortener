package ru.bulak.linkshortener.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class SortRequest {
    @NotEmpty
    private String field;
    @Pattern(regexp = "ASC|DESC")
    private String direction = "ASC";
}
