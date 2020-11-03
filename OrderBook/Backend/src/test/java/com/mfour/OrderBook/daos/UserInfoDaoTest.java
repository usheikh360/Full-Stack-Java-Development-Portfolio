package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.MpidInfo;
import com.mfour.OrderBook.entities.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoDaoTest {

    @Autowired
    MpidInfoDao mpidInfoDao;

    @Autowired
    OrdersDao ordersDao;

    @Autowired
    StockDao stockDao;

    @Autowired
    TradesDao tradesDao;

    @Autowired
    UserInfoDao userInfoDao;

    @BeforeEach
    void setUp() {
        mpidInfoDao.deleteAll();
        ordersDao.deleteAll();
        stockDao.deleteAll();
        userInfoDao.deleteAll();
    }

    @Test
    public void testAddGetUser() {
        List<UserInfo> retrievedList = userInfoDao.findAll();
        assertEquals(0, retrievedList.size());

        //ARRANGE
        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");

        //ACT
        userInfoDao.save(testUser);
        int savedId = testUser.getId();

        UserInfo retrievedUser = userInfoDao.findById(savedId).get();

        //ASSERT
        assertEquals(testUser, retrievedUser);
    }

    @Test
    public void testGetAllUsers() {

        //ARRANGE
        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");
        userInfoDao.save(testUser);

        UserInfo testUserTwo = new UserInfo();
        testUserTwo.setUsername("Test User 2");
        testUserTwo.setPassword("abcdef");
        userInfoDao.save(testUserTwo);

        //ACT
        List<UserInfo> heroAndVillains = userInfoDao.findAll();

        //ASSERT
        assertEquals(2, heroAndVillains.size());
        assertTrue(heroAndVillains.contains(testUser));
        assertTrue(heroAndVillains.contains(testUserTwo));
    }

    @Test
    public void testUpdateUsers() {

        //ARRANGE
        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");

        userInfoDao.save(testUser);
        int savedId = testUser.getId();

        //ACT ASSERT BEFORE UPDATE
        UserInfo retrievedUserFromDao = userInfoDao.findById(savedId).get();
        assertEquals(testUser, retrievedUserFromDao);

        //ACT ASSERT DURING UPDATE
        testUser.setPassword("hello");
        userInfoDao.save(testUser);
        assertNotEquals(testUser, retrievedUserFromDao);

        //ACT AND ASSERT AFTER UPDATE
        retrievedUserFromDao = userInfoDao.findById(savedId).get();
        assertEquals(testUser, retrievedUserFromDao);
    }

    @Test
    public void testDeleteUser() {

        //ARRANGE
        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");

        userInfoDao.save(testUser);
        int savedId = testUser.getId();

        //ACT ASSERT BEFORE DELETE
        UserInfo retrievedUserFromDao = userInfoDao.findById(savedId).get();
        assertEquals(testUser, retrievedUserFromDao);

        //ACT ASSERT AFTER DELETE
        userInfoDao.delete(testUser);
        List<UserInfo> retrievedHeroListFromDao = userInfoDao.findAll();
        assertEquals(0, retrievedHeroListFromDao.size());

        assertTrue(userInfoDao.findById(savedId).isEmpty());
    }

    @Test
    public void testGetByUsername() {

        //ARRANGE
        UserInfo testUser = new UserInfo();
        testUser.setUsername("Test User");
        testUser.setPassword("123456");

        //ACT
        userInfoDao.save(testUser);

        UserInfo retrievedUser = userInfoDao.getByUsername("Test User");

        //ASSERT
        assertEquals(testUser, retrievedUser);
    }
}