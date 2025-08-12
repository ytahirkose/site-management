package com.smartshopai.domain.mapper;

import com.smartshopai.domain.dto.NotificationDto;
import com.smartshopai.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    NotificationDto toDto(Notification notification);

    Notification toEntity(NotificationDto notificationDto);

    List<NotificationDto> toDtoList(List<Notification> notifications);

    List<Notification> toEntityList(List<NotificationDto> notificationDtos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(NotificationDto notificationDto, @MappingTarget Notification notification);
}
