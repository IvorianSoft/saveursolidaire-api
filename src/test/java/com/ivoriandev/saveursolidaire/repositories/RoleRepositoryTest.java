package com.ivoriandev.saveursolidaire.repositories;

import com.ivoriandev.saveursolidaire.models.Role;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSave() {
        Role role = new Role();
        role.setName("ROLE_USER");
        entityManager.persistAndFlush(role);

        Role found = roleRepository.findById(role.getId()).orElse(null);
        Assert.assertNotNull(found);
        Assert.assertEquals(role.getName(), found.getName());
    }

    @Test
    public void testFindByNameIgnoreCase() {
        Role role = new Role();
        role.setName("ROLE_USER");
        entityManager.persistAndFlush(role);

        Role found = roleRepository.findByNameIgnoreCase(role.getName()).orElse(null);
        Assert.assertNotNull(found);
        Assert.assertEquals(role.getName(), found.getName());
        Assert.assertEquals(role.getId(), found.getId());
    }

    @Test
    public void testFindByNameIgnoreCaseNotFound() {
        Role found = roleRepository.findByNameIgnoreCase("ROLE_USER").orElse(null);
        Assert.assertNull(found);
    }

    @Test
    public void testFindByNameIgnoreCaseNull() {
        Role found = roleRepository.findByNameIgnoreCase(null).orElse(null);
        Assert.assertNull(found);
    }

    @Test
    public void testFindByNameIgnoreCaseEmpty() {
        Role found = roleRepository.findByNameIgnoreCase("").orElse(null);
        Assert.assertNull(found);
    }
}
