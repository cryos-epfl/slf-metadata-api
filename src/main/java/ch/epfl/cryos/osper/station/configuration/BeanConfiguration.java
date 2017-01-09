package ch.epfl.cryos.osper.station.configuration;

import ch.slf.pro.common.util.converter.ConverterConfiguration;
import ch.slf.pro.common.util.exception.handler.demo.ExceptionDemoConfig;
import ch.slf.pro.common.util.localization.LocalizationService;
import ch.slf.pro.common.util.localization.LocalizationServiceImpl;
import ch.slf.pro.common.util.validator.PropertyValidator;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;

/**
 * Contains the spring managed beans which cannot be handled by spring autodetection.
 *
 * @author pertschy@slf.ch, Jun 11, 2015
 */
@Configuration
@Import({
        ConverterConfiguration.class,
        ExceptionDemoConfig.class
})
//@Import(ExceptionDemoConfig.class)
public class BeanConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BeanConfiguration.class);


    @Bean
    @Scope("request")
    public LocalizationService localizationService(){
        LocalizationService element = new LocalizationServiceImpl("messages");
        log.debug("Created element '{}'", element);
        return element;
    }


    @Bean
    @Scope("singleton")
    public MessageSource messageSource(){
        ResourceBundleMessageSource src = new ResourceBundleMessageSource();
        src.setBasenames(
                "messages",
                "ch.slf.pro.common.util.localization.messages.ValidationMessages",
                "ch.slf.pro.common.util.localization.messages.ExceptionMessages");
        return src;
    }

    @Bean
    @Scope("prototype")
    public PropertyValidator propertyValidator() {
        PropertyValidator element = new PropertyValidator();
        log.debug("Created element '{}'", element);
        return element;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean element = new LocalValidatorFactoryBean();
        element.setValidationMessageSource(messageSource());
        log.debug("Created element '{}'", element);
        return element;
    }

    @Bean
    public Module guavaModule() {
        return new GuavaModule();
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);

    }


}
