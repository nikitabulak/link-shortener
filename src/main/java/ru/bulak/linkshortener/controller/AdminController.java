package ru.bulak.linkshortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.CreateShortLinkResponse;
import ru.bulak.linkshortener.dto.common.CommonRequest;
import ru.bulak.linkshortener.dto.common.CommonResponse;
import ru.bulak.linkshortener.service.LinkInfoService;

@Slf4j
@RestController
@RequestMapping("/api/v1/link-infos")
@RequiredArgsConstructor
public class AdminController {
    private final LinkInfoService linkInfoService;

    @PostMapping()
    public CommonResponse<CreateShortLinkResponse> postCreateShortLink(@RequestBody CommonRequest<CreateShortLinkRequest> request) {
        log.info("Поступил запрос на создание короткой ссылки: {}", request);

        CreateShortLinkResponse createShortLinkResponse = linkInfoService.createLinkInfo(request.getBody());

        return CommonResponse.<CreateShortLinkResponse>builder()
                .body(createShortLinkResponse)
                .build();
    }
}
