package com.ChatClone.B.Oauth.Service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ChatClone.B.Oauth.Model.OAuthUserDetails;

@Repository("oauthUserDetailsDAO")
public class OAuthUserDetailsDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public OAuthUserDetails getUserById(String username) {
		return sqlSession.selectOne("user.selectUserById",username);
	}
}
