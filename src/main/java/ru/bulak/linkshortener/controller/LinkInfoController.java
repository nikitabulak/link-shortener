package ru.bulak.linkshortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.CreateShortLinkResponse;
import ru.bulak.linkshortener.dto.common.CommonRequest;
import ru.bulak.linkshortener.dto.common.CommonResponse;
import ru.bulak.linkshortener.service.LinkInfoService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/link-infos")
@RequiredArgsConstructor
public class LinkInfoController {
    private final LinkInfoService linkInfoService;

    @PostMapping()
    public CommonResponse<CreateShortLinkResponse> postCreateShortLink(@RequestBody @Valid CommonRequest<CreateShortLinkRequest> request) {
        log.info("Поступил запрос на создание короткой ссылки: {}", request);

        CreateShortLinkResponse createShortLinkResponse = linkInfoService.createLinkInfo(request.getBody());

        return CommonResponse.<CreateShortLinkResponse>builder()
                .body(createShortLinkResponse)
                .build();
    }

    @GetMapping
    public List<CommonResponse<CreateShortLinkResponse>> getAllShortLinks() {
        log.info("Поступил запрос на получение всех коротких ссылок");

        List<CreateShortLinkResponse> linkInfoResponses = linkInfoService.getAllShortLinks();
        return linkInfoResponses.stream()
                .map(x -> CommonResponse.<CreateShortLinkResponse>builder()
                        .body(x)
                        .build())
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable UUID id) {
        log.info("Поступил запрос на удаление короткой ссылки по id: {}", id);

        return linkInfoService.deleteById(id);

    }
}
