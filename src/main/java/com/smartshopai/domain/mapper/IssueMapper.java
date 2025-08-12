package com.smartshopai.domain.mapper;

import com.smartshopai.domain.dto.IssueDto;
import com.smartshopai.domain.entity.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IssueMapper {

    IssueDto toDto(Issue issue);

    Issue toEntity(IssueDto issueDto);

    List<IssueDto> toDtoList(List<Issue> issues);

    List<Issue> toEntityList(List<IssueDto> issueDtos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(IssueDto issueDto, @MappingTarget Issue issue);
}
