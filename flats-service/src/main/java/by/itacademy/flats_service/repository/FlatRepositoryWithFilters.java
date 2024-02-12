package by.itacademy.flats_service.repository;

import by.itacademy.flats_service.core.dto.FlatFilter;
import by.itacademy.flats_service.core.entity.Flat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepositoryWithFilters {

    Page<Flat> getFlatsWithFilters(Pageable pageable);
    Page<Flat> getFlatsWithFilters(FlatFilter flatFilter, Pageable pageable);
}
