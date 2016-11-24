package ch.epfl.cryos.osper.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * @author pertschy@slf.ch, Jun 14, 2016
 *
 */
@Configuration
@Profile("insecure")
public class WebSecurityConfigurationPermitAll extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").permitAll();
		http.csrf().disable();
	}

}

