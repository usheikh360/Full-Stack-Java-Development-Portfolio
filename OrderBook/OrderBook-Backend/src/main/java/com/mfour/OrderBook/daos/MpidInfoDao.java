package com.mfour.OrderBook.daos;

import com.mfour.OrderBook.entities.MpidInfo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Configurable
public interface MpidInfoDao extends JpaRepository<MpidInfo,Integer> {

    @Query(value = "SELECT * FROM mpidinfo m WHERE m.mpid = :mpid", nativeQuery = true)
    MpidInfo getMpidInfoByMpid(@Param("mpid")String mpid);

}
