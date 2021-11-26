package com.StartupReview.controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.StartupReview.models.ERole;
import com.StartupReview.models.Role;
import com.StartupReview.models.User;
import com.StartupReview.payload.request.LoginRequest;
import com.StartupReview.payload.request.SignupRequest;
import com.StartupReview.payload.response.JwtResponse;
import com.StartupReview.payload.response.MessageResponse;
import com.StartupReview.repository.RoleRepository;
import com.StartupReview.repository.UserRepository;
import com.StartupReview.security.jwt.JwtUtils;
import com.StartupReview.security.services.UserDetailsImpl;
import com.StartupReview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        if( loginRequest.getUsername().length() == 0 || !userService.existsByUsername(loginRequest.getUsername())
                ||  !encoder.matches(loginRequest.getPassword(),userService.findByUsername(loginRequest.getUsername()).get().getPassword())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Invalid username or password"));
        } else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getName(),
                    roles));
        }

    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try{
            Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
            Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

            if( signUpRequest.getUsername().length() <= 3 || signUpRequest.getUsername().length() > 20 || userService.existsByUsername(signUpRequest.getUsername() )){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username should be greater than 3 and less than 20 or username is already taken"));
            } else if(signUpRequest.getEmail().length()>20 || !emailPattern.matcher(signUpRequest.getEmail()).find()|| userService.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already used or invalid email or email length should be less than 20"));
            } else if( signUpRequest.getPassword().length() < 5 || signUpRequest.getPassword().length() > 40 || !p.matcher(signUpRequest.getPassword()).find()){
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: password should be greater than 5 and less than 40 or the password does not contain special character"));
            } else {
                User user = new User(signUpRequest.getUsername(),
                        signUpRequest.getEmail(),signUpRequest.getName(),
                        encoder.encode(signUpRequest.getPassword()));

                Set<String> strRoles = signUpRequest.getRole();
                Set<Role> roles = new HashSet<>();

                if (strRoles == null) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                } else {

                    strRoles.forEach(role -> {
                        if(role.equals("admin") || role.equals("mod") || role.equals("user")){
                            switch (role) {
                                case "admin":
                                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                    roles.add(adminRole);

                                    break;
                                case "mod":
                                    Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                    roles.add(modRole);

                                    break;
                                default:
                                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                    roles.add(userRole);
                            }
                        } else {
                                throw new RuntimeException("Error: invalid user role");
//
                        }

                    });
                }

                user.setRoles(roles);
//                userService.saveUser(user);

                logger.info("[RECORD ADDED] - User added successfully");
                return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
            }




//            if (userService.existsByUsername(signUpRequest.getUsername())) {
//                logger.error("[RECORD EXISTS] - username is already taken!");
//                return ResponseEntity
//                        .badRequest()
//                        .body(new MessageResponse("Error: Username is already taken!"));
//            }
//
//            if (userService.existsByEmail(signUpRequest.getEmail())) {
//                logger.error("[RECORD EXISTS] - Email is already in use!");
//                return ResponseEntity
//                        .badRequest()
//                        .body(new MessageResponse("Error: Email is already in use!"));
//            }
//
//            // Create new user's account
//            User user = new User(signUpRequest.getUsername(),
//                    signUpRequest.getEmail(),signUpRequest.getName(),
//                    encoder.encode(signUpRequest.getPassword()));
//
//            Set<String> strRoles = signUpRequest.getRole();
//            Set<Role> roles = new HashSet<>();
//
//            if (strRoles == null) {
//                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                roles.add(userRole);
//            } else {
//
//                strRoles.forEach(role -> {
//                    switch (role) {
//                        case "admin":
//                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                            roles.add(adminRole);
//
//                            break;
//                        case "mod":
//                            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                            roles.add(modRole);
//
//                            break;
//                        default:
//                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                            roles.add(userRole);
//                    }
//                });
//            }
//
//            user.setRoles(roles);
//            userService.saveUser(user);
//
//            logger.info("[RECORD ADDED] - User added successfully");
//            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e){
            logger.error("[UNABLE TO ADD RECORD] - Unable to add user"+e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Unable to add user"+e.getMessage()));

        }


    }
}

