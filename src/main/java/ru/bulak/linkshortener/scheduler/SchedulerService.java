package ru.bulak.linkshortener.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bulak.linkshortener.service.LinkInfoService;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final LinkInfoService linkInfoService;

    @Async("elasticExecutor")
    @Scheduled(cron = "${scheduling.cron.delete-link-infos}")
    public void deleteAllNonActiveLinkInfos() {
        linkInfoService.deleteNonActiveLinkInfosByUpdateDate(false, ZonedDateTime.now().minusMonths(1));
    }
}
