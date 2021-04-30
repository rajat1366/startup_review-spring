package com.StartupReview.service;

import com.StartupReview.repository.StartupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StartupServiceTest {

    @Mock
    private StartupRepository startupRepository;

    private StartupService startupService;

    @BeforeEach
    void setUp() {
        startupService = new StartupService(startupRepository);
    }

    @Test
    void saveStartup() {
    }

    @Test
    void findByName() {
    }

    @Test
    void getstartups() {
    }

    @Test
    void getstartupsFromSearchData() {
    }

    @Test
    void getstartupsById() {
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