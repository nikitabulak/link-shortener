package ru.bulak.linkshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.model.LinkInfo;
import ru.bulak.linkshortener.service.LinkInfoServiceImpl;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LinkShortenerApp {
    @Autowired
    private LinkInfoServiceImpl linkInfoService;

    @PostConstruct
    public void pc() {
        LinkInfo linkInfo = linkInfoService.createLinkInfo(new CreateShortLinkRequest());

        String shortLink = linkInfoService.getByShortLink(linkInfo.getShortLink());
        System.out.println(shortLink);
    }

    public static void main(String[] args) {
        SpringApplication.run(LinkShortenerApp.class);
    }
}
