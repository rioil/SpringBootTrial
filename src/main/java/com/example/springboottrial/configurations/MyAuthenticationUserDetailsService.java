package com.example.springboottrial.configurations;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class MyAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        // MyPreAuthenticatedProcessingFilterで取り出したAuthorizationヘッダの値を取得
        var credential = token.getCredentials().toString();

        // ヘッダが適切に設定されていなければエラー
        // MEMO: MyPreAuthenticatedProcessingFilterはヘッダが設定されていないときに空文字列を返す
        if(credential.isEmpty()){
            throw new UsernameNotFoundException("Authorization header must not be empty.");
        }

        // TODO: 本番ではcredentialはbearer <token> の形で渡されるので，token部分をDBと照合して検証する
        switch (credential){
            case "key1":
                return new User("user", "", AuthorityUtils.createAuthorityList("GetItem"));
            case "key2":
                return new User("admin", "", AuthorityUtils.createAuthorityList("GetItem", "GetMembers"));
            default:
                throw new UsernameNotFoundException("Invalid Authorization header.");
        }
    }
}
