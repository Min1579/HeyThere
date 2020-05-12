package org.devs.heythere_backend.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public boolean existByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public boolean existByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public Long register(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole(RoleName.ROLE_USER);
        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user).getId();
    }

    @Transactional
    public List<UserResearchFoundResponseDto> searchByUsernameOrNameOrEmail(final String usernameOrNameOrEmail) {
        return userRepository.findByUsernameContainingOrNameContainingOrEmailContaining(
                usernameOrNameOrEmail,
                usernameOrNameOrEmail,
                usernameOrNameOrEmail).stream()
                .map(user -> UserResearchFoundResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .name(user.getName())
                        .email(user.getEmail())
                        .picture(user.getPicture())
                        .build())
                .collect(Collectors.toList());
//        return userRepository.findByUsernameOrEmail(usernameOrNameOrEmail,usernameOrNameOrEmail);
    }

}
