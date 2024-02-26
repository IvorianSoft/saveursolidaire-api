package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.ConflictException;
import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import com.ivoriandev.saveursolidaire.utils.constants.AuthoritiesConstantsTest;
import com.ivoriandev.saveursolidaire.utils.dto.auth.AuthenticationDto;
import com.ivoriandev.saveursolidaire.utils.dto.auth.AuthenticationResponse;
import com.ivoriandev.saveursolidaire.utils.dto.user.SellerRegisterDto;
import com.ivoriandev.saveursolidaire.utils.dto.user.UserRegisterDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoreService storeService;

    private UserRegisterDto getValidUserRegisterDto() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail("hamed@saveursolidaire.com");
        userRegisterDto.setName("Hamed");
        userRegisterDto.setPassword("P@ssw0rd");
        userRegisterDto.setContact("0123456788");

        return userRegisterDto;
    }

    private AuthenticationDto getValidAuthenticationDto() {
        return AuthenticationDto.builder()
                        .email("email-authenticate@saveursolidaire.com")
                        .password(getValidUserRegisterDto().getPassword())
                        .build();
    }

    private SellerRegisterDto getValidSellerRegisterDto() {
        SellerRegisterDto sellerRegisterDto = new SellerRegisterDto();
        sellerRegisterDto.setEmail("seller@saveursolidaire.com");
        sellerRegisterDto.setName("Seller");
        sellerRegisterDto.setPassword("P@ssw0rd");
        sellerRegisterDto.setContact("0123456788");
        sellerRegisterDto.setStoreName("Store");
        sellerRegisterDto.setStoreDescription("Store description");
        sellerRegisterDto.setStoreContact("0123456788");
        sellerRegisterDto.setStoreAddress("Store address");
        sellerRegisterDto.setStoreCity("Store city");
        sellerRegisterDto.setStoreCountry("Store country");
        sellerRegisterDto.setStorePostalCode("00000");
        sellerRegisterDto.setStoreComplement("Store complement");
        sellerRegisterDto.setStoreLatitude(0.0);
        sellerRegisterDto.setStoreLongitude(0.0);

        return sellerRegisterDto;
    }

    @Test
    public void testRegister() {
        int countBeforeCreate = userService.all().size();

        UserRegisterDto userRegisterDto = getValidUserRegisterDto();
        userRegisterDto.setContact("0-contact-register");
        AuthenticationResponse authenticationResponse = authService.register(userRegisterDto);

        Assert.assertNotNull(authenticationResponse);
        Assert.assertNotNull(authenticationResponse.getToken());
        Assert.assertEquals(3, authenticationResponse.getToken().split("\\.").length);
        Assert.assertEquals(countBeforeCreate + 1, userService.all().size());
    }

    @Test
    public void testAuthenticate() {
        // authenticate new user
        UserRegisterDto userRegisterDto = getValidUserRegisterDto();
        userRegisterDto.setContact("0-contact-authenticate");
        userRegisterDto.setEmail("email-authenticate@saveursolidaire.com");
        authService.register(userRegisterDto);

        // authenticate the user who has been registered before
        AuthenticationResponse authenticationResponse = authService.authenticate(getValidAuthenticationDto());

        Assert.assertNotNull(authenticationResponse);
        Assert.assertNotNull(authenticationResponse.getToken());
        Assert.assertEquals(3, authenticationResponse.getToken().split("\\.").length);
    }

    @Test
    public void testRegisterSeller() {
        int countBeforeCreate = userService.all().size();
        int countStoreBeforeCreate = storeService.all().size();

        AuthenticationResponse authenticationResponse = authService.registerSeller(getValidSellerRegisterDto());

        Assert.assertNotNull(authenticationResponse);
        Assert.assertNotNull(authenticationResponse.getToken());
        Assert.assertEquals(3, authenticationResponse.getToken().split("\\.").length);
        Assert.assertEquals(countBeforeCreate + 1, userService.all().size());
        Assert.assertEquals(countStoreBeforeCreate + 1, storeService.all().size());
    }

    @Test
    public void testRegisterWithExistingEmail() {
        // authenticate new user with existing email
        UserRegisterDto userRegisterDto = getValidUserRegisterDto();
        userRegisterDto.setEmail(AuthoritiesConstantsTest.ADMIN);

        ConflictException conflictException = Assert.assertThrows(ConflictException.class, () -> {
            authService.register(userRegisterDto);
        });

        Assert.assertEquals("Email already exists", conflictException.getReason());
        Assert.assertEquals(HttpStatus.CONFLICT.value(), conflictException.getStatusCode().value());
    }

}
