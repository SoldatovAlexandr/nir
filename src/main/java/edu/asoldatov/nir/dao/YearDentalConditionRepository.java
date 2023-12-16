package edu.asoldatov.nir.dao;

import edu.asoldatov.nir.model.cadet.DentalCondition;
import edu.asoldatov.nir.model.cadet.YearDentalCondition;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface YearDentalConditionRepository extends CrudRepository<YearDentalCondition, Long> {

    List<YearDentalCondition> findAllByDentalCondition(DentalCondition dentalCondition);
}
