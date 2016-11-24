package ch.epfl.cryos.osper.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import ch.epfl.cryos.osper.Application;



@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@TestPropertySource(locations = { "classpath:test.properties" })
@ActiveProfiles({ "insecure", "test" })
public class StationMetadataControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void singleStation() throws Exception {
		this.mockMvc.perform(get("/metadata/stations/IMIS:ALI:2"))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.type",equalTo("Feature")))
			.andExpect(jsonPath("$.properties.name",equalTo("IMIS:ALI:2")))
				.andExpect(jsonPath("$.properties.description", equalTo("Chenau (ALI2) 1716 m")));
	}

	//ToDo: add error tests
	//ToDo: add test s for all endpoints
//	@Test
//    public void periodBadRequest() throws Exception {
//		this.mockMvc.perform(get("/dateExample/period?date=2015-12-01&period=3D"))
//			.andExpect(status().is4xxClientError())
//			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//			.andExpect(jsonPath("$.error",equalTo("Bad Request")));
//    }

	

}
