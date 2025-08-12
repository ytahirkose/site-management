package com.smartshopai.domain.mapper;

import com.smartshopai.domain.dto.SiteDto;
import com.smartshopai.domain.entity.Site;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SiteMapperTest {

    private final SiteMapper mapper = Mappers.getMapper(SiteMapper.class);

    @Test
    void testToDto() {
        // Given
        Site site = Site.builder()
                .id("site1")
                .name("Test Site")
                .code("TEST001")
                .description("Test Description")
                .status(Site.SiteStatus.ACTIVE)
                .createdBy("admin1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        SiteDto dto = mapper.toDto(site);

        // Then
        assertNotNull(dto);
        assertEquals(site.getId(), dto.getId());
        assertEquals(site.getName(), dto.getName());
        assertEquals(site.getCode(), dto.getCode());
        assertEquals(site.getDescription(), dto.getDescription());
        assertEquals(site.getStatus().toString(), dto.getStatus().toString());
        assertEquals(site.getCreatedBy(), dto.getCreatedBy());
    }

    @Test
    void testToEntity() {
        // Given
        SiteDto dto = SiteDto.builder()
                .name("Test Site")
                .code("TEST001")
                .description("Test Description")
                .status(Site.SiteStatus.ACTIVE)
                .build();

        // When
        Site entity = mapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getStatus(), entity.getStatus());
    }

    @Test
    void testToDtoList() {
        // Given
        List<Site> sites = Arrays.asList(
                Site.builder().id("site1").name("Site 1").build(),
                Site.builder().id("site2").name("Site 2").build()
        );

        // When
        List<SiteDto> dtos = mapper.toDtoList(sites);

        // Then
        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("site1", dtos.get(0).getId());
        assertEquals("site2", dtos.get(1).getId());
    }

    @Test
    void testToEntityList() {
        // Given
        List<SiteDto> dtos = Arrays.asList(
                SiteDto.builder().name("Site 1").build(),
                SiteDto.builder().name("Site 2").build()
        );

        // When
        List<Site> entities = mapper.toEntityList(dtos);

        // Then
        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals("Site 1", entities.get(0).getName());
        assertEquals("Site 2", entities.get(1).getName());
    }

    @Test
    void testToMinimalDto() {
        // Given
        Site site = Site.builder()
                .id("site1")
                .name("Test Site")
                .code("TEST001")
                .description("Test Description")
                .status(Site.SiteStatus.ACTIVE)
                .createdBy("admin1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        SiteDto dto = mapper.toMinimalDto(site);

        // Then
        assertNotNull(dto);
        assertEquals(site.getId(), dto.getId());
        assertEquals(site.getName(), dto.getName());
        assertEquals(site.getCode(), dto.getCode());
        assertEquals(site.getDescription(), dto.getDescription());
        assertEquals(site.getStatus().toString(), dto.getStatus().toString());
        // Address, contactInfo, settings should be ignored
        assertNull(dto.getAddress());
        assertNull(dto.getContactInfo());
        assertNull(dto.getSettings());
    }

    @Test
    void testToEntityForCreation() {
        // Given
        SiteDto dto = SiteDto.builder()
                .name("Test Site")
                .code("TEST001")
                .description("Test Description")
                .status(Site.SiteStatus.INACTIVE)
                .build();

        // When
        Site entity = mapper.toEntityForCreation(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getDescription(), entity.getDescription());
        // Status should be set to ACTIVE by constant mapping
        assertEquals(Site.SiteStatus.ACTIVE, entity.getStatus());
        // ID, createdAt, updatedAt, deletedAt should be ignored
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
        assertNull(entity.getDeletedAt());
    }

    @Test
    void testToEntityForUpdate() {
        // Given
        SiteDto dto = SiteDto.builder()
                .name("Updated Site")
                .code("UPD001")
                .description("Updated Description")
                .build();

        // When
        Site entity = mapper.toEntityForUpdate(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getDescription(), entity.getDescription());
        // ID, createdAt, createdBy, deletedAt should be ignored
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getCreatedBy());
        assertNull(entity.getDeletedAt());
    }

    @Test
    void testToStatusDto() {
        // Given
        Site site = Site.builder()
                .id("site1")
                .name("Test Site")
                .code("TEST001")
                .description("Test Description")
                .status(Site.SiteStatus.ACTIVE)
                .createdBy("admin1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        SiteDto dto = mapper.toStatusDto(site);

        // Then
        assertNotNull(dto);
        assertEquals(site.getId(), dto.getId());
        assertEquals(site.getName(), dto.getName());
        assertEquals(site.getCode(), dto.getCode());
        assertEquals(site.getDescription(), dto.getDescription());
        assertEquals(site.getStatus().toString(), dto.getStatus().toString());
        // Address, contactInfo, settings, createdBy, createdAt, updatedAt should be ignored
        assertNull(dto.getAddress());
        assertNull(dto.getContactInfo());
        assertNull(dto.getSettings());
        assertNull(dto.getCreatedBy());
        assertNull(dto.getCreatedAt());
        assertNull(dto.getUpdatedAt());
    }
}
