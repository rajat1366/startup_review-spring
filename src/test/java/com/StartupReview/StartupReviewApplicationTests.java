package com.StartupReview;

import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.repository.StartupRepository;
import com.StartupReview.service.StartupService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class StartupReviewApplicationTests {

	@Test
	void contextLoads()   {
	}
//	@Autowired
//	private StartupService startupService;
//
//	@MockBean
//	private StartupRepository startupRepository;
//
//	@Test
//	public void getAllStartupsTest() {
//		when(startupRepository.findAll())
//				.thenReturn(Stream
//			.of(new Startup("cred", "fintech startup that let users make credit card payments through their app"),
//					new Startup("zomato","food doorstep delivery startup")).collect(Collectors.toList()));
//		assertEquals(2, startupService.findAll().size());
//	}
//
//	@Test
//	public void getStartupsByIdTest() {
//
//	}
//
//	@Test
//	public void saveStartupTest() {
//		User user = new User("user1","user1@gmail.com","name","password");
//		Date dt = new Date();
//		LocalDateTime now = LocalDateTime.now();
//		Startup startup = new Startup("zoom","video conferencing app",user,dt,now);
//
//		when(startupRepository.save(startup)).thenReturn(startup);
//		assertEquals(startup, startupService.saveStartup(startup));
//	}

}
