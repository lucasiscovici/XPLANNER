package com.LUCUX.XPlanner.conf;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.LUCUX.XPlanner.model.Token;
import com.LUCUX.XPlanner.model.User;
import com.LUCUX.XPlanner.security.CustomUserDetailsService;
import com.LUCUX.XPlanner.service.UserService;
import com.LUCUX.XPlanner.util.TokenUtil;

public class AuthTokenFilter extends GenericFilterBean {
	private UserService us;
	private CustomUserDetailsService customUserDetailsService;
	public static final String authTokenHeaderName = "x-auth-token";

	public AuthTokenFilter(CustomUserDetailsService userDetailsService,UserService us) {
		this.customUserDetailsService = userDetailsService;
		this.us=us;
	}
	// public  char name(char a) {
	// 	String str1 = new String("āáǎàâêēéěèīíǐìîōóǒòôūúǔùǖǘǚǜûÂĀÁǍÀĒÉĚÈÊĪÍǏÌÎÔŌÓǑÒŪÚǓÙǕǗǙǛÛçÇ");
	// 	String str2 = new String("aaaaaeeeeeiiiiiooooouuuuuuuuuAAAAAEEEEEIIIIIOOOOOUUUUUUUUUcC");
	// 	int indexInStr1  = str1.indexOf(a);
	// 	if (indexInStr1 == -1 ) return a;
	// 	return str2.charAt(indexInStr1);
		
	// }
	// public  String CesarTo(String phrase) {
	// 	String o="";
	// 	for (int i = 0; i < phrase.length(); i++) {
	// 		o+=name(phrase.charAt(i));
	// 	}
	// 	return o;
	// }
	
	// public static void main(String[] args) {
	// 	String v = "ma chàine accenté";
	// 	System.out.println(AuthTokenFilter.CesarTo(v));
		
	// }
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String authToken = httpServletRequest.getHeader(authTokenHeaderName);

			if (StringUtils.hasText(authToken)) {
				String username = TokenUtil.getUserNameFromToken(authToken);

				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				User user = us.findByUsernameOrEmail(username);
				if (TokenUtil.validateToken(authToken, userDetails,user.tokens)) {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
							userDetails.getPassword(), userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(token);
				}else {
					if(user!=null){
						System.out.println(user.tokens);
						Optional<Token> u= user.tokens.stream().filter(list -> list.getToken().equals(authToken)).findFirst();
						if (u.isPresent()) {
							
								u.get().setValable(false);
						
						us.save(user);
						}
					}
				}
			}

			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
	}
}
