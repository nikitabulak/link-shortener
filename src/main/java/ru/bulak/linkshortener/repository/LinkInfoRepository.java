package ru.bulak.linkshortener.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bulak.linkshortener.model.LinkInfo;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkInfoRepository extends JpaRepository<LinkInfo, UUID> {

    @Query("""
            FROM LinkInfo li
            WHERE (:linkPart IS NULL OR li.link LIKE '%' || :linkPart || '%')
            AND (cast(:endTimeFrom as date) IS NULL OR li.endTime >= :endTimeFrom)
            AND (cast(:endTimeTo as date) IS NULL OR li.endTime >= :endTimeTo)
            AND (:descriptionPart IS NULL OR li.description LIKE '%' || :descriptionPart || '%')
            AND (:active IS NULL OR li.active = :active)
            """)
    Page<LinkInfo> findByFilter(String linkPart,
                                ZonedDateTime endTimeFrom,
                                ZonedDateTime endTimeTo,
                                String descriptionPart,
                                Boolean active,
                                Pageable pageable);

    Optional<LinkInfo> findByShortLinkAndActiveTrueAndEndTimeIsAfter(String shortLink, ZonedDateTime endTime);

    Optional<LinkInfo> findByIdAndActiveTrue(UUID id);
    void deleteAllByActiveIsAndUpdateTimeBefore(boolean active, ZonedDateTime dateTime);

    @Query("""
            UPDATE LinkInfo li
            SET li.openingCount = li.openingCount + 1
            WHERE li.shortLink = :shortLink
                        """)
    @Modifying
    @Transactional
    void incrementOpeningCountByShortLink(String shortLink);
}
