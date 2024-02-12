package by.itacademy.flats_service.transformer;

import by.itacademy.flats_service.core.dto.FlatDTO;
import by.itacademy.flats_service.core.dto.FlatWriteDTO;
import by.itacademy.flats_service.core.entity.Flat;
import by.itacademy.flats_service.transformer.api.IFlatTransformer;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class FlatTransformer implements IFlatTransformer {

    @Override
    public FlatDTO transformFlatInfoDtoFromEntity(Flat flat) {
        return new FlatDTO().setId(flat.getId())
                .setCreationDate(flat.getCreationDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .setUpdatedDate(flat.getUpdateDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .setOfferType(flat.getOfferType())
                .setDescription(flat.getDescription())
                .setBedrooms(flat.getBedrooms())
                .setArea(flat.getArea())
                .setPrice(flat.getPrice())
                .setFloor(flat.getFloor())
                .setPhotoUrls(flat.getPhotoUrls())
                .setOriginalUrl(flat.getOriginalUrl());
    }

    @Override
    public Flat transformEntityFromFlatWriteDto(FlatWriteDTO flatWriteDto) {
        return new Flat().setOfferType(flatWriteDto.getOfferType())
                .setDescription(flatWriteDto.getDescription())
                .setBedrooms(flatWriteDto.getBedrooms())
                .setArea(flatWriteDto.getArea())
                .setPrice(flatWriteDto.getPrice())
                .setFloor(flatWriteDto.getFloor())
                .setPhotoUrls(flatWriteDto.getPhotoUrls())
                .setOriginalUrl(flatWriteDto.getOriginalUrl());
    }
}
