package com.ChatClone.B.Oauth.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ChatClone.B.Oauth.Model.OAuthUserDetails;

@Service("oauthUserDetailsService")
public class OAuthUserDetailsService implements UserDetailsService{
	@Autowired
	@Qualifier("oauthUserDetailsDAO")
	private OAuthUserDetailsDAO userAuthDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		OAuthUserDetails user = userAuthDAO.getUserById(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
}
