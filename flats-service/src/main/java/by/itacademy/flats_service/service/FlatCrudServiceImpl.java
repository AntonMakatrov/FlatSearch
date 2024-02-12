package by.itacademy.flats_service.service;

import by.itacademy.flats_service.core.dto.FlatFilter;
import by.itacademy.flats_service.core.dto.FlatDTO;
import by.itacademy.flats_service.core.dto.FlatWriteDTO;
import by.itacademy.flats_service.core.entity.Flat;
import by.itacademy.flats_service.repository.FlatRepository;
import by.itacademy.flats_service.service.api.FlatCrudService;
import by.itacademy.flats_service.transformer.api.IFlatTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FlatCrudServiceImpl implements FlatCrudService {

    private final FlatRepository flatRepository;
    private final IFlatTransformer flatTransformer;

    public FlatCrudServiceImpl(
            FlatRepository flatRepository,
            IFlatTransformer flatTransformer
    ) {
        this.flatRepository = flatRepository;
        this.flatTransformer = flatTransformer;
    }

    @Override
    public void createFlat(FlatWriteDTO flatWriteDto) {
        flatRepository.save(flatTransformer.transformEntityFromFlatWriteDto(flatWriteDto));
    }

    @Override
    public Page<FlatDTO> getAllFlats(FlatFilter flatFilter, Pageable pageable) {
        Page<Flat> pageEntity = flatRepository.getFlatsWithFilters(flatFilter, pageable);
        List<FlatDTO> auditDtoList = pageEntity.stream()
                .map(flatTransformer::transformFlatInfoDtoFromEntity)
                .toList();
        return new PageImpl<FlatDTO>(auditDtoList, pageable, pageEntity.getTotalElements());
    }
}
