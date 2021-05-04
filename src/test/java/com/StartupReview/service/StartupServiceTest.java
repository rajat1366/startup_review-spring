package com.StartupReview.service;

import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.repository.StartupRepository;
import com.StartupReview.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StartupServiceTest {

    @Mock
    private StartupRepository startupRepository;

    private StartupService startupService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        startupService = new StartupService(startupRepository);
    }

    @Test
    void saveStartup() {
        User user = new User("user1","user1@gmail.com","name","password");
		Date dt = new Date();
		LocalDateTime now = LocalDateTime.now();
		Startup startup = new Startup("zoom","video conferencing app",user,dt,now,"video","testLink");

		when(startupRepository.save(startup)).thenReturn(startup);
		assertEquals(startup, startupService.saveStartup(startup));
    }

    @Test
    void findByNameTrue() {
        User user = new User("user1","user1@gmail.com","name","password");
        Date dt = new Date();
        LocalDateTime now = LocalDateTime.now();

        String name = "zoom";
        Startup startup = new Startup(name,"video conferencing app",user,dt,now,"video","testLink");

        when(startupRepository.existsByName(name)).thenReturn(Boolean.TRUE);
//        Boolean b = startupService.findByName(name);

//        assertThat(b).isTrue();
        assertEquals(Boolean.TRUE, startupService.findByName(name));
    }

    @Test
    void findByNameFalse() {
        String name = "zoom";
        when(startupRepository.existsByName(name)).thenReturn(Boolean.FALSE);
        Boolean b = startupService.findByName(name);

        assertThat(b).isFalse();
    }

    @Test
    void getstartups() {
//        Pageable pageable = PageRequest.of(0,3);
//        when(startupRepository.findAllByOrderByLaunchDateDesc(pageable)).thenReturn();
    }


    @Test
    void getstartupsFromSearchData() {
    }

    @Test
    void getstartupsById() {
        User testUser = new User("user1","user1@gmail.com","name","password");
//        userRepository.save(testUser);

        Date dt = new Date();
        LocalDateTime now = LocalDateTime.now();

        Startup startup = new Startup("dummy","dummy",testUser,dt,now,"dummy","testLink");

        startup.setId(1l);

        when(startupRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(startup));

        Optional<Startup> s = startupService.getstartupsById(startup.getId());

        assertNotNull(s);
    }

    @Test
    void deleteById() {
    }

    @Test
    void findAll() {
        //when
        startupService.findAll();
        //then
        verify(startupRepository).findAll();
    }
}