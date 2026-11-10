package com.jiltsa.admin.promotions.persistence.mapper;

import com.jiltsa.admin.promotions.domain.dto.RecordingLossExpirationDto;
import com.jiltsa.admin.promotions.persistence.entity.RecordingLossExpiration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecordingLossExpirationMapper {
    RecordingLossExpirationDto toRecordingLossExpirationDto(RecordingLossExpiration record);
    List<RecordingLossExpirationDto> toRecordingLossExpirationDtoList(List<RecordingLossExpiration> records);
}
