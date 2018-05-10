package com.LUCUX.XPlanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.LUCUX.XPlanner.model.Token;
import com.LUCUX.XPlanner.repository.TokenRepository;
import com.LUCUX.XPlanner.service.TokenService;

@Service("tokenService")
@Transactional
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenRepository tokenRepository;

}
