package com.mrprez.gencross.online.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mrprez.gencross.online.dao.UserDao;
import com.mrprez.gencross.online.exception.EmailAlreadyExistException;
import com.mrprez.gencross.online.exception.UsernameAlreadyExistException;
import com.mrprez.gencross.online.model.User;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserDao userDao;
	@Mock
	private PasswordEncoder passwordEncoder;
	
	
	
	@Test
	public void createUser() throws NoSuchAlgorithmException, InvalidKeySpecException, UsernameAlreadyExistException, EmailAlreadyExistException {
		// GIVEN
		Mockito.when(passwordEncoder.encode("myPassword")).thenReturn("myHash");
		
		// WHEN
		userService.createUser("myUser", "myemail@test.com", "myPassword");
		
		// THEN
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		Mockito.verify(userDao).createUser(userCaptor.capture(), Mockito.eq("myHash"));
		Assertions.assertThat(userCaptor.getValue().getUsername()).isEqualTo("myUser");
		Assertions.assertThat(userCaptor.getValue().getEmail()).isEqualTo("myemail@test.com");
	}

	@Test
	public void createUser_usernameAlreadyExists() throws NoSuchAlgorithmException, InvalidKeySpecException, UsernameAlreadyExistException, EmailAlreadyExistException {
		// GIVEN
		Mockito.when(userDao.getFromUsername("myUser")).thenReturn(new User());
		
		// WHEN
		assertThrows(
				UsernameAlreadyExistException.class, 
				() -> userService.createUser("myUser", "myemail@test.com", "myPassword"));
	}

	@Test
	public void createUser_emailAlreadyExists() throws NoSuchAlgorithmException, InvalidKeySpecException, UsernameAlreadyExistException, EmailAlreadyExistException {
		// GIVEN
		Mockito.when(userDao.getFromEmail("myemail@test.com")).thenReturn(new User());
		
		// WHEN
		assertThrows(
				EmailAlreadyExistException.class, 
				() -> userService.createUser("myUser", "myemail@test.com", "myPassword"));
	}
}
