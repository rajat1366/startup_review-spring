package com.StartupReview;

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
class  StartupReviewApplicationTests {

	@Autowired
	StartupService startupService;

	@Autowired
	UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads()   {
	}

	@Test
	void test1() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/api/startup/search")
				.param("searchData","zoom")
				.param("page","3")
				.param("size","4"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
