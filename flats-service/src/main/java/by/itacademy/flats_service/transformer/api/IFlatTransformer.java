package by.itacademy.flats_service.transformer.api;


import by.itacademy.flats_service.core.dto.FlatDTO;
import by.itacademy.flats_service.core.dto.FlatWriteDTO;
import by.itacademy.flats_service.core.entity.Flat;

public interface IFlatTransformer {

    FlatDTO transformFlatInfoDtoFromEntity(Flat flat);

    Flat transformEntityFromFlatWriteDto(FlatWriteDTO flatInfoDto);
}
