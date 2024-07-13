package ru.bulak.linkshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.bulak.linkshortener.beanpostprocessor.LogExecutionTime;
import ru.bulak.linkshortener.dto.*;
import ru.bulak.linkshortener.exception.NotFoundException;
import ru.bulak.linkshortener.exception.NotFoundPageException;
import ru.bulak.linkshortener.mapper.LinkInfoMapper;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.property.LinkShortenerProperty;
import ru.bulak.linkshortener.repository.LinkInfoRepository;

import java.time.ZonedDateTime;
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
        LinkInfo linkInfo = linkInfoRepository.findByShortLinkAndActiveTrueAndEndTimeIsAfter(shortLink, ZonedDateTime.now())
                .orElseThrow(() -> new NotFoundPageException("Не удалось найти длинную ссылку по короткой: " + shortLink));

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
        Pageable pageable = createPageable(filterRequest);

        return linkInfoRepository.findByFilter(
                        filterRequest.getLinkPart(),
                        filterRequest.getEndTimeFrom(),
                        filterRequest.getEndTimeTo(),
                        filterRequest.getDescriptionPart(),
                        filterRequest.getActive(),
                        pageable).stream()
                .map(linkInfoMapper::toResponse)
                .toList();
    }

    private static Pageable createPageable(FilterLinkInfoRequest filterRequest) {
        PageableRequest page = filterRequest.getPage();

        List<Sort.Order> orders = page.getSorts().stream()
                .map(sort -> new Sort.Order(Sort.Direction.fromString(sort.getDirection()), sort.getField()))
                .toList();

        Sort sort = CollectionUtils.isEmpty(orders)
                ? Sort.unsorted()
                : Sort.by(orders);

        return PageRequest.of(page.getNumber() - 1, page.getSize(), sort);
    }

    @Override
    @LogExecutionTime
    public void deleteById(UUID id) {
        linkInfoRepository.deleteById(id);

    }
}
