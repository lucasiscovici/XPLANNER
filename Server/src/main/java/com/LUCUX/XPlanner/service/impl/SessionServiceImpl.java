package com.LUCUX.XPlanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LUCUX.XPlanner.model.Session;
import com.LUCUX.XPlanner.repository.SessionRepository;
import com.LUCUX.XPlanner.service.SessionService;

@Service("sessionService")
@Transactional
public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionRepository sessionRepository;

}
