package ru.bulak.linkshortener.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkInfoResponse {
    private UUID id;
    private String link;
    private ZonedDateTime endTime;
    private String description;
    private Boolean active;
    private String shortLink;
    private Long openingCount;
}
