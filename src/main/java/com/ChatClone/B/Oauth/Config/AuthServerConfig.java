package com.ChatClone.B.Oauth.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


@EnableAuthorizationServer
@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AuthorizationCodeServices authorizationCodeServices;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
		clients.jdbc(dataSource);
		
//		clients.inMemory()
//			.withClient("iamclient")
//			.secret(passwordEncoder.encode("iamsecret"))
//			.authorizedGrantTypes("authorization_code","password","refresh_token")
//			.scopes("read","write")
//			.accessTokenValiditySeconds(60*60)
//			.refreshTokenValiditySeconds((6*60*60))
//			.autoApprove(true);
	}
	
//	@Bean
//	public AuthorizationCodeServices jdbcAuthorizationCodeServices(DataSource dataSource) {
//		return new JdbcAuthorizationCodeServices(dataSource);
//	}
	
	@Bean
	@Primary
	public ClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
		return new JdbcClientDetailsService(dataSource);
	}
	

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
	
	@Bean
    public ApprovalStore approvalStore() { //(3)
        return new JdbcApprovalStore(dataSource);
    }
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
//		endpoints.tokenStore(tokenStore)
//			.authenticationManager(authenticationManager)
//			.userDetailsService(userDetailsService);
		
		endpoints //(4)
        .approvalStore(approvalStore())
        .tokenStore(tokenStore())
        .authenticationManager(authenticationManager)
//		.authorizationCodeServices(authorizationCodeServices)
;
	}
	
	
	
	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private AuthorizationCodeServices authorizationCodeServices;
//	
//	@Autowired
//	private ApprovalStore approvalStore;
//
//	@Autowired
//	private TokenStore tokenStore;
//
//	@Bean
//	public AuthorizationCodeServices jdbcAuthorizationCodeServices(DataSource dataSource) {
//		return new JdbcAuthorizationCodeServices(dataSource);
//	}
//	
//	@Bean
//	public ApprovalStore jdbcApprovalStore(DataSource dataSource) {
//		return new JdbcApprovalStore(dataSource);
//	}
//
//	@Bean
//	public TokenStore jdbcTokenStore(DataSource dataSource) {
//		return new CustomJdbcTokenStore(dataSource);
//	}
//	
//	@Bean
//	@Primary
//	public ClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
//		return new JdbcClientDetailsService(dataSource);
//	}
//
///*
//데이터베이스를 사용하여 사용자를 관리하므로 이 코드는 삭제처리합니다.
//	@Autowired
//	@Qualifier("userDetailsService")
//	private UserDetailsService userDetailsService;
//*/	
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints
//			.authenticationManager(authenticationManager)
//			.authorizationCodeServices(authorizationCodeServices)
//			.tokenStore(tokenStore)
//			.approvalStore(approvalStore)
////			.userDetailsService(userDetailsService)
//			;
//	}
//	
//	class CustomJdbcTokenStore extends JdbcTokenStore {
//		public CustomJdbcTokenStore(DataSource dataSource) {
//			super(dataSource);
//		}
//
//		@Override
//		public OAuth2AccessToken readAccessToken(String tokenValue) {
//			OAuth2AccessToken accessToken = null;
//
//			try {
//				accessToken = new DefaultOAuth2AccessToken(tokenValue);
//			} catch (EmptyResultDataAccessException e) {
//			} catch (IllegalArgumentException e) {
//				removeAccessToken(tokenValue);
//			}
//
//			return accessToken;
//		}
//	}
}
