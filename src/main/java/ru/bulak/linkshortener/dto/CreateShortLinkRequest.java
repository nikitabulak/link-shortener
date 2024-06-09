package ru.bulak.linkshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateShortLinkRequest {
    private String link;
    private ZonedDateTime endTime;
    private String description;
    private Boolean active;
}
