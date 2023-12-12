package com.erp.backend.services;


import com.erp.backend.dtos.auth.AuthenticationRequest;
import com.erp.backend.dtos.auth.AuthenticationResponse;
import com.erp.backend.dtos.auth.RegisterRequest;
import com.erp.backend.entities.User;
import com.erp.backend.enums.Role;
import com.erp.backend.enums.Status;
import com.erp.backend.exceptions.ResourceNotFoundException;
import com.erp.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
  //  private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .name(request.getName())
                .phone(request.getPhone())
                .status(Status.NOMAL)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ResourceNotFoundException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new ResourceNotFoundException("Token not found");
        }
//      var user = repository.findByEmail(request.getEmail())
//              .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        // check getUser and is Active
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}

