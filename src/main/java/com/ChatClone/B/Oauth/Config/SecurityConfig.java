package com.ChatClone.B.Oauth.Config;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
/////////////////////  JPA 방식에서 사용했던 코드 //////////////////////
//	@Resource(name="userService")
//	private UserDetailsService userDetailsService;
//	
//	@Bean
//	@Override
//	protected AuthenticationManager authenticationManager() throws Exception{
//		return super.authenticationManager();
//	}
//	
//	@Bean
//	public PasswordEncoder encoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
//	
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(userDetailsService)
//			.passwordEncoder(encoder());
//	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http.cors()
//			.and()
//			.csrf()
//			.disable()
//			.anonymous()
//			.disable()
//			.authorizeRequests()
//			.antMatchers("/api-docs/**")
//			.permitAll();
//	}
	
	
	
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login")
			.and()
			.httpBasic();
		
//		makeAuthorizationRequestHeader();
	}
	
	private static void makeAuthorizationRequestHeader() {
		String oauthClientId = "client";
		String oauthClientSecret = "secret";

		Encoder encoder = Base64.getEncoder();
		try {
			String toEncodeString = String.format("%s:%s", oauthClientId, oauthClientSecret);
			String authorizationRequestHeader = "Basic " + encoder.encodeToString(toEncodeString.getBytes("UTF-8"));
//			log.debug("AuthorizationRequestHeader : [{}] ", authorizationRequestHeader);			// Y2xpZW50OnNlY3JldA==
		} catch (UnsupportedEncodingException e) {
		}
	}


	/**
	 * Need to configure this support password mode support password grant type
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
////////////////////////// In Memory 방식에서 사용한 코드 ////////////////////////////
	/*
	데이터베이스를 사용하므로 이 코드는 삭제처리합니다.
	@Bean
	public UserDetailsService userDetailsService() {
		PasswordEncoder encoder = passwordEncoder();
		String password = encoder.encode("pass");
		log.debug("PasswordEncoder password : [{}] ", password);					// {bcrypt}$2a$10$q6JJMlG7Q7Gt4n/76ydvp.Vk9pWVcTfCQ4NtWyBzNtWOmefYNw/wO

		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("user").password(password).roles("USER").build());
		manager.createUser(User.withUsername("admin").password("{noop}pass").roles("USER", "ADMIN").build());
		return manager;
	}
	*/

//////////////////////// JDBC를 사용하지만, Mybatis sql문을 사용하지 않았을 때 필요한 코드 //////////////////////////
	@Autowired
	DataSource dataSource;
	private JdbcUserDetailsManager userDetailsManager;

	// Enable jdbc authentication
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		this.userDetailsManager = auth
		.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select username, password, enabled from oauth_user_details where username = ?")
		.authoritiesByUsernameQuery("select username, authority from oauth_user_authorities where username = ?")
//		.rolePrefix("ROLE_")
		.getUserDetailsService();
//		필요할 경우 아래의 코드들을 주석해제합니다.
//		.userExistsSql("select username from oauth_user_details where username = ?")
//		.createUserSql("insert into oauth_user_details (username, password, enabled) values (?,?,?)")
//		.createAuthoritySql("insert into oauth_user_authorities (username, authority) values (?,?)")
//		.updateUserSql("update oauth_user_details set password = ?, enabled = ? where username = ?")
//		.deleteUserSql("delete from oauth_user_details where username = ?")
//		.deleteUserAuthoritiesSql("delete from oauth_user_authorities where username = ?");
	}
	
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
//		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//		jdbcUserDetailsManager.setDataSource(dataSource);
//		
//		this.userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from oauth_user_details where username = ?");
		this.userDetailsManager.setUserExistsSql("select username from oauth_user_details where username = ?");
		this.userDetailsManager.setCreateUserSql("insert into oauth_user_details (username, password, enabled) values (?,?,?)");
		this.userDetailsManager.setCreateAuthoritySql("insert into oauth_user_authorities (username, authority) values (?,?)");
		this.userDetailsManager.setUpdateUserSql("update oauth_user_details set password = ?, enabled = ? where username = ?");
		this.userDetailsManager.setDeleteUserSql("delete from oauth_user_details where username = ?");
		this.userDetailsManager.setDeleteUserAuthoritiesSql("delete from oauth_user_authorities where username = ?");
//		
//		return jdbcUserDetailsManager;
		return this.userDetailsManager;
	}

	
	
}
