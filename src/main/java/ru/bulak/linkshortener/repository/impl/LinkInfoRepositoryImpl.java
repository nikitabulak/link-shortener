package ru.bulak.linkshortener.repository.impl;

import org.springframework.stereotype.Repository;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.repository.LinkInfoRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoRepository {
    private final Map<String, LinkInfo> linkInfoConcurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        return Optional.ofNullable(linkInfoConcurrentHashMap.get(shortLink));
    }

    @Override
    public LinkInfo saveShortLink(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());
        linkInfoConcurrentHashMap.put(linkInfo.getShortLink(), linkInfo);
        return linkInfoConcurrentHashMap.get(linkInfo.getShortLink());
    }

    @Override
    public List<LinkInfo> findAll() {
        return new ArrayList<>(linkInfoConcurrentHashMap.values());
    }

    @Override
    public boolean deleteById(UUID id) {
        Optional<LinkInfo> linkInfoForDelete = linkInfoConcurrentHashMap.values().stream().filter(x -> x.getId().equals(id)).findAny();
        if (linkInfoForDelete.isEmpty()) {
            return false;
        } else {
            return linkInfoConcurrentHashMap.remove(linkInfoForDelete.get().getShortLink()) != null;
        }
    }
}
