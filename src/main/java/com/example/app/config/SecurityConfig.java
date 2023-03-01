package com.example.app.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.formLogin(login -> login
						.loginProcessingUrl("/login")//ログインURL
						.usernameParameter("email")//username用formのname属性
						.passwordParameter("loginPass")//password用formのname属性
						.loginPage("/login")//ログインURL
						.defaultSuccessUrl("/training", false)//デフォルトのログイン遷移先。第二引数がfalseの場合直前に開こうとしていたページへの遷移が優先される
						.failureUrl("/login")//ログイン失敗時の遷移先
						.permitAll())

				.rememberMe(rem -> rem
						.key("uniqueKeyAndSecret")
						.rememberMeParameter("remember-me")//rememberMe-formのname属性
						.rememberMeCookieName("remember-me")//cookie名
						.tokenValiditySeconds(86400)//rememmber-me有効秒数
						.tokenRepository(new InMemoryTokenRepositoryImpl())//アクセスごとにcookieのシリーズを更新
						.useSecureCookie(true)//https専用cookie
				)

				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))//ログアウトリクエストのURL
						.deleteCookies("remember-me")//cookie削除
						.addLogoutHandler(new CustomLogoutHandler())//ログアウト時にクエリパラメータをリダイレクトに受け渡すハンドラー
						.invalidateHttpSession(true)//セッション破棄
				)

				.authorizeHttpRequests(auth -> auth
						.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()//staticディレクトリは許可
						.mvcMatchers("/login*", "/accounts/**").permitAll()//マッチするリクエストの許可
						.anyRequest().authenticated()//上記以外のリクエストは要認証
				)

				.headers(headers -> headers
						.frameOptions(frameOptions -> frameOptions.sameOrigin())//X-Frame-Options: SAMEORIGIN クリックジャッキング対策
						.contentSecurityPolicy(csp -> csp.policyDirectives("script-src 'self'"))//Content-Security-Policy 同一オリジンからのJSのみ許可(XSS対策)
				)

				.requiresChannel(req -> req
						.anyRequest().requiresSecure()//httpスキームのセキュアリダイレクト
				)

				.csrf(csrf -> csrf
						.ignoringAntMatchers("/ws/**", "/user/**", "/topic/notice/**"))
				;
		
		
		return http.build();
	}

	//		@Bean
	//		ServletWebServerFactory servletContainer() {
	//			TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
	//				
	//				@Override
	//				protected void postProcessContext(Context context) {
	//					SecurityConstraint securityConstraint = new SecurityConstraint();
	//					securityConstraint.setUserConstraint("CONFIDENTIAL");
	//					SecurityCollection collection = new SecurityCollection();
	//					collection.addPattern("/*");
	//					securityConstraint.addCollection(collection);
	//					context.addConstraint(securityConstraint);
	//				}
	//			};
	//			tomcat.addAdditionalTomcatConnectors(redirectConnector());
	//			return tomcat;
	//		}
	//		
	//		//Connector設定
	//		private Connector redirectConnector() {
	//			Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	//			connector.setScheme("http");//スキーム指定
	//			connector.setPort(8080);//受信ポート
	//			connector.setSecure(false);//上記接続のセキュア接続フラグ false->セキュアに転送
	//			connector.setRedirectPort(8443);//リダイレクトポート
	//			return connector;
	//		}

}
