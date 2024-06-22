package ru.bulak.linkshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.bulak.linkshortener.beanpostprocessor.LogExecutionTime;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.exception.NotFoundException;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.repository.LinkInfoRepository;

@Service
@Primary
public class LinkInfoServiceImpl {
    @Value("${link-shortener.short-link-length}")
    private int shortLinkLength;
    @Autowired
    private LinkInfoRepository linkInfoRepository;

    @LogExecutionTime
    public LinkInfo createLinkInfo(CreateShortLinkRequest createShortLinkRequest) {
        LinkInfo linkInfo = new LinkInfo(null,
                createShortLinkRequest.getLink(),
                RandomStringUtils.randomAlphanumeric(shortLinkLength),
                createShortLinkRequest.getEndTime(),
                createShortLinkRequest.getDescription(),
                createShortLinkRequest.getActive(),
                0L);
        return linkInfoRepository.save(linkInfo);
    }

    @LogExecutionTime
    public String getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLink(shortLink)
                .orElseThrow(() -> new NotFoundException("Link not found"))
                .getShortLink();
    }
}
