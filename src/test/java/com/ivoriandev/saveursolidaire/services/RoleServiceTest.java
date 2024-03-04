package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.models.Role;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testGetAllRoles() {
        List<Role> roles = roleService.all();

        assertEquals(5, roles.size());
    }

    @Test
    public void testGetRoleById() {
        Role role = roleService.read(1);

        assertEquals("ADMIN", role.getName());
    }

    @Test
    public void testCreateRole() {
        Role role = new Role();
        role.setName("NEW_ROLE");

        //count before create
        int countBeforeCreate = roleService.all().size();

        Role createdRole = roleService.create(role);

        assertEquals("NEW_ROLE", createdRole.getName());
        assertEquals(countBeforeCreate + 1, roleService.all().size());
    }

    @Test
    public void testUpdateRole() {
        Role role = roleService.read(4);
        role.setName("UPDATED_ROLE");

        Role updatedRole = roleService.update(role.getId(), role);

        assertEquals("UPDATED_ROLE", updatedRole.getName());
    }

    @Test
    public void testDeleteRole() {
        //count before delete
        int countBeforeDelete = roleService.all().size();

        roleService.delete(4);

        assertEquals(countBeforeDelete - 1, roleService.all().size());
    }

    @Test
    public void testGetDefaultRole() {
        Role role = roleService.getDefaultRole();

        assertEquals("CUSTOMER", role.getName());
    }
}
