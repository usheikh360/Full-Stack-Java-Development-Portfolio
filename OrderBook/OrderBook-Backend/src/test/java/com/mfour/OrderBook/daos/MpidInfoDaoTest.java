package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.MpidInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MpidInfoDaoTest {

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
        tradesDao.deleteAll();
        userInfoDao.deleteAll();
    }

    @Test
    public void testAddGetMpidInfo(){
        MpidInfo mpidInfo = new MpidInfo();
        mpidInfo.setMpid("NSDQ");
        mpidInfo.setName("Nasdaq");

        mpidInfoDao.save(mpidInfo);

        MpidInfo testMpid = mpidInfoDao.findById(mpidInfo.getId()).get();

        assertEquals(mpidInfo,testMpid);
    }

    @Test
    public void testGetAllMpidInfo(){
        MpidInfo mpidInfo = new MpidInfo();
        mpidInfo.setMpid("NSDQ");
        mpidInfo.setName("Nasdaq");
        mpidInfoDao.save(mpidInfo);

        MpidInfo mpidInfo2 = new MpidInfo();
        mpidInfo2.setMpid("GOOG");
        mpidInfo2.setName("Google");
        mpidInfoDao.save(mpidInfo2);

        List<MpidInfo> mpidInfoList = mpidInfoDao.findAll();
        MpidInfo testMpidInfo = mpidInfoList.get(0);
        MpidInfo testMpidInfo2 = mpidInfoList.get(1);

        assertEquals(mpidInfo,testMpidInfo);
        assertEquals(mpidInfo2,testMpidInfo2);
    }

    @Test
    public void testUpdateMpidInfo(){
        MpidInfo mpidInfo = new MpidInfo();
        mpidInfo.setMpid("NSDQ");
        mpidInfo.setName("Nasdaq");
        mpidInfoDao.save(mpidInfo);

        mpidInfoDao.save(mpidInfo);
        MpidInfo initialMpid = mpidInfoDao.findById(mpidInfo.getId()).get();
        assertEquals(mpidInfo,initialMpid);

        mpidInfo.setName("Google");
        mpidInfo.setMpid("GOOG");
        mpidInfoDao.save(mpidInfo);

        MpidInfo editedMpid = mpidInfoDao.findById(mpidInfo.getId()).get();

        assertEquals(mpidInfo,editedMpid);

    }

    @Test
    public void testDeleteMpidInfo(){
        MpidInfo mpidInfo = new MpidInfo();
        mpidInfo.setMpid("NSDQ");
        mpidInfo.setName("Nasdaq");
        mpidInfoDao.save(mpidInfo);

        MpidInfo mpidInfo2 = new MpidInfo();
        mpidInfo2.setMpid("GOOG");
        mpidInfo2.setName("Google");
        mpidInfoDao.save(mpidInfo2);

        mpidInfoDao.delete(mpidInfo);

        assertEquals(mpidInfoDao.findAll().get(0),mpidInfo2);
    }

    @Test
    public void testgetMpidInfoByMpid(){
        MpidInfo mpidInfo = new MpidInfo();
        mpidInfo.setMpid("NSDQ");
        mpidInfo.setName("Nasdaq");

        mpidInfoDao.save(mpidInfo);

        MpidInfo testMpid = mpidInfoDao.getMpidInfoByMpid("NSDQ");

        assertEquals(mpidInfo,testMpid);

    }


}
