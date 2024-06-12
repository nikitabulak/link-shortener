package ru.bulak.linkshortener.repository.impl;

import org.springframework.stereotype.Repository;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.repository.LinkInfoRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoRepository {
    private final Map<String, LinkInfo> linkInfoConcurrentHashMap = new ConcurrentHashMap<>();

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
