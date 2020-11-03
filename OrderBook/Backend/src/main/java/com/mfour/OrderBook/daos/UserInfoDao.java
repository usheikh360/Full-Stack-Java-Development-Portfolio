package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.UserInfo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Configurable
public interface UserInfoDao extends JpaRepository<UserInfo,Integer> {

    @Query(value = "SELECT * FROM userinfo u WHERE u.username = :username",nativeQuery = true)
    UserInfo getByUsername(@Param("username")String username);
}
