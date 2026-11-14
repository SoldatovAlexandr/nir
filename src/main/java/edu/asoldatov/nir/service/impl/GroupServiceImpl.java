package edu.asoldatov.nir.service.impl;

import edu.asoldatov.nir.common.School;
import edu.asoldatov.nir.dao.GroupRepository;
import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.Group;
import edu.asoldatov.nir.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public Group create(String name, School school) {
        groupRepository.findByNameAndSchool(name, school)
                .ifPresent(groupRepository::delete);

        Group group = Group.builder()
                .name(name)
                .school(school)
                .build();
        return groupRepository.save(group);
    }

    @Override
    @Transactional
    public void deleteAll() {
        for (Group v : groupRepository.findAll()) {
            log.info("DELETE " + v.getName());
            groupRepository.delete(v);
        }
    }

    @Override
    @Transactional
    public void prepare() {
        for (Group group : groupRepository.findAll()) {
            int age = group.getCadets().stream().mapToInt(Cadet::getAge).filter(v -> v != 0).findAny().orElse(0);

            group.getCadets().forEach(v -> {
                if (v.getAge() == 0) {
                    v.setAge(age);
                }

                if (v.getPlaceOfBirth().equals("")) {
                    v.setPlaceOfBirth(group.getSchool().getRegion());
                }

                if (v.getRegionOfResidence().equals("")) {
                    v.setRegionOfResidence(group.getSchool().getRegion());
                }
            });


        }
    }

    @Override
    public Group findByNameAndSchool(String name, School school) {
        return groupRepository.findByNameAndSchool(name, school).get();
    }


}
