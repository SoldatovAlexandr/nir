package edu.asoldatov.nir.dao;

import edu.asoldatov.nir.model.cadet.Ohis;
import org.springframework.data.repository.CrudRepository;

public interface OhisRepository extends CrudRepository<Ohis, Long> {

    boolean existsByCadetId(long cadetId);

}
