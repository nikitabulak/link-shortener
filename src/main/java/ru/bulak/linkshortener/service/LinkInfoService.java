package ru.bulak.linkshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.exception.NotFoundException;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.repository.LinkInfoRepository;
import ru.bulak.linkshortener.repository.impl.LinkInfoRepositoryImpl;
import ru.bulak.linkshortener.util.Constants;

public class LinkInfoService {
    private final LinkInfoRepository linkInfoRepository = new LinkInfoRepositoryImpl();

    public LinkInfo createLinkInfo(CreateShortLinkRequest createShortLinkRequest) {
        LinkInfo linkInfo = new LinkInfo(null,
                createShortLinkRequest.getLink(),
                RandomStringUtils.randomAlphanumeric(Constants.SHORT_LINK_LENGTH),
                createShortLinkRequest.getEndTime(),
                createShortLinkRequest.getDescription(),
                createShortLinkRequest.getActive(),
                0L);
        return linkInfoRepository.save(linkInfo);
    }

    public String getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLink(shortLink).orElseThrow(() -> new NotFoundException("Link not found"))
                .getLink();
    }
}
