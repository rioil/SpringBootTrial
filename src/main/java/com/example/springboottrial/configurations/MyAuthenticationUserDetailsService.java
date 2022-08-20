package com.example.springboottrial.configurations;

import com.example.springboottrial.auth.EmptyCredentialException;
import com.example.springboottrial.auth.JwtUtil;
import com.example.springboottrial.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class MyAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        // ヘッダが適切に設定されていなければエラー
        if (token.getCredentials() == null) {
            throw new EmptyCredentialException("Authorization header must not be empty");
        }

        // MyPreAuthenticatedProcessingFilterで取り出したAuthorizationヘッダの値を取得
        var credential = token.getCredentials().toString();

        // credentialはBearer <token> という形式なので，token部分を取り出す
        var splits = credential.split(" ");
        if (splits.length != 2 || !splits[0].equals("bearer")) {
            throw new BadCredentialsException("Invalid Authorization header.");
        }
        var jwt = splits[1];

        // トークンを検証
        var result = JwtUtil.validateToken(jwt);

        var user = userService.find(result.userid());
        if (user == null) {
            throw new UsernameNotFoundException("Invalid Authorization header.");
        }

        // 権限はAccessを仮指定しておく
        var defaultAuthorities = AuthorityUtils.createAuthorityList("Access");

        // ユーザー情報を作成
        var userInfo = new User(user.getDisplayName(), "", defaultAuthorities);

        return userInfo;
    }
}
