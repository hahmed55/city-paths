package com.citypaths.service;

import com.citypaths.web.CityPathsController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=com.citypaths.com.citypaths.model.CityConnections.class)
public class CityPathsServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CityPathsController cityPathsControllerMock;

    @InjectMocks
    private CityPathsService cityPathsService = Mockito.mock(CityPathsService.class);

    @Before
    public void init() throws Exception{
        Resource resource;
        resource = new ClassPathResource("/city.txt");
        File input = resource.getFile();
        mockMvc = MockMvcBuilders.standaloneSetup(cityPathsControllerMock).build();
    }


    @Test
    public void testConnectedBostonNewark() throws Exception {
        Mockito.when(cityPathsService.citiesConnected("Boston" , "Newark")).thenReturn(true);
        String getPath = "/connected?origin=Boston&destination=Newark";
        mockMvc.perform(MockMvcRequestBuilders.get(getPath))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("yes"));
    }

    @Test
    public void testConnectedBostonPhiladelphia() throws Exception {
        Mockito.when(cityPathsService.citiesConnected("Boston" , "Philadelphia")).thenReturn(true);
        String getPath = "/connected?origin=Boston&destination=Philadelphia";
        mockMvc.perform(MockMvcRequestBuilders.get(getPath))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("yes"));

    }

    @Test
    public void testConnectedPhiladelphiaAlbany() throws Exception {
        Mockito.when(cityPathsService.citiesConnected("Philadelphia" , "Albany")).thenReturn(false);
        String getPath = "/connected?origin=Philadelphia&destination=Albany";
        mockMvc.perform(MockMvcRequestBuilders.get(getPath))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("no"));
    }

    @Test
    public void testOriginMissing() throws Exception {
        String getPath = "/connected?source=Philadelphia&destination=Albany";
        mockMvc.perform(MockMvcRequestBuilders.get(getPath))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDestinationMissing() throws Exception {
        String getPath = "/connected?origin=Philadelphia";
        mockMvc.perform(MockMvcRequestBuilders.get(getPath))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
