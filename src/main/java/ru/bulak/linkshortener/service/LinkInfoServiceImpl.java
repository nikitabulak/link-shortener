package ru.bulak.linkshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.bulak.linkshortener.beanpostprocessor.LogExecutionTime;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.FilterLinkInfoRequest;
import ru.bulak.linkshortener.dto.LinkInfoResponse;
import ru.bulak.linkshortener.dto.UpdateShortLinkRequest;
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
    public LinkInfoResponse createLinkInfo(CreateShortLinkRequest createShortLinkRequest) {
        LinkInfo linkInfo = linkInfoMapper.fromCreateRequest(createShortLinkRequest);
        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength()));
        linkInfo.setOpeningCount(0L);

        linkInfoRepository.save(linkInfo);

        return linkInfoMapper.toResponse(linkInfo);
    }

    @Override
    public LinkInfoResponse updateLinkInfo(UpdateShortLinkRequest updateShortLinkRequest) {
        LinkInfo existingLinkInfo = linkInfoRepository.findByIdAndActiveTrue(updateShortLinkRequest.getId())
                .orElseThrow(() -> new NotFoundException("Link not found"));
        LinkInfo newLinkInfo = linkInfoMapper.fromUpdateRequest(updateShortLinkRequest);

        existingLinkInfo.setLink(newLinkInfo.getLink());
        existingLinkInfo.setEndTime(newLinkInfo.getEndTime());
        existingLinkInfo.setDescription(newLinkInfo.getDescription());
        existingLinkInfo.setActive(newLinkInfo.getActive());
        existingLinkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength()));
        existingLinkInfo.setOpeningCount(0L);

        linkInfoRepository.save(existingLinkInfo);

        return linkInfoMapper.toResponse(existingLinkInfo);
    }

    @Override
    @LogExecutionTime
    public LinkInfo getByShortLink(String shortLink) {
        LinkInfo linkInfo = linkInfoRepository.findByShortLinkAndActiveTrue(shortLink)
                .orElseThrow(() -> new NotFoundException("Link not found"));

        linkInfoRepository.incrementOpeningCountByShortLink(shortLink);

        return linkInfo;
    }

    @Override
    @LogExecutionTime
    public List<LinkInfoResponse> getAllShortLinks() {
        return linkInfoRepository.findAll().stream()
                .map(linkInfoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LinkInfoResponse> findByFilter(FilterLinkInfoRequest filterRequest) {
        return linkInfoRepository.findByFilter(
                        filterRequest.getLinkPart(),
                        filterRequest.getEndTimeFrom(),
                        filterRequest.getEndTimeTo(),
                        filterRequest.getDescriptionPart(),
                        filterRequest.getActive()).stream()
                .map(linkInfoMapper::toResponse)
                .toList();
    }

    @Override
    @LogExecutionTime
    public void deleteById(UUID id) {
        linkInfoRepository.deleteById(id);

    }
}
