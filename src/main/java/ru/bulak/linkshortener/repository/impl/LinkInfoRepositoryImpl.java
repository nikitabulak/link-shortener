package ru.bulak.linkshortener.repository.impl;

import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.repository.LinkInfoRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LinkInfoRepositoryImpl implements LinkInfoRepository {
    private final ConcurrentHashMap<String, LinkInfo> linkInfoConcurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        return Optional.ofNullable(linkInfoConcurrentHashMap.get(shortLink));
    }

    @Override
    public LinkInfo save(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());
        linkInfoConcurrentHashMap.put(linkInfo.getShortLink(), linkInfo);
        return linkInfoConcurrentHashMap.get(linkInfo.getShortLink());
    }
}
