package ru.bulak.linkshortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.FilterLinkInfoRequest;
import ru.bulak.linkshortener.dto.LinkInfoResponse;
import ru.bulak.linkshortener.dto.UpdateShortLinkRequest;
import ru.bulak.linkshortener.dto.common.CommonRequest;
import ru.bulak.linkshortener.dto.common.CommonResponse;
import ru.bulak.linkshortener.service.LinkInfoService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/link-infos")
@RequiredArgsConstructor
public class LinkInfoController {
    private final LinkInfoService linkInfoService;

    @PostMapping()
    public CommonResponse<LinkInfoResponse> postCreateShortLink(@RequestBody @Valid CommonRequest<CreateShortLinkRequest> request) {
        LinkInfoResponse linkInfoResponse = linkInfoService.createLinkInfo(request.getBody());
        return CommonResponse.<LinkInfoResponse>builder()
                .body(linkInfoResponse)
                .build();
    }

    //TODO
    @PatchMapping()
    public CommonResponse<LinkInfoResponse> patchUpdateShortLink(@RequestBody @Valid CommonRequest<UpdateShortLinkRequest> request) {
        LinkInfoResponse linkInfoResponse = linkInfoService.updateLinkInfo(request.getBody());
        return CommonResponse.<LinkInfoResponse>builder()
                .body(linkInfoResponse)
                .build();
    }

    @PostMapping("/filter")
    public CommonResponse<List<LinkInfoResponse>> filter(@RequestBody @Valid CommonRequest<FilterLinkInfoRequest> request) {
        List<LinkInfoResponse> linkInfoResponses = linkInfoService.findByFilter(request.getBody());

        return CommonResponse.<List<LinkInfoResponse>>builder()
                .body(linkInfoResponses)
                .build();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        linkInfoService.deleteById(id);
    }
}
