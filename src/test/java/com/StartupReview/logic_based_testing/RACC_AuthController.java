package com.StartupReview.logic_based_testing;

import com.StartupReview.payload.request.SignupRequest;
import com.StartupReview.repository.RoleRepository;
import com.StartupReview.security.jwt.JwtUtils;
import com.StartupReview.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
public class RACC_AuthController {

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

    // this  is Restricted Active Clause Coverage on the predicate on url - /api/auth/signup  on the predicate
    //          on line 102 in AuthController.java
    //if( signUpRequest.getPassword().length() < 5 || signUpRequest.getPassword().length() > 40 || !p.matcher(signUpRequest.getPassword()).find())
    //                              C1             ||                      C2                   ||                C3              naming the clauses.

    @Test
    void test1() throws Exception{
        //      C1    ||    C2     ||     C3        --  C1 is major clause  -- password = pass
        //      T     ||    F      ||      F        --  return bad response

        SignupRequest testUser = new SignupRequest("rajat123", "test", "test@test.com", new HashSet<String>(), "pass");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void test2() throws Exception{
        //      C1    ||    C2     ||     C3        --  C2 is major clause  -- password = passwordlengthisgreaterthanfortyisinvalid$
        //      F     ||    T      ||      F        --  return bad response

        SignupRequest testUser = new SignupRequest("rajat123", "test", "test@test.com", new HashSet<String>(), "passwordlengthisgreaterthanfortyisinvalid$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    void test3() throws Exception{
        //      C1    ||    C2     ||     C3        --  C3 is major clause  -- password = passwithoutspecialcharacter
        //      F     ||    F      ||      T        --  return bad response

        SignupRequest testUser = new SignupRequest("rajat123", "test", "test@test.com", new HashSet<String>(), "passwithoutspecialcharacter");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void test4() throws Exception{
        //      C1    ||    C2     ||     C3        --  C3 is major clause  -- username = password$
        //      F     ||    F      ||      F        --  return ok response

        SignupRequest testUser = new SignupRequest("rajat123", "test", "test@test.com", new HashSet<String>(), "passwordwithSpecialChar$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
