package edu.asoldatov.nir.dao;

import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.DentalCondition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DentalConditionRepository extends CrudRepository<DentalCondition, Long> {

    DentalCondition findByCadet(Cadet cadet);

}
