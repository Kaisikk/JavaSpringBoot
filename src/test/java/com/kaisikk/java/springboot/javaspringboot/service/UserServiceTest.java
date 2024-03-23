package com.kaisikk.java.springboot.javaspringboot.service;

import com.kaisikk.java.springboot.javaspringboot.domain.Role;
import com.kaisikk.java.springboot.javaspringboot.domain.User;
import com.kaisikk.java.springboot.javaspringboot.repos.UserRepository;
import junit.framework.TestCase;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest extends TestCase {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testAddUser() {

        User user = new User();
        user.setEmail("TestEmail");

        boolean isCreated = userService.addUser(user);
        Assert.assertTrue(isCreated);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void addUserFailTest() {

        User user = new User();

        user.setUsername("admin");

        Mockito.doReturn(new User())
                .when(userRepository)
                .findByUsername("admin");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertFalse(isUserCreated);

        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender, Mockito.times(0))
                .send(ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    public void testActivateUser() {

        User user = new User();

        user.setActivationCode("bingo!");

        Mockito.doReturn(user)
                .when(userRepository)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        Assert.assertTrue(isUserActivated);
        Assert.assertNull(user.getActivationCode());

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void activateUserFailTest() {

        boolean isUserActivated = userService.activateUser("activate me");

        Assert.assertFalse(isUserActivated);

        Mockito.verify(userRepository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));

    }
}