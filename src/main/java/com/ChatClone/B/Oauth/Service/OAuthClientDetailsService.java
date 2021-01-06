package com.ChatClone.B.Oauth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.ChatClone.B.Oauth.Model.OAuthClientDetails;

@Service("oauthClientDetailsService")
public class OAuthClientDetailsService implements ClientDetailsService{
	@Autowired
	@Qualifier("oauthClientDetailsDAO")
	private OAuthClientDetailsDAO clientDetailsDAO;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException{
		OAuthClientDetails client = clientDetailsDAO.getClientById(clientId);
		
		if(client==null) {
			throw new ClientRegistrationException(clientId);
		}
		
		return client;
	}
}
