package com.LUCUX.XPlanner.DAO;


import org.springframework.data.repository.CrudRepository;

import com.LUCUX.XPlanner.model.UserInfo;

public interface UserInfoDAO extends CrudRepository<UserInfo, Long> { }