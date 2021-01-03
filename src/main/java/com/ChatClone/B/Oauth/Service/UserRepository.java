package com.ChatClone.B.Oauth.Service;

import com.ChatClone.B.Oauth.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
