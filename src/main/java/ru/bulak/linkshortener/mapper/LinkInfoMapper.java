package ru.bulak.linkshortener.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.bulak.linkshortener.dto.CreateShortLinkRequest;
import ru.bulak.linkshortener.dto.CreateShortLinkResponse;
import ru.bulak.linkshortener.model.LinkInfo;

@Mapper(componentModel = "spring")
@Component
public interface LinkInfoMapper {
    LinkInfo fromCreateRequest(CreateShortLinkRequest request);

    CreateShortLinkResponse toResponse(LinkInfo linkInfo);
}
