package com.StartupReview.logic_based_testing;


import com.StartupReview.models.User;
import com.StartupReview.payload.request.SignupRequest;
import com.StartupReview.repository.RoleRepository;
import com.StartupReview.security.jwt.JwtUtils;
import com.StartupReview.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
public class CACC_AuthController {

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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // this  is Correlated Active Clause Coverage on the predicate on url - /api/auth/signup  on the predicate
    //          on line 94 in AuthController.java
    //if( signUpRequest.getUsername().length() < 3 || signUpRequest.getUsername().length() > 20 || userService.existsByUsername(signUpRequest.getUsername() ))
    //                              C1             ||                      C2                   ||      C3              naming the clauses.
    //  method test1 and test2 show C1 as the major clause
    //  method test3 and test4 show C2 as major clause
    //  method test5 and test6 show C3 as major clause

    @Test
    void test1() throws Exception{
        // username = "tet"     C1 = true   C2 = false   C3  = false    return bad request

        SignupRequest testUser = new SignupRequest("tet", "test", "test@test.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    void test100() throws Exception{
        // username = "test"     C1 = true   C2 = false   C3  = false    return bad request

        SignupRequest testUser = new SignupRequest("test", "test", "test@test.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
