package com.StartupReview.logic_based_testing;


import com.StartupReview.service.StartupService;
import com.StartupReview.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class CACC_StartupController {

    @Autowired
    StartupService startupService;

    @Autowired
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

     //    this  is Correlated Active Clause Coverage on the predicate on url - /api/startup/search  on the predicate
    // if(page < 0  || size <= 0 || searchData.length() == 0){   on line 49 in StartupController.java

    @Test
    void test1() throws Exception{
        //(page < 0  || size <= 0 || searchData.length() == 0)
        //   page = -5 ,  size = 1 , searchData = "zoom"      return bad response
        //          T  ||     F     ||    F
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","-5")
                .param("size","1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void test2() throws Exception{
        //  (page < 0  || size <= 0 || searchData.length() == 0)
        //   page = 2 , size = 0 , searchData = "zoom"      return bad response
        //       F   ||     T     ||    F
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","2")
                .param("size","0"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void test3() throws Exception{
        //  (page < 0  || size <= 0 || searchData.length() == 0)
        //   page = 2 , size = 3 , searchData = ""     return bad response
        //       F    ||     F     ||    T
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","")
                .param("page","2")
                .param("size","3"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void test4() throws Exception{
        //  (page < 0  || size <= 0 || searchData.length() == 0)
        //   page = 2 , size = 3, searchData = "zoom"     return ok response
        //        F   ||      F   ||    F

        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","2")
                .param("size","3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
