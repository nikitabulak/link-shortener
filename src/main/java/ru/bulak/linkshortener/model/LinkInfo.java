package ru.bulak.linkshortener.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class LinkInfo extends AuditableEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String link;
    private String shortLink;
    private ZonedDateTime endTime;
    private String description;
    private Boolean active;
    private Long openingCount;

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
