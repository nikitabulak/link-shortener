package ru.bulak.linkshortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.service.LinkInfoService;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LinkShortenerController {
    private final LinkInfoService linkInfoService;

    @GetMapping("/short-link/{shortLink}")
    public ResponseEntity<LinkInfo> getByShortLink(@PathVariable String shortLink) {
        LinkInfo linkInfo = linkInfoService.getByShortLink(shortLink);
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .header(HttpHeaders.LOCATION, linkInfo.getLink())
                .build();
    }
}
