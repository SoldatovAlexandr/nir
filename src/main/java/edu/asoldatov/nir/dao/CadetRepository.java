package edu.asoldatov.nir.dao;

import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CadetRepository extends CrudRepository<Cadet, Long> {

    List<Cadet> findByFullNameAndGroup(String fullName, Group group);

}
