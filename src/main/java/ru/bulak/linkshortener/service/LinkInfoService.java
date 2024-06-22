package ru.bulak.linkshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.bulak.linkshortener.beanpostprocessor.LogExecutionTime;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.CreateShortLinkResponse;
import ru.bulak.linkshortener.exception.NotFoundException;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.property.LinkShortenerProperty;
import ru.bulak.linkshortener.repository.LinkInfoRepository;

@Service
@Primary
public class LinkInfoService {
    @Autowired
    private LinkInfoRepository linkInfoRepository;
    @Autowired
    LinkShortenerProperty linkShortenerProperty;

    @LogExecutionTime
    public CreateShortLinkResponse createLinkInfo(CreateShortLinkRequest createShortLinkRequest) {
        LinkInfo linkInfo = new LinkInfo(null,
                createShortLinkRequest.getLink(),
                RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength()),
                createShortLinkRequest.getEndTime(),
                createShortLinkRequest.getDescription(),
                createShortLinkRequest.getActive(),
                0L);

        linkInfoRepository.save(linkInfo);

        return CreateShortLinkResponse.builder()
                .id(linkInfo.getId())
                .link(linkInfo.getLink())
                .shortLink(linkInfo.getShortLink())
                .endTime(linkInfo.getEndTime())
                .description(linkInfo.getDescription())
                .active(linkInfo.getActive())
                .build();
    }

    @LogExecutionTime
    public String getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLink(shortLink)
                .orElseThrow(() -> new NotFoundException("Link not found"))
                .getLink();
    }
}
