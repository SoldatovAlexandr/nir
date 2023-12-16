package edu.asoldatov.nir.service;

import edu.asoldatov.nir.common.School;
import edu.asoldatov.nir.model.cadet.Group;

public interface GroupService {

    Group create(String name, School school);

    void deleteAll();

    void prepare();

}
