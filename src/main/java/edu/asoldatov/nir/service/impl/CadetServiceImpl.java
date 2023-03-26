package edu.asoldatov.nir.service.impl;

import edu.asoldatov.nir.dao.CadetRepository;
import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.DentalCondition;
import edu.asoldatov.nir.service.CadetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CadetServiceImpl implements CadetService {

    private final CadetRepository cadetRepository;

    @Transactional
    @Override
    public void save(Cadet cadet) {
        cadet.getTemporomandibularJoint().setCadet(cadet);
        DentalCondition dentalCondition = cadet.getDentalCondition();
        dentalCondition.setCadet(cadet);
        dentalCondition.getYears().forEach(v -> {
            v.setDentalCondition(dentalCondition);
            v.getTeeth().forEach(t -> t.setYearDentalCondition(v));
        });
        cadet.getEnamelSpotting().forEach(v -> v.setCadet(cadet));
        cadet.getPeriodontalTissue().forEach(v -> v.setCadet(cadet));

        cadetRepository.save(cadet);
    }
}
