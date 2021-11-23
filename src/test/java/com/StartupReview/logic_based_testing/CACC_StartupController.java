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

    // this  is Correlated Active Clause Coverage on the predicate on url - /api/startup/search  on the predicate
    //                    if(searchData!= null && page != null && size != null )   on line 48 in StartupController.java
    //                              C1         &&      C2      &&      C3              naming the clauses.
    //  method test1 and test2 show C1 as the major clause
    //  method test3 and test4 show C2 as major clause
    //  method test5 and test6 show C3 as major clause

    @Test
    void test1() throws Exception{
        // searchData = null  ,  page = 1 , size = 2    return bad response


        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("page","1")
                .param("size","2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void test2() throws Exception{
        // searchData = "zoom" ,  page = 3 , size = 4    return ok response


        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","3")
                .param("size","4"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void test3() throws Exception{
        // searchData = "zoom"  ,  page = null , size = 2    return bad response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("size","2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void test4() throws Exception{
        // searchData = "swiggy" ,  page = 1 , size = 4    return ok response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","swiggy")
                .param("page","1")
                .param("size","4"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test5() throws Exception{
        // searchData = "test"  ,  page = 1 , size = null    return bad response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","test")
                .param("page","1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void test6() throws Exception{
        // searchData = "test123" ,  page = 3 , size = 4    return ok response


        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","3")
                .param("size","4"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //                    if(page < 0  || size <= 0 || searchData.length() == 0){   on line 49 in StartupController.java
    //                          C1     ||      C2  ||      C3                      naming the clauses.
    //  method test7 and test8 show C1 as the major clause
    //  method test9 and test10 show C2 as major clause
    //  method test11 and test12 show C3 as major clause

    @Test
    void test7() throws Exception{
        // searchData = "zoom"  ,  page = -5 , size = 4    return bad response


        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","-5")
                .param("size","4"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void test8() throws Exception{
        // searchData = "swiggy" ,  page = 10 , size = 5    return ok response


        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","swiggy")
                .param("page","10")
                .param("size","5"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test9() throws Exception{
        // searchData = "zoom"  ,  page = 1 , size = -2    return bad response


        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","1")
                .param("size","-2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void test10() throws Exception{
        // searchData = "swiggy" ,  page = 3 , size = 7    return ok response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","swiggy")
                .param("page","3")
                .param("size","7"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }




    @Test
    void test11() throws Exception{
        // searchData = ""  ,  page = 1 , size = 3    return bad response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","")
                .param("page","1")
                .param("size","3"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void test12() throws Exception{
        // searchData = "zoom" ,  page = 3 , size = 4    return ok response

        mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
                .param("searchData","zoom")
                .param("page","3")
                .param("size","4"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
