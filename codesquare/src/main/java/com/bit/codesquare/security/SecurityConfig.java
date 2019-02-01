package com.bit.codesquare.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	LoginUserDetailsService userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	/* Password Encoder 등록 */
	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	/* 인증방식 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

//	    /* Security 제외 패턴 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resource/**");
	}

	/* 각종 시큐어 패턴등록 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

				.authorizeRequests() /* 인증 요청 선언?????? */


				.antMatchers("/", "/member/login", "/signUp", "/member/signUp", "/logout").permitAll()
				//.antMatchers("/member/**").authenticated() // 로그인 하면 다 가능
				.antMatchers("/member/modifyInstructorInfo").hasAnyRole("2") // 특정 권한 지정
				// .antMatchers("/modifyInstructorInfo").hasAnyRole(roles)
				.and().csrf().and()

				.formLogin() /* 로그인 폼 나오도록 */
				.loginPage("/member/login") /* 내가 만든 로그인 페이지 */
				.usernameParameter("userId") /* username 을 대체할 아이디 param default username */
				.permitAll() /* 모두 오픈 ( 반대는 denyAll() ) */
				.defaultSuccessUrl("/").failureUrl("/member/login?error").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
				.logoutSuccessUrl("/"); /* 로그아웃 성공시 리다이렉트 url */
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

//	private Filter ssoFilter() {
//		CompositeFilter filter = new CompositeFilter();
//		List<Filter> filters = new ArrayList<>();
//		filters.add(ssoFilter(google(), new GoogleOAuth2ClientAuthenticationProcessingFilter(socialService)));
//		filters.add(ssoFilter(facebook(), new FacebookOAuth2ClientAuthenticationProcessingFilter(socialService)));
//		filter.setFilters(filters);
//		return filter;
//	}
//
//	private Filter ssoFilter(ClientResources client, OAuth2ClientAuthenticationProcessingFilter filter) {
//		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
//		filter.setRestTemplate(restTemplate);
//		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(),
//				client.getClient().getClientId());
//		filter.setTokenServices(tokenServices);
//		tokenServices.setRestTemplate(restTemplate);
//		return filter;
//	}

}
