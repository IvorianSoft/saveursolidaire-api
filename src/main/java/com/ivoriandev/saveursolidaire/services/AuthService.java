package com.ivoriandev.saveursolidaire.services;

import com.ivoriandev.saveursolidaire.exceptions.BadRequestException;
import com.ivoriandev.saveursolidaire.exceptions.ConflictException;
import com.ivoriandev.saveursolidaire.models.Role;
import com.ivoriandev.saveursolidaire.models.Store;
import com.ivoriandev.saveursolidaire.models.embedded.Location;
import com.ivoriandev.saveursolidaire.repositories.UserRepository;
import com.ivoriandev.saveursolidaire.services.jwt.JwtService;
import com.ivoriandev.saveursolidaire.models.User;
import com.ivoriandev.saveursolidaire.utils.Utilities;
import com.ivoriandev.saveursolidaire.utils.dto.auth.AuthenticationDto;
import com.ivoriandev.saveursolidaire.utils.dto.auth.AuthenticationResponse;
import com.ivoriandev.saveursolidaire.utils.dto.user.SellerRegisterDto;
import com.ivoriandev.saveursolidaire.utils.dto.user.UserDto;
import com.ivoriandev.saveursolidaire.utils.dto.user.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final StoreService storeService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    public AuthenticationResponse register(UserRegisterDto userRegisterDto) {
        throwExceptionIfEmailExist(userRegisterDto.getEmail());

        User user = createUser(userRegisterDto, roleService.getDefaultRole());

        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationDto authenticationDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDto.getEmail(),
                        authenticationDto.getPassword()
                )
        );

        var user = userService.findByEmail(authenticationDto.getEmail());
        if (user == null) {
            throw new BadRequestException("Invalid credentials");
        }
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public UserDto me(){
        var userName = Utilities.getAuthenticateUserName();
        User user = userService.findByEmail(userName);
        if (user == null) {
            throw new BadRequestException("Invalid credentials");
        }

        return UserDto.from(user);
    }

    public AuthenticationResponse registerSeller(SellerRegisterDto dto) {
        throwExceptionIfEmailExist(dto.getEmail());

        User user = createUser(dto, roleService.getSellerRole());
        Store store = storeService.createAndAttacheUser(getStoreFormatted(dto), user.getId());

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    private User createUser(UserRegisterDto userRegisterDto, Role role) {
        User user = User.builder()
                .name(userRegisterDto.getName())
                .email(userRegisterDto.getEmail())
                .contact(userRegisterDto.getContact())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .role(role == null ? roleService.getDefaultRole() : role)
                .isActive(role != null && role.getName().equals("CUSTOMER"))
                .build();
        user = userService.create(user);
        return user;
    }

    public User updateIsActive(Integer id) {
        User user = userService.read(id);
        user.setIsActive(!user.getIsActive());

        return userRepository.save(user);
    }

    private Store getStoreFormatted(SellerRegisterDto dto) {
        return Store.builder()
                .name(dto.getStoreName())
                .contact(dto.getStoreContact())
                .description(dto.getStoreDescription())
                .location(
                        Location.builder()
                                .address(dto.getStoreAddress())
                                .city(dto.getStoreCity())
                                .country(dto.getStoreCountry())
                                .postalCode(dto.getStorePostalCode())
                                .complement(dto.getStoreComplement())
                                .latitude(dto.getStoreLatitude())
                                .longitude(dto.getStoreLongitude())
                                .build()
                )
                .build();
    }

    private void throwExceptionIfEmailExist(String email) {
        if (userService.existsByEmail(email)) {
            throw new ConflictException("Email already exists");
        }
    }
}
