package com.LUCUX.XPlanner.service;

import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.model.UserInfo;

public interface UserInfoService {

	UserInfo saveUserInfoIfNotFound(UserInfo userInfo);
}
