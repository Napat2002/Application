package com.example.REST_API.controllers;

import org.springframework.boot.test.context.SpringBootTest;

import com.example.REST_API.models.Application;
import com.example.REST_API.repository.ApplicationRepository;
import com.example.REST_API.services.ApplicationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SpringBootTest
class ApplicationControllerTest {

    @InjectMocks
    private ApplicationService applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateApplication() {
        Application app = new Application();
        app.setProductType("TypeA");

        when(applicationRepository.save(any(Application.class))).thenAnswer(i -> i.getArgument(0));

        Application created = applicationService.createApplication(app);
        assertNotNull(created.getCreatedOn());
        assertNotNull(created.getModifiedOn());
        assertEquals("TypeA", created.getProductType());
    }

    @Test
    public void testGetAllApplications() {
        List<Application> list = Arrays.asList(new Application(), new Application());
        when(applicationRepository.findAll()).thenReturn(list);

        List<Application> result = applicationService.getAllApplications();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetApplicationById() {
        Application app = new Application();
        app.setAppId(1L);
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(app));

        Optional<Application> result = applicationService.getApplicationById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getAppId());
    }

    @Test
    public void testUpdateApplicationFound() {
        Application existing = new Application();
        existing.setAppId(1L);
        existing.setProductType("Old");

        Application updateData = new Application();
        updateData.setProductType("New");

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(applicationRepository.save(any(Application.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Application> updated = applicationService.updateApplication(1L, updateData);

        assertTrue(updated.isPresent());
        assertEquals("New", updated.get().getProductType());
    }

    @Test
    public void testUpdateApplicationNotFound() {
        when(applicationRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Application> updated = applicationService.updateApplication(2L, new Application());

        assertFalse(updated.isPresent());
    }

    @Test
    public void testDeleteApplicationFound() {
        when(applicationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(applicationRepository).deleteById(1L);

        boolean deleted = applicationService.deleteApplication(1L);
        assertTrue(deleted);
    }

    @Test
    public void testDeleteApplicationNotFound() {
        when(applicationRepository.existsById(2L)).thenReturn(false);

        boolean deleted = applicationService.deleteApplication(2L);
        assertFalse(deleted);
    }
}
