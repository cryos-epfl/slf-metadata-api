package ch.epfl.cryos.osper.station.configuration;

import ch.epfl.cryos.osper.station.ApplicationFields;
import ch.slf.pro.common.util.exception.handler.*;
import ch.slf.pro.common.util.exception.handler.tools.ExceptionTypeDocBuilder;
import ch.slf.pro.common.util.geo.Crs;
import ch.slf.pro.common.util.swagger.CrsSwaggerModel;
import ch.slf.pro.common.util.swagger.GeometrySwaggerModel;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.*;
import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * 
 * Configures and enables swagger springfox documentation.
 * The generated files are hosted under http://&lt;host&gt;/&lt;app&gt;/swagger-ui.html (http://localhost:8180/swagger-ui.html).
 * For informations how to document the REST interface see the swagger and springfox documentation.
 * 
 * @see <a href="http://swagger.io">Swagger</a>
 * @see <a href="https://github.com/swagger-api/swagger-core">Swagger core</a>
 * @see <a href="https://github.com/swagger-api/swagger-core/wiki/Annotations">Swagger core annotations</a>
 * @see <a href="http://springfox.github.io/springfox/">Springfox</a>
 * 
 * 
 * @author pertschy@slf.ch, Jun 22, 2015
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	

	@Autowired
	private Environment env;
	
	@Bean
	public Docket archetypeDocket() {
		Docket docket =  new Docket(DocumentationType.SWAGGER_2);
		docket.groupName("1 - SLFPro Archetype");
		docket.useDefaultResponseMessages(false);
//		docket.select().paths(or(
//				regex(ApplicationFields.REST_METADATA+".*"),
//				regex(ApplicationFields.REST_DATA+".*"))).build();
		docket.select().paths(
				regex(ApplicationFields.REST_METADATA+ ".*")).build();
		addModelSubstitute(docket);
		addArchetypeGlobalParameters(docket);
		setArchetypeApiInfo(docket);
		//setArchetypeGlobalResponseMessages(docket);
		return docket;
	}
	
	@Bean
	public Docket infoDocket() {
		Docket docket =  new Docket(DocumentationType.SWAGGER_2);
		docket
			.groupName("2 - SLFPro Archetype Info")
			.useDefaultResponseMessages(false)
			.select().paths(
				regex(ApplicationFields.REST_MAPPING_COMPONENT_INFO+".*")).build()
			.apiInfo(new ApiInfoBuilder()
	            .title("Station metadata access services")
	            .description(
	            	   "<p>Controllers to get some information such as version about this sdbo-api</p>")
	            .contact(new Contact("Slf Davos", "http://www.slf.ch", "info@slf.ch"))
	            .version(env.getProperty("system.version", String.class, ""))
	            .build());
		return docket;
	}
	
	private void addModelSubstitute(Docket docket){
		docket.directModelSubstitute(Crs.class, CrsSwaggerModel.class).directModelSubstitute(Geometry.class, GeometrySwaggerModel.class)
		.directModelSubstitute(LocalDate.class, String.class).directModelSubstitute(LocalTime.class, String.class)
		.directModelSubstitute(LocalDateTime.class, String.class).directModelSubstitute(OffsetDateTime.class, String.class)
		.directModelSubstitute(OffsetTime.class, String.class).directModelSubstitute(Duration.class, String.class)
		.directModelSubstitute(Period.class, String.class).directModelSubstitute(ZonedDateTime.class, String.class);
	}
	
	@SuppressWarnings("unused")
	private void setArchetypeGlobalResponseMessages(Docket docket){
		//GET Exceptions
		ExceptionTypeDocBuilder getDocBuilder = new ExceptionTypeDocBuilder()
			.addResponseDescriptions(RestControllerExceptionType.values())
			.addResponseDescriptions(new ExceptionType[] {
					DataAccessExceptionType.CLEANUP_FAILURE,
					DataAccessExceptionType.DATA_RETRIEVAL_FAILURE,
					DataAccessExceptionType.DATA_SOURCE_LOOKUP_FAILURE,
					DataAccessExceptionType.INVALID_DATA_ACCESS_API_USAGE,
					DataAccessExceptionType.INVALID_DATA_ACCESS_RESOURCE_USAGE,
					DataAccessExceptionType.NON_TRANSIENT_DATA_ACCESS_RESOURCE,
					DataAccessExceptionType.PERMISSION_DENIED,
					DataAccessExceptionType.QUERY_TIMEOUT,
					DataAccessExceptionType.RECOVERABLE,
					DataAccessExceptionType.TRANSIENT_DATA_ACCESS_RESOURCE,
					DataAccessExceptionType.UNCATEGORIZED})
			.addResponseDescriptions(GeneralExceptionType.values());
		//POST Exceptions
		ExceptionTypeDocBuilder postDocBuilder = new ExceptionTypeDocBuilder()
			.addResponseDescriptions(RestControllerExceptionType.values())
			.addResponseDescriptions(ValidationExceptionType.values())
			.addResponseDescriptions(new ExceptionType[] {
					DataAccessExceptionType.CLEANUP_FAILURE,
					DataAccessExceptionType.DATA_INTEGRITY_VIOLATION,
					DataAccessExceptionType.DATA_RETRIEVAL_FAILURE,
					DataAccessExceptionType.DATA_SOURCE_LOOKUP_FAILURE,
					DataAccessExceptionType.DUPLICATE_KEY,
					DataAccessExceptionType.INVALID_DATA_ACCESS_API_USAGE,
					DataAccessExceptionType.INVALID_DATA_ACCESS_RESOURCE_USAGE,
					DataAccessExceptionType.NON_TRANSIENT_DATA_ACCESS_RESOURCE,
					DataAccessExceptionType.PERMISSION_DENIED,
					DataAccessExceptionType.QUERY_TIMEOUT,
					DataAccessExceptionType.RECOVERABLE,
					DataAccessExceptionType.TRANSIENT_DATA_ACCESS_RESOURCE,
					DataAccessExceptionType.UNCATEGORIZED})
			.addResponseDescriptions(GeneralExceptionType.values());
		//Put Exceptions
		ExceptionTypeDocBuilder putDocBuilder = new ExceptionTypeDocBuilder()
				.addResponseDescriptions(RestControllerExceptionType.values())
				.addResponseDescriptions(ValidationExceptionType.values())
				.addResponseDescriptions(new ExceptionType[] {
						DataAccessExceptionType.CLEANUP_FAILURE,
						DataAccessExceptionType.DATA_INTEGRITY_VIOLATION,
						DataAccessExceptionType.DATA_RETRIEVAL_FAILURE,
						DataAccessExceptionType.DATA_SOURCE_LOOKUP_FAILURE,
						DataAccessExceptionType.INVALID_DATA_ACCESS_API_USAGE,
						DataAccessExceptionType.INVALID_DATA_ACCESS_RESOURCE_USAGE,
						DataAccessExceptionType.NON_TRANSIENT_DATA_ACCESS_RESOURCE,
						DataAccessExceptionType.OBJECT_OPTIMISTIC_LOCKING_FAILURE,
						DataAccessExceptionType.PERMISSION_DENIED,
						DataAccessExceptionType.QUERY_TIMEOUT,
						DataAccessExceptionType.RECOVERABLE,
						DataAccessExceptionType.TRANSIENT_DATA_ACCESS_RESOURCE,
						DataAccessExceptionType.UNCATEGORIZED})
				.addResponseDescriptions(GeneralExceptionType.values());
		//Delete Exceptions
		ExceptionTypeDocBuilder deleteDocBuilder = new ExceptionTypeDocBuilder()
				.addResponseDescriptions(new ExceptionType[] {
						RestControllerExceptionType.TYPE_MISMATCH_EXCEPTION,
						DataAccessExceptionType.CLEANUP_FAILURE,
						DataAccessExceptionType.DATA_RETRIEVAL_FAILURE,
						DataAccessExceptionType.DATA_SOURCE_LOOKUP_FAILURE,
						DataAccessExceptionType.INVALID_DATA_ACCESS_API_USAGE,
						DataAccessExceptionType.INVALID_DATA_ACCESS_RESOURCE_USAGE,
						DataAccessExceptionType.NON_TRANSIENT_DATA_ACCESS_RESOURCE,
						DataAccessExceptionType.PERMISSION_DENIED,
						DataAccessExceptionType.QUERY_TIMEOUT,
						DataAccessExceptionType.RECOVERABLE,
						DataAccessExceptionType.TRANSIENT_DATA_ACCESS_RESOURCE,
						DataAccessExceptionType.UNCATEGORIZED
				})
				.addResponseDescriptions(GeneralExceptionType.values());
		docket.globalResponseMessage(RequestMethod.GET, getDocBuilder.getResponseMessages());
		docket.globalResponseMessage(RequestMethod.POST, postDocBuilder.getResponseMessages());
		docket.globalResponseMessage(RequestMethod.PUT, putDocBuilder.getResponseMessages());
		docket.globalResponseMessage(RequestMethod.DELETE, deleteDocBuilder.getResponseMessages());
	}
	
	private void addArchetypeGlobalParameters(Docket docket){
		ArrayList<Parameter> parameters = new ArrayList<>();
		parameters.add(new ParameterBuilder()
	                .name("lang")
	                .description(" ISO 639 language code to define the language which is used for messages")
	                .modelRef(new ModelRef("String"))
	                .parameterType("query")
	                .required(false)
	                .build());
		docket.globalOperationParameters(parameters);
	}
	
	private void setArchetypeApiInfo(Docket docket) {
		ExceptionTypeDocBuilder docBuilder = new ExceptionTypeDocBuilder();
		docBuilder
			.addResponseDescriptions(RestControllerExceptionType.values())
			.addResponseDescriptions(new ExceptionType[] {
					DataAccessExceptionType.CLEANUP_FAILURE,
					DataAccessExceptionType.DATA_INTEGRITY_VIOLATION,
					DataAccessExceptionType.DATA_RETRIEVAL_FAILURE,
					DataAccessExceptionType.DATA_SOURCE_LOOKUP_FAILURE,
					DataAccessExceptionType.DUPLICATE_KEY,
					DataAccessExceptionType.INVALID_DATA_ACCESS_API_USAGE,
					DataAccessExceptionType.INVALID_DATA_ACCESS_RESOURCE_USAGE,
					DataAccessExceptionType.NON_TRANSIENT_DATA_ACCESS_RESOURCE,
					DataAccessExceptionType.OBJECT_OPTIMISTIC_LOCKING_FAILURE,
					DataAccessExceptionType.PERMISSION_DENIED,
					DataAccessExceptionType.QUERY_TIMEOUT,
					DataAccessExceptionType.RECOVERABLE,
					DataAccessExceptionType.TRANSIENT_DATA_ACCESS_RESOURCE,
					DataAccessExceptionType.UNCATEGORIZED})
			.addResponseDescriptions(ValidationExceptionType.values())
			.addResponseDescriptions(GeneralExceptionType.values());
        docket
        	.apiInfo(new ApiInfoBuilder()
            .title("Metadata access REST API for the PostrgeSQL based SDBO")
            .description(
            	   "<p>Metadata access REST controllers</p>"
            	+ "<h3>Following a overview about possible Exceptions</h3>"
        		+ "<p>"+docBuilder.buildHtmlTable(false)+"</p>")
            .contact(new Contact("Slf Davos", "http://www.slf.ch", "nataliya.kryvych@epfl.ch"))
            .version(env.getProperty("system.version", String.class, ""))
            .build());
    }
	
}
