package com.mfour.OrderBook.service;

import com.mfour.OrderBook.daos.UserInfoDao;
import com.mfour.OrderBook.entities.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserInfoDao userInfoDao;

    public UserInfo login(String username, String password) throws ExceptionHandler {
        UserInfo retrievedUser;

        if (username == null) {
            throw new NullPointerException("Please enter a username");
        }else if (password == null) {
            throw new NullPointerException("Please enter a password");
        }

        retrievedUser = userInfoDao.getByUsername(username);
        if (retrievedUser == null) {
            throw new ExceptionHandler("Invalid Username - username does not exist");
        }

        if (!password.equals(retrievedUser.getPassword())) {
            throw new ExceptionHandler("Login attempt failed - Please try again");
        }
        return retrievedUser;
    }

    public String createNewUser(String username, String password) throws ExceptionHandler {

        if(username.isEmpty() || username.isBlank() || username == null) {
            throw new ExceptionHandler("Please enter a username");
        }

        UserInfo retrievedUser = userInfoDao.getByUsername(username);
        if (retrievedUser != null) {
            throw new ExceptionHandler("Invalid Username - username already exists");
        }

        if (password.length() < 8) {
            throw new ExceptionHandler("Invalid Password - password is less than 8 characters");
        }

        boolean containsCapitalLetter = false;

        for (int i=0 ; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                containsCapitalLetter = true;
            }
        }

        if (!containsCapitalLetter) {
            throw new ExceptionHandler("Invalid password  - does not contain a capital letter");
        }

        UserInfo newUser = new UserInfo();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userInfoDao.save(newUser);
        return "SUCCESS - user created";
    }
}
