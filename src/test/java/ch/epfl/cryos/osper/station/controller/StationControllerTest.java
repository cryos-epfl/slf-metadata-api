package ch.epfl.cryos.osper.station.controller;

import ch.epfl.cryos.osper.station.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@TestPropertySource(locations = {"classpath:test.properties"})
@ActiveProfiles({"insecure", "test"})
public class StationControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private String STATIONS_PATH = "/metadata";

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void singleStation() throws Exception {
        this.mockMvc.perform(get(STATIONS_PATH + "/stations/721628"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", equalTo("Feature")))
                .andExpect(jsonPath("$.properties.name", equalTo("ALI2")))
                .andExpect(jsonPath("$.properties.description", equalTo("Chenau 1716 m")))
        .andDo(print());
    }

    //ToDo: add error tests
    //ToDo: add test s for all endpoints
	@Test
    public void stationBadRequest() throws Exception {
        this.mockMvc.perform(get(STATIONS_PATH + "/stations/yyy"))
//                .andDo(print())
			.andExpect(status().is4xxClientError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.error",equalTo("Bad Request")));
    }

    @Test
    public void stationNotFound() throws Exception {
        this.mockMvc.perform(get(STATIONS_PATH + "/stations/1"))
//                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error",equalTo("Not Found")));
    }

    @Test
    public void networks() throws Exception {
        this.mockMvc.perform(get(STATIONS_PATH + "/networks"))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void stationsOfNetworks() throws Exception {
        this.mockMvc.perform(get(STATIONS_PATH + "/networks/imis/stations"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", equalTo("FeatureCollection")))
                .andExpect(jsonPath("$.features").isArray())
                .andExpect(jsonPath("$.features[0].properties.network", equalTo("IMIS")));
    }

    @Test
    public void stations() throws Exception {
        this.mockMvc.perform(get(STATIONS_PATH + "/stations"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", equalTo("FeatureCollection")))
                .andExpect(jsonPath("$.features").isArray())
                .andExpect(jsonPath("$.features", hasSize(greaterThan(900))));
    }

}
