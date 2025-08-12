package com.smartshopai.domain.mapper;

import com.smartshopai.domain.dto.SiteDto;
import com.smartshopai.domain.entity.Site;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.IterableMapping;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteMapper {

    @Named("toDto")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "contactInfo", source = "contactInfo")
    @Mapping(target = "settings", source = "settings")
    SiteDto toDto(Site site);

    @Named("toEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "address", source = "address")
    @Mapping(target = "contactInfo", source = "contactInfo")
    @Mapping(target = "settings", source = "settings")
    Site toEntity(SiteDto siteDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<SiteDto> toDtoList(List<Site> sites);

    @IterableMapping(qualifiedByName = "toEntity")
    List<Site> toEntityList(List<SiteDto> siteDtos);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "contactInfo", ignore = true)
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SiteDto toMinimalDto(Site site);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    Site toEntityForCreation(SiteDto siteDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Site toEntityForUpdate(SiteDto siteDto);

    @Mapping(target = "address", ignore = true)
    @Mapping(target = "contactInfo", ignore = true)
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Named("toStatusDto")
    SiteDto toStatusDto(Site site);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "contactInfo", ignore = true)
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SiteDto toAddressDto(Site site);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "settings", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SiteDto toContactInfoDto(Site site);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "contactInfo", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SiteDto toSettingsDto(Site site);
}
