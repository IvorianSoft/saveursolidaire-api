package com.ivoriandev.saveursolidaire.rest.controllers;

import com.ivoriandev.saveursolidaire.services.AuthService;
import com.ivoriandev.saveursolidaire.utils.dto.auth.AuthenticationDto;
import com.ivoriandev.saveursolidaire.utils.dto.auth.AuthenticationResponse;
import com.ivoriandev.saveursolidaire.utils.dto.auth.TokenDto;
import com.ivoriandev.saveursolidaire.utils.dto.user.SellerRegisterDto;
import com.ivoriandev.saveursolidaire.utils.dto.user.UserDto;
import com.ivoriandev.saveursolidaire.utils.dto.user.UserRegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ivoriandev.saveursolidaire.utils.constants.AppConstant.API_BASE_URL_V1;

@RestController
@RequestMapping(API_BASE_URL_V1 + "/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserRegisterDto dto) throws Exception {
        log.info("start authController.register");

        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(value = "/register-seller", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<AuthenticationResponse> registerSeller(@Valid @RequestBody SellerRegisterDto dto) throws Exception {
        log.info("start authController.registerSeller");

        return ResponseEntity.ok(authService.registerSeller(dto));
    }

    @PostMapping(value = "/authenticate", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationDto dto) throws Exception {
        log.info("start authController.authenticate");

        return ResponseEntity.ok(authService.authenticate(dto));
    }

    @GetMapping(value = "/me", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<UserDto> me() throws Exception {
        log.info("start authController.me");

        return ResponseEntity.ok(authService.me());
    }

}
