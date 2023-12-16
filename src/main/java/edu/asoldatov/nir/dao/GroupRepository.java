package edu.asoldatov.nir.dao;

import edu.asoldatov.nir.common.School;
import edu.asoldatov.nir.model.cadet.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByNameAndSchool(String name, School school);

}
