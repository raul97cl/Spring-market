package com.market.repositositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.market.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findFirstByEmail(String email);
}
