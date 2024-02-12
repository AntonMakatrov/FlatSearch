package by.itacademy.flats_service.service.api;

import by.itacademy.flats_service.core.dto.FlatFilter;
import by.itacademy.flats_service.core.dto.FlatDTO;
import by.itacademy.flats_service.core.dto.FlatWriteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlatCrudService {

    void createFlat(FlatWriteDTO flatWriteDto);

    Page<FlatDTO> getAllFlats(FlatFilter flatFilter, Pageable pageable);
}
