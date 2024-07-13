package ru.bulak.linkshortener.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public class AuditableEntity {
    private ZonedDateTime createTime;
    private ZonedDateTime updateTime;

    @PrePersist
    public void prePersist() {
        this.createTime = ZonedDateTime.now();
        this.updateTime = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = ZonedDateTime.now();
    }

}
