package ru.bulak.linkshortener.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkInfo {
    private UUID id;
    private String link;
    private String shortLink;
    private ZonedDateTime endTime;
    private String description;
    private Boolean active;
    private long openingCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkInfo linkInfo = (LinkInfo) o;
        return Objects.equals(id, linkInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
