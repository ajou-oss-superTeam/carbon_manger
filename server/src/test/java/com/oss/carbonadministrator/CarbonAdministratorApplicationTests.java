package com.oss.carbonadministrator;

import com.oss.carbonadministrator.domain.User;
import com.oss.carbonadministrator.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@SpringBootTest
class CarbonAdministratorApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void dbLinkTest(){
		Optional<User> test = userRepository.findByEmail("dfldlk@gmail.com");
		if(!test.isPresent()) {
			fail("검색 결과 없어서 연결 테스트 불가");
			return;
		}
		User user = test.get();
		assertEquals("dfldlk@gmail.com", user.getEmail());
	}

}
