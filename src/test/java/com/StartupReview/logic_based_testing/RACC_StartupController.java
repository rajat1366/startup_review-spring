package com.StartupReview.logic_based_testing;

import com.StartupReview.service.StartupService;
import com.StartupReview.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class RACC_StartupController {
    @Autowired
    StartupService startupService;

    @Autowired
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    // this  is Restricted Active Clause Coverage on the predicate on url - /api/startup/search  on the predicate
    //     if(searchData!= null && page != null && size != null )   on line 48 in StartupController.java

    @Test
    void test1() throws Exception{
        //(searchData!= null && page != null && size != null )       C1 is major clause
        // searchData = null  ,  page = 1 , size = 2    return bad response
        //          F        &&     T    &&    T
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("page","1")
                .param("size","2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }




    @Test
    void test2() throws Exception{
        //(searchData!= null && page != null && size != null )   C2 is major clause
        // searchData = "zoom"  ,  page = null , size = 2    return bad response
        //          T         &&     F         &&    T
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("size","2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }




    @Test
    void test3() throws Exception{
        //(searchData!= null && page != null && size != null )      C3 is major clause
        // searchData = "zoom"  ,  page = 1 , size = null    return bad response
        //          T          &&     T     &&    F
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","test")
                .param("page","1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



    @Test
    void test4() throws Exception{
        //(searchData!= null && page != null && size != null )      C1 is major clause
        // searchData = "zoom" ,  page = 1 , size = 2    return ok response
        //          T        &&     T    &&    T
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","3")
                .param("size","4"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
