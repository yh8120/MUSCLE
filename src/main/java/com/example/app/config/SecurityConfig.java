package com.example.app.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.app.config.handler.CustomLogoutHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.formLogin().loginProcessingUrl("/login")
				.usernameParameter("email")
				.passwordParameter("loginPass")
				.loginPage("/login")
				.defaultSuccessUrl("/training", false)
				.failureUrl("/login")
				.permitAll();

		http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
				.addLogoutHandler(new CustomLogoutHandler())
				.invalidateHttpSession(true);

		http.authorizeHttpRequests()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.mvcMatchers("/login*", "/accounts/**").permitAll()
				.anyRequest().authenticated();
		
//		http
//		.csrf(csrf -> csrf.disable());

		return http.build();
	}

	//	@Bean
	//	ServletWebServerFactory servletContainer() {
	//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {@Override
	//			protected void postProcessContext(Context context) {
	//				SecurityConstraint securityConstraint = new SecurityConstraint();
	//				securityConstraint.setUserConstraint("CONFIDENTIAL");
	//				SecurityCollection collection = new SecurityCollection();
	//				collection.addPattern("/*");
	//				securityConstraint.addCollection(collection);
	//				context.addConstraint(securityConstraint);
	//			}
	//		};
	//		tomcat.addAdditionalTomcatConnectors(redirectConnector());
	//		return tomcat;
	//	}
	//	
	//	private Connector redirectConnector() {
	//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	//		connector.setScheme("http");
	//		connector.setPort(8080);
	//		connector.setSecure(false);
	//		connector.setRedirectPort(8443);
	//		return connector;
	//	}

}
