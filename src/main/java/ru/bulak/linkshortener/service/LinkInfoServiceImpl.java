package ru.bulak.linkshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.bulak.linkshortener.beanpostprocessor.LogExecutionTime;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.CreateShortLinkResponse;
import ru.bulak.linkshortener.exception.NotFoundException;
import ru.bulak.linkshortener.mapper.LinkInfoMapper;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.property.LinkShortenerProperty;
import ru.bulak.linkshortener.repository.LinkInfoRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
public class LinkInfoServiceImpl implements LinkInfoService {
    @Autowired
    private LinkInfoRepository linkInfoRepository;
    @Autowired
    private LinkInfoMapper linkInfoMapper;
    @Autowired
    LinkShortenerProperty linkShortenerProperty;

    @Override
    @LogExecutionTime
    public CreateShortLinkResponse createLinkInfo(CreateShortLinkRequest createShortLinkRequest) {
        LinkInfo linkInfo = linkInfoMapper.fromCreateRequest(createShortLinkRequest);
        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength()));
        linkInfo.setOpeningCount(0L);

        linkInfoRepository.saveShortLink(linkInfo);

        return linkInfoMapper.toResponse(linkInfo);
    }

    @Override
    @LogExecutionTime
    public String getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLink(shortLink)
                .orElseThrow(() -> new NotFoundException("Link not found"))
                .getLink();
    }

    @Override
    @LogExecutionTime
    public List<CreateShortLinkResponse> getAllShortLinks() {
        return linkInfoRepository.findAll().stream()
                .map(linkInfoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @LogExecutionTime
    public boolean deleteById(UUID id) {
        return linkInfoRepository.deleteById(id);
    }
}
