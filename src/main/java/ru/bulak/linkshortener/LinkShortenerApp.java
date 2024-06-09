package ru.bulak.linkshortener;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.service.LinkInfoService;

import java.time.ZonedDateTime;

@SpringBootApplication
public class LinkShortenerApp {
    public static void main(String[] args) {
        LinkInfoService linkInfoService = new LinkInfoService();
        CreateShortLinkRequest shortLinkRequest = new CreateShortLinkRequest(
                RandomStringUtils.randomAlphabetic(15),
                ZonedDateTime.now().plusHours(65),
                "Test description",
                true
        );
        LinkInfo linkInfo = linkInfoService.createLinkInfo(shortLinkRequest);
        System.out.println(linkInfoService.getByShortLink(linkInfo.getShortLink()).equals(linkInfo.getLink()));

        SpringApplication.run(LinkShortenerApp.class);
    }
}
