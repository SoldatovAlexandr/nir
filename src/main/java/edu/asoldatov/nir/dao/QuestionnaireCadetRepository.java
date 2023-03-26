package edu.asoldatov.nir.dao;

import edu.asoldatov.nir.model.questionnaire.QuestionnaireCadet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionnaireCadetRepository extends CrudRepository<QuestionnaireCadet, Long> {

    List<QuestionnaireCadet> findByDooAndAge(String doo, Integer age);

}
