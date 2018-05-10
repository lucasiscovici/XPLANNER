package com.LUCUX.XPlanner.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.LUCUX.XPlanner.conf.Configuration;
import com.LUCUX.XPlanner.model.UserInfo;


public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	
	@Query("select u from "+Configuration.userinfosC+" u where :what=:o")
	UserInfo findBy(@Param("what") String what,@Param("o") String o);
}
