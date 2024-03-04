package com.ivoriandev.saveursolidaire.controllers;

import com.ivoriandev.saveursolidaire.models.Basket;
import com.ivoriandev.saveursolidaire.repositories.BasketRepository;
import com.ivoriandev.saveursolidaire.services.BasketService;
import com.ivoriandev.saveursolidaire.utils.TestUtil;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstantsTest;
import com.ivoriandev.saveursolidaire.utils.dto.basket.CreateBasketDto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest.API_BASE_URL_V1;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.JVM)
public class BasketControllerTest {
    private static final String BASE_PATH = API_BASE_URL_V1 + "/baskets";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketRepository basketRepository;

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testGetAllBasketsWithUserAdmin() throws Exception {
        this.mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("BASKET"))
                .andExpect(jsonPath("$[0].description").value("DESCRIPTION"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[0].note").value("NOTE"))
                .andExpect(jsonPath("$[0].isActive").value(Boolean.TRUE))
                .andExpect(jsonPath("$[0].store.id").value(1));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.SELLER)
    public void testGetAllBasketsWithUserSeller() throws Exception {
        this.mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testGetBasketByIdWithUserAdmin() throws Exception {
        this.mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BASKET"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.note").value("NOTE"))
                .andExpect(jsonPath("$.isActive").value(Boolean.TRUE))
                .andExpect(jsonPath("$.store.id").value(1));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.SELLER)
    public void testGetBasketByIdWithUserSeller() throws Exception {
        this.mockMvc.perform(get(BASE_PATH + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BASKET"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.note").value("NOTE"))
                .andExpect(jsonPath("$.isActive").value(Boolean.TRUE))
                .andExpect(jsonPath("$.store.id").value(1));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testGetAllBasketsByStoreIdWithUserAdmin() throws Exception {
        this.mockMvc.perform(get(BASE_PATH + "/store/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("BASKET"))
                .andExpect(jsonPath("$[0].description").value("DESCRIPTION"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[0].note").value("NOTE"))
                .andExpect(jsonPath("$[0].isActive").value(Boolean.TRUE))
                .andExpect(jsonPath("$[0].store.id").value(1));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.SELLER)
    public void testGetAllBasketsByStoreIdWithUserSeller() throws Exception {
        this.mockMvc.perform(get(BASE_PATH + "/store/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("BASKET"))
                .andExpect(jsonPath("$[0].description").value("DESCRIPTION"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[0].note").value("NOTE"))
                .andExpect(jsonPath("$[0].isActive").value(Boolean.TRUE))
                .andExpect(jsonPath("$[0].store.id").value(1));
    }


    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testUpdateBasketWithUserAdmin() throws Exception {
        Basket basket = basketService.read(1);
        basket.setName("BASKET_UPDATE");
        basket.setDescription("DESCRIPTION_UPDATE");
        basket.setPrice(1000.0);
        basket.setNote("NOTE_UPDATE");

        this.mockMvc.perform(put(BASE_PATH + "/1")
                        .contentType("application/json")
                        .content(TestUtil.convertObjectToJsonBytes(basket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BASKET_UPDATE"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION_UPDATE"))
                .andExpect(jsonPath("$.price").value(1000.0))
                .andExpect(jsonPath("$.note").value("NOTE_UPDATE"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.SELLER)
    public void testUpdateBasketWithUserSeller() throws Exception {
        Basket basket = basketService.read(1);
        basket.setName("BASKET_UPDATE_SELLER");
        basket.setDescription("DESCRIPTION_UPDATE_SELLER");
        basket.setPrice(1000.0);
        basket.setNote("NOTE_UPDATE_SELLER");

        this.mockMvc.perform(put(BASE_PATH + "/1")
                        .contentType("application/json")
                        .content(TestUtil.convertObjectToJsonBytes(basket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BASKET_UPDATE_SELLER"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION_UPDATE_SELLER"))
                .andExpect(jsonPath("$.price").value(1000.0))
                .andExpect(jsonPath("$.note").value("NOTE_UPDATE_SELLER"));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testUpdateBasketQuantityWithUserAdmin() throws Exception {
        this.mockMvc.perform(put(BASE_PATH + "/1/quantity")
                        .contentType("application/json")
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.SELLER)
    public void testUpdateBasketQuantityWithUserSeller() throws Exception {
        this.mockMvc.perform(put(BASE_PATH + "/1/quantity")
                        .contentType("application/json")
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testUpdateBasketActiveStatusWithUserAdmin() throws Exception {
        Basket basket = basketService.read(1);

        this.mockMvc.perform(put(BASE_PATH + "/1/active-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isActive").value(!basket.getIsActive()));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.SELLER)
    public void testUpdateBasketActiveStatusWithUserSeller() throws Exception {
        Basket basket = basketService.read(1);

        this.mockMvc.perform(put(BASE_PATH + "/1/active-status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isActive").value(!basket.getIsActive()));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testDeleteBasketWithUserAdmin() throws Exception {
        this.mockMvc.perform(delete(BASE_PATH + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.SELLER)
    public void testDeleteBasketWithUserSeller() throws Exception {
        this.mockMvc.perform(delete(BASE_PATH + "/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.ADMIN)
    public void testCreateBasketWithUserAdmin() throws Exception {
        CreateBasketDto basket = new CreateBasketDto();
        basket.setName("BASKET_CREATE");
        basket.setDescription("DESCRIPTION_CREATE");
        basket.setPrice(100.0);
        basket.setQuantity(1);
        basket.setNote("NOTE_CREATE");
        basket.setStoreId(1);

        this.mockMvc.perform(post(BASE_PATH)
                        .contentType("application/json")
                        .content(TestUtil.convertObjectToJsonBytes(basket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BASKET_CREATE"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION_CREATE"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.initialQuantity").value(1))
                .andExpect(jsonPath("$.note").value("NOTE_CREATE"))
                .andExpect(jsonPath("$.isActive").value(Boolean.TRUE))
                .andExpect(jsonPath("$.store.id").value(1));
    }

    @Test
    @WithUserDetails(AuthoritiesConstantsTest.SELLER)
    public void testCreateBasketWithUserSeller() throws Exception {
        CreateBasketDto basket = new CreateBasketDto();
        basket.setName("BASKET_CREATE_SELLER");
        basket.setDescription("DESCRIPTION_CREATE_SELLER");
        basket.setPrice(1000.0);
        basket.setQuantity(5);
        basket.setNote("NOTE_CREATE_SELLER");
        basket.setStoreId(1);

        this.mockMvc.perform(post(BASE_PATH)
                        .contentType("application/json")
                        .content(TestUtil.convertObjectToJsonBytes(basket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("BASKET_CREATE_SELLER"))
                .andExpect(jsonPath("$.description").value("DESCRIPTION_CREATE_SELLER"))
                .andExpect(jsonPath("$.price").value(1000.0))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.initialQuantity").value(5))
                .andExpect(jsonPath("$.note").value("NOTE_CREATE_SELLER"))
                .andExpect(jsonPath("$.isActive").value(Boolean.TRUE))
                .andExpect(jsonPath("$.store.id").value(1));
    }
}
