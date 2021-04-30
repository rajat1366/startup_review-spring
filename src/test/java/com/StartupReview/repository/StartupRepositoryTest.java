package com.StartupReview.repository;

import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.service.StartupService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DataJpaTest
class StartupRepositoryTest {

    @Autowired
    private StartupRepository startupRepositoryTest;

    @Autowired
    private UserRepository userRepositoryTest;

    @Test
    void existsByNameTrue() {
        //given
        User testUser = new User("user1","user1@gmail.com","name","password");
		userRepositoryTest.save(testUser);

        Date dt = new Date();
		LocalDateTime now = LocalDateTime.now();

		String name= "zoom";
		Startup startup = new Startup(name,"video conferencing app",testUser,dt,now,"video");
        startupRepositoryTest.save(startup);

        //when
        Boolean expected = startupRepositoryTest.existsByName(name);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void existsByNameFalse() {
        //given
        String name= "zoom";
        //when
        Boolean expected = startupRepositoryTest.existsByName(name);
        //then
        assertThat(expected).isFalse();
    }

    @Test
    void findAllByOrderByLaunchDateDesc() {
        Pageable pageable = PageRequest.of(0,3);
        Page<Startup> s = startupRepositoryTest.findAllByOrderByLaunchDateDesc(pageable);
        assertNotNull(s);
    }


    @Test
    void findByNameContainingOrDescriptionContaining() {
        String search = "video";
        Pageable pageable = PageRequest.of(0,3);
        Page<Startup> s = startupRepositoryTest.findByNameContainingOrDescriptionContaining(search,search,pageable);
        assertNotNull(s);
    }
}