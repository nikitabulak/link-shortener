package ru.bulak.linkshortener.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.model.LinkInfo;

@Slf4j
@RequiredArgsConstructor
@Service("linkInfoServiceProxy")
public class LogExecutionTimeLinkInfoServiceProxy implements LinkInfoService {
    private final LinkInfoService linkInfoService;

    @Override
    public LinkInfo createLinkInfo(CreateShortLinkRequest createShortLinkRequest) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.createLinkInfo(createShortLinkRequest);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода createLinkInfo: {} мс", stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    public String getByShortLink(String shortLink) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.getByShortLink(shortLink);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода getByShortLink: {} мс", stopWatch.getTotalTimeMillis());
        }
    }
}
