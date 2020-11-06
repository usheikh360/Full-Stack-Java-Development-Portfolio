package com.mfour.OrderBook.service;

import com.mfour.OrderBook.daos.UserInfoDao;
import com.mfour.OrderBook.entities.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserInfoDao userInfoDao;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
        userInfoDao.deleteAll();

        UserInfo testUser = new UserInfo();
        testUser.setUsername("TestUser1");
        testUser.setPassword("A1234567");
        userInfoDao.save(testUser);

    }

    @Test
    void loginWithNullUsername() throws ExceptionHandler {

        //ACT and ASSERT
        try {
            userService.login(null, "A234567");
        } catch (NullPointerException e) {
            assertEquals("Please enter a username", e.getMessage());
        }
    }

    @Test
    void loginWithNullPassword() throws ExceptionHandler {

        //ACT and ASSERT
        try {
            userService.login("TestUser", null);
        } catch (NullPointerException e) {
            assertEquals("Please enter a password", e.getMessage());
        }
    }

    @Test
    void loginWithUsernameThatDoesNotExist() {

        //ACT and ASSERT
        try {
            userService.login("testUserFake", "A1234567");
        } catch (ExceptionHandler e) {
            assertEquals("Invalid Username - username does not exist", e.getMessage());
        }
    }

    @Test
    void loginWithValidCredentials() throws ExceptionHandler {

        //ACT and ASSERT
        UserInfo retrievedUserFromDB = userService.login("TestUser1","A1234567");
        assertEquals(retrievedUserFromDB.getUsername(), "TestUser1");
        assertEquals(retrievedUserFromDB.getPassword(), "A1234567");
    }

    @Test
    void createNewUserWithInvalidUsername() {

        //ACT and ASSERT
        try {
            userService.createNewUser("", "A234567");
        } catch (ExceptionHandler e) {
            assertEquals("Please enter a username", e.getMessage());
        }
    }

    @Test
    void createNewUserWithInvalidPasswordCharacters() {
        try {
            userService.createNewUser("NewUser1", "12345");
        } catch (ExceptionHandler e) {
            assertEquals("Invalid Password - password is less than 8 characters", e.getMessage());
        }
    }

    @Test
    void createNewUserThatAlreadyExists() {
        try {
            userService.createNewUser("TestUser1", "A1234567");
        } catch (ExceptionHandler e) {
            assertEquals("Invalid Username - username already exists", e.getMessage());
        }
    }

    @Test
    void createNewUserWithInvalidPasswordCapitalLetter() {
        try {
            userService.createNewUser("NewUser1", "12345678");
        } catch (ExceptionHandler e) {
            assertEquals("Invalid password  - does not contain a capital letter", e.getMessage());
        }
    }

    @Test
    void createNewUserValidInputs() throws ExceptionHandler {
        String result = userService.createNewUser("NewUser3", "A1234567");
        assertEquals("SUCCESS - user created", result);
    }
}