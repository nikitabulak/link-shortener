package ru.bulak.linkshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterLinkInfoRequest {
    private UUID id;
    private String linkPart;
    private ZonedDateTime endTimeFrom;
    private ZonedDateTime endTimeTo;
    private String descriptionPart;
    private Boolean active;
}
