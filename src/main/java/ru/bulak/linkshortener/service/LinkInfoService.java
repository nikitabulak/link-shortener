package ru.bulak.linkshortener.service;

import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.FilterLinkInfoRequest;
import ru.bulak.linkshortener.dto.LinkInfoResponse;
import ru.bulak.linkshortener.dto.UpdateShortLinkRequest;
import ru.bulak.linkshortener.model.LinkInfo;

import java.util.List;
import java.util.UUID;

public interface LinkInfoService {
    LinkInfoResponse createLinkInfo(CreateShortLinkRequest createShortLinkRequest);

    LinkInfo getByShortLink(String shortLink);

    List<LinkInfoResponse> getAllShortLinks();

    void deleteById(UUID id);

    List<LinkInfoResponse> findByFilter(FilterLinkInfoRequest filterLinkInfoRequest);

    LinkInfoResponse updateLinkInfo(UpdateShortLinkRequest updateShortLinkRequest);
}
