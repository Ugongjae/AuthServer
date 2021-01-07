package com.ChatClone.B.Oauth.Model;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.ChatClone.B.Oauth.Service.SerializableObjectConverter;

public class OAuthRefreshToken {
	private String id;
	private String tokenId;
	private String token;
	private String authentication;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public OAuth2RefreshToken getTokenObject() {
		return token != null ? SerializableObjectConverter.deserializeRefreshToken(token) : null;
	}

	public void setTokenObject(OAuth2RefreshToken token) {
		this.token = SerializableObjectConverter.serializeRefreshToken(token);
	}

	public OAuth2Authentication getAuthenticationObject() {
		return SerializableObjectConverter.deserializeAuthentication(authentication);
	}

	public void setAuthenticationObject(OAuth2Authentication authentication) {
		this.authentication = SerializableObjectConverter.serializeAuthentication(authentication);
	}

	public String getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
}
