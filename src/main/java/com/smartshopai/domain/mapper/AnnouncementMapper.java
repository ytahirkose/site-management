package com.smartshopai.domain.mapper;

import com.smartshopai.domain.dto.AnnouncementDto;
import com.smartshopai.domain.entity.Announcement;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnnouncementMapper {

    AnnouncementDto toDto(Announcement announcement);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authorName", ignore = true)
    Announcement toEntity(AnnouncementDto announcementDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authorName", ignore = true)
    void updateEntityFromDto(AnnouncementDto announcementDto, @MappingTarget Announcement announcement);

    List<AnnouncementDto> toDtoList(List<Announcement> announcements);

    @AfterMapping
    default void setComputedFields(@MappingTarget AnnouncementDto announcementDto, Announcement announcement) {
        if (announcement.getExpiryDate() != null) {
            announcementDto.setExpired(LocalDateTime.now().isAfter(announcement.getExpiryDate()));
            announcementDto.setDaysUntilExpiry(calculateDaysUntilExpiry(announcement));
        }
    }

    default boolean isExpired(Announcement announcement) {
        if (announcement.getExpiryDate() == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(announcement.getExpiryDate());
    }

    default long calculateDaysUntilExpiry(Announcement announcement) {
        if (announcement.getExpiryDate() == null) {
            return -1;
        }
        return ChronoUnit.DAYS.between(LocalDateTime.now(), announcement.getExpiryDate());
    }
}
