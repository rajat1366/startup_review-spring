package com.StartupReview.logic_based_testing;


import com.StartupReview.models.User;
import com.StartupReview.payload.request.LoginRequest;
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
    //if( signUpRequest.getUsername().length() <= 3 || signUpRequest.getUsername().length() > 20 || userService.existsByUsername(signUpRequest.getUsername() ))
    //                              C1             ||                      C2                   ||      C3              naming the clauses.

    @Test
    void test1() throws Exception{
        //      C1    ||    C2     ||     C3         -- username = tet
        //      T     ||    F      ||       F       --  return bad request

        SignupRequest testUser = new SignupRequest("tet", "test", "test@test.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    void test2() throws Exception{
        //      C1    ||    C2     ||     C3        --  C2 is major clause  -- username = testlengthisgreaterthan20
        //      F     ||    T      ||      F        --  return bad request

        SignupRequest testUser = new SignupRequest("testlengthisgreaterthan20", "test", "test@test.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void test3() throws Exception{
        //      C1    ||    C2     ||     C3         -- username = admin
        //      F     ||    F      ||      T        --  return bad request

        SignupRequest testUser = new SignupRequest("admin", "test", "test@test.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    @Test
    void test4() throws Exception{
        //      C1    ||    C2     ||     C3        --  C3 is major clause  -- username = test123
        //      F     ||    F      ||      F        --  return ok response

        SignupRequest testUser = new SignupRequest("test123", "test", "test@test.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    // this  is Correlated Active Clause Coverage on the predicate on url - /api/auth/signup  on the predicate
    //          on line 98 in AuthController.java
    //if(signUpRequest.getEmail().length()>20 || !emailPattern.matcher(signUpRequest.getEmail()).find()|| userService.existsByEmail(signUpRequest.getEmail()))
    //                              C1        ||                      C2                               ||                C3              naming the clauses.

    @Test
    void test5() throws Exception{
        //      C1    ||    C2     ||     C3        --  C1 is major clause  -- email = testslengthgreaterthan20@gmail.com
        //      T     ||    F      ||      F        --  return bad response

        SignupRequest testUser = new SignupRequest("test123", "test", "testslengthgreaterthan20@gmail.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void test6() throws Exception{
        //      C1    ||    C2     ||     C3        --  C2 is major clause  -- email = test123.com
        //      F     ||    T      ||      F        --  return bad response

        SignupRequest testUser = new SignupRequest("rajat123", "test", "test123.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void test7() throws Exception{
        //      C1    ||    C2     ||     C3        --  C3 is major clause  -- email = admin@admin.com
        //      F     ||    F      ||      T        --  return bad response

        SignupRequest testUser = new SignupRequest("rajat123", "test", "admin@admin.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void test8() throws Exception{
        //      C1    ||    C2     ||     C3        --  C3 is major clause  -- username = test123@gmail.com
        //      F     ||    F      ||      F        --  return ok response

        SignupRequest testUser = new SignupRequest("test123", "test", "test123@gmail.com", new HashSet<String>(), "test123$");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    // this  is Correlated Active Clause Coverage on the predicate on url - /api/auth/signin  on the predicate
    //          on line 63 in AuthController.java
//   if( loginRequest.getUsername().length() == 0 || !userService.existsByUsername(loginRequest.getUsername())
//            ||  !encoder.matches(loginRequest.getPassword(),userService.findByUsername(loginRequest.getUsername()).get().getPassword())){
    //                              C1             ||                      C2                   ||      C3              naming the clauses.

    @Test
    void test9() throws Exception{
        //      C1    ||    C2     ||     C3         -- username = ""  -- password = test
        //      T     ||    F      ||       F       --  return bad request

        LoginRequest testUser = new LoginRequest("", "test");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


    @Test
    void test10() throws Exception{
        //      C1    ||    C2     ||     C3        -- username = test123  -- password = pass
        //      F     ||    T      ||      F        --  return bad request

        LoginRequest testUser = new LoginRequest("test123", "pass");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    void test11() throws Exception{
        //      C1    ||    C2     ||     C3         -- username = rajat  -- password = wrongPass
        //      F     ||    F      ||      T        --  return bad request

        LoginRequest testUser = new LoginRequest("rajat", "wrongPass");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    @Test
    void test12() throws Exception{
        //      C1    ||    C2     ||     C3        -- username = rajat  -- password = rajat
        //      F     ||    F      ||      F        --  return ok response

        LoginRequest testUser = new LoginRequest("rajat", "rajat");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
