package com.ChatClone.B.Oauth.Service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ChatClone.B.Oauth.Model.OAuthClientDetails;

@Repository("oauthClientDetailsDAO")
public class OAuthClientDetailsDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public OAuthClientDetails getClientById(String username) {
		return sqlSession.selectOne("client.selectClientById", username);
	}
}
