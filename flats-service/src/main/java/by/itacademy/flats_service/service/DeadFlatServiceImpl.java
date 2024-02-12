package by.itacademy.flats_service.service;

import by.itacademy.flats_service.core.entity.DeadFlat;
import by.itacademy.flats_service.repository.DeadFlatRepository;
import by.itacademy.flats_service.service.api.DeadFlatService;
import org.springframework.stereotype.Component;

@Component
public class DeadFlatServiceImpl implements DeadFlatService {

    private final DeadFlatRepository deadFlatRepository;

    public DeadFlatServiceImpl(DeadFlatRepository deadFlatRepository) {
        this.deadFlatRepository = deadFlatRepository;
    }

    @Override
    public void saveDeadFlat(DeadFlat deadFlat){
        deadFlatRepository.save(deadFlat);
    }
}
