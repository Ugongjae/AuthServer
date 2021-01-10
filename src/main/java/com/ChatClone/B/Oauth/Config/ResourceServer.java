package com.ChatClone.B.Oauth.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().anyRequest().authenticated()
		.and()
		.requestMatchers().antMatchers("/api/**");
		
//		http.anonymous().disable()
//			.authorizeRequests()
//			.antMatchers("/users/**").authenticated()
//			.and()
//			.exceptionHandling()
//			.accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
	@Primary
	@Bean
	public RemoteTokenServices tokenService() {
		RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
		tokenService.setClientId("client");
		tokenService.setClientSecret("secret");
		return tokenService;
	}
}
