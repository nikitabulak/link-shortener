package ru.bulak.linkshortener.repository;

import ru.bulak.linkshortener.model.LinkInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkInfoRepository {
    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo saveShortLink(LinkInfo linkInfo);

    List<LinkInfo> findAll();

    boolean deleteById(UUID id);
}
