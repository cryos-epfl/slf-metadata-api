package ch.epfl.cryos.osper.controller;

import java.time.ZonedDateTime;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ch.epfl.cryos.osper.ApplicationFields;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller to get some information about this sdbo-api
 * 
 * @author pertschy@slf.ch, Jul 13, 2016
 *
 */
@RestController
@RequestMapping(ApplicationFields.REST_MAPPING_COMPONENT_INFO)
@Api(value = "REST sdbo-api configuration informations")
@PropertySources({@PropertySource("classpath:info.properties")})
public class ComponentInfoController {

	@Autowired
	private Environment env;
	
	private Properties properties;
	
	@PostConstruct
	private void createDto(){
		properties = new Properties();
		properties.setProperty("version", env.getProperty("system.version"));
		properties.setProperty("buildTime", env.getProperty("system.buildtime"));
		properties.setProperty("compilerVersion", env.getProperty("system.compiler"));
//		properties.setProperty("databaseUrl", env.getProperty("spring.datasource.url"));
//		properties.setProperty("databaseUser", env.getProperty("spring.datasource.username"));
//		properties.setProperty("boottime", ZonedDateTime.now().toString());
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get REST sdbo-api configuration informations", notes = "Get some informations about the sdbo-api configuration", response = Properties.class)
	public Properties readComponentInfo() {
		return properties;
	}

}
