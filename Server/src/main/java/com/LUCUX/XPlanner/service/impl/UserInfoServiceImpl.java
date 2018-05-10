package com.LUCUX.XPlanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.model.UserInfo;
import com.LUCUX.XPlanner.repository.UserInfoRepository;
import com.LUCUX.XPlanner.service.UserInfoService;

@Service("userInfoService")
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	@Transactional
	public UserInfo saveUserInfoIfNotFound(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userInfoRepository.save(userInfo);
		return userInfo;
	}

}
