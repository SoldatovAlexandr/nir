package edu.asoldatov.nir.service.impl;

import edu.asoldatov.nir.common.School;
import edu.asoldatov.nir.dao.GroupRepository;
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
        if (groupRepository.findByNameAndSchool(name, school).isPresent()) {
            throw new RuntimeException(String.format("Group with name [%s] and school [%s] already exists.", name, school.getLabel()));
        }
        Group group = Group.builder()
                .name(name)
                .school(school)
                .build();
        return groupRepository.save(group);
    }
}
