package ru.bulak.linkshortener.service;

import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.CreateShortLinkResponse;

import java.util.List;
import java.util.UUID;

public interface LinkInfoService {
    CreateShortLinkResponse createLinkInfo(CreateShortLinkRequest createShortLinkRequest);

    String getByShortLink(String shortLink);

    List<CreateShortLinkResponse> getAllShortLinks();

    boolean deleteById(UUID id);
}
