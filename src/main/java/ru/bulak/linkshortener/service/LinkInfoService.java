package ru.bulak.linkshortener.service;

import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.model.LinkInfo;

public interface LinkInfoService {
    LinkInfo createLinkInfo(CreateShortLinkRequest createShortLinkRequest);

    String getByShortLink(String shortLink);
}
