package com.LUCUX.XPlanner.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import com.LUCUX.XPlanner.model.Token;
import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.service.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class TokenUtil { 

	private static UserService userService;
	public static final String MAGIC_KEY = "LUCUX";
	public static final long expiration = TK._1H_sec; // EXPIRATION_TIME

	public static String createToken(UserDetails userDetails) {
		long expires = System.currentTimeMillis() + 1000L * TokenUtil.expiration;
		return userDetails.getUsername() + ":" + expires + ":" + computeSignature(userDetails, expires);
	}

	public static String computeSignature(UserDetails userDetails, long expires) {
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(userDetails.getUsername()).append(":");
		signatureBuilder.append(expires).append(":");
		signatureBuilder.append(userDetails.getPassword()).append(":");
		signatureBuilder.append(TokenUtil.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}
		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}

	public static String getUserNameFromToken(String authToken) {
		if (authToken == null) {
			return null;
		}
		String[] parts = authToken.split(":");
		return parts[0];
	}

	public static boolean validateToken(String authToken, UserDetails userDetails,List<Token> tokens) {
		String[] parts = authToken.split(":");
		long expires = Long.parseLong(parts[1]);
		String signature = parts[2];
		String signatureToMatch = computeSignature(userDetails, expires);
		return  expires >= System.currentTimeMillis() && signature.equals(signatureToMatch) && tokens.stream()
				.filter(line -> line.getToken().equals(authToken) && line.getValable() ).count()>0;
		
	}
}
