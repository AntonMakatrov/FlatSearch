package by.itacademy.flats_service.repository;


import by.itacademy.flats_service.core.entity.DeadFlat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeadFlatRepository extends JpaRepository<DeadFlat, UUID> {
}
