package ru.bulak.linkshortener.repository;

import ru.bulak.linkshortener.model.LinkInfo;

import java.util.Optional;

public interface LinkInfoRepository {
    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo save(LinkInfo linkInfo);
}
