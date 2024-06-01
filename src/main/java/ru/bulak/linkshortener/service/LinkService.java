package ru.bulak.linkshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.util.Constants;

public class LinkService {
    public String generateShortLink(CreateShortLinkRequest createShortLinkRequest) {
        return RandomStringUtils.random(Constants.SHORT_LINK_LENGTH, true, true);
    }
}
