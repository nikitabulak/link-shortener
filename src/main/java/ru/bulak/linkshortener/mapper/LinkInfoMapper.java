package ru.bulak.linkshortener.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.LinkInfoResponse;
import ru.bulak.linkshortener.dto.UpdateShortLinkRequest;
import ru.bulak.linkshortener.model.LinkInfo;

@Mapper(componentModel = "spring")
@Component
public interface LinkInfoMapper {
    LinkInfo fromCreateRequest(CreateShortLinkRequest request);

    LinkInfo fromUpdateRequest(UpdateShortLinkRequest request);

    LinkInfoResponse toResponse(LinkInfo linkInfo);
}
