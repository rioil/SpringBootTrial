package com.example.springboottrial.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            // /greeting 以下は認証不要にする
            try {
                auth.mvcMatchers("/greeting/**", "/users", "/login").permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .addFilter(createMyPreAuthenticatedProcessingFilter())
                        .authenticationProvider(createPreAuthenticatedAuthenticationProvider())
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // MEMO: CSRFを切らないとPOSTリクエストがすべて403になる
        http.csrf().disable();

        return http.build();
    }

    public MyPreAuthenticatedProcessingFilter createMyPreAuthenticatedProcessingFilter(){
        var filter = new MyPreAuthenticatedProcessingFilter();
        filter.setAuthenticationManager(createAuthenticationManger());

        return filter;
    }

    @Bean
    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> createAuthenticationUserDetailsService(){
        return new MyAuthenticationUserDetailsService();
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider createPreAuthenticatedAuthenticationProvider(){
        var provider =  new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(createAuthenticationUserDetailsService());
        provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());

        return provider;
    }

    // TODO: これで正しいのかは要確認
    @Bean
    public AuthenticationManager createAuthenticationManger(){
        return new ProviderManager(createPreAuthenticatedAuthenticationProvider());
    }
}
