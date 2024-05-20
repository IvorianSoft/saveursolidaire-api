package com.ivoriandev.saveursolidaire.controllers;

import com.ivoriandev.saveursolidaire.models.Role;
import com.ivoriandev.saveursolidaire.services.RoleService;
import com.ivoriandev.saveursolidaire.utils.TestUtil;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstantsTest;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest.API_BASE_URL_V1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
@AutoConfigureMockMvc
@Transactional
public class RoleControllerTest {
    private static final String BASE_PATH = API_BASE_URL_V1 + "/roles";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleService roleService;

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testGetAllRolesWithUserAdmin() throws Exception {
        this.mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("ADMIN"))
                .andExpect(jsonPath("$[1].name").value("USER"))
                .andExpect(jsonPath("$[2].name").value("CUSTOMER"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.CUSTOMER)
    public void testGetAllRolesWithUserUser() throws Exception {
        this.mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testGetRoleByIdWithUserAdmin() throws Exception {
        this.mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ADMIN"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testGetRoleByIdWithUserAdminNotFound() throws Exception {
        this.mockMvc.perform(get(BASE_PATH + "/100"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testUpdateRoleWithUserAdmin() throws Exception {
        Role role = roleService.read(2);
        role.setName("UPDATED_ROLE");

        this.mockMvc.perform(put(BASE_PATH + "/2")
                .contentType("application/json")
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UPDATED_ROLE"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testUpdateRoleWithUserAdminBadRequest() throws Exception {
        Role role = roleService.read(2);

        this.mockMvc.perform(put(BASE_PATH + "/test")
                .contentType("application/json")
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testCreateRoleWithUserAdmin() throws Exception {
        Role role = new Role();
        role.setName("NEW_ROLE");

        this.mockMvc.perform(post(BASE_PATH)
                .contentType("application/json")
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("NEW_ROLE"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testCreateRoleWithUserAdminBadRequest() throws Exception {
        Role role = new Role();
        role.setName(null);

        this.mockMvc.perform(post(BASE_PATH)
                .contentType("application/json")
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testCreateRoleWithUserAdminBadRequest2() throws Exception {
        Role role = new Role();
        role.setName("ADMIN");

        this.mockMvc.perform(post(BASE_PATH)
                .contentType("application/json")
                .content(TestUtil.convertObjectToJsonBytes(role)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testDeleteRoleWithUserAdmin() throws Exception {
        this.mockMvc.perform(delete(BASE_PATH + "/4"))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get(BASE_PATH + "/4"))
                .andExpect(status().isNotFound());
    }

}
