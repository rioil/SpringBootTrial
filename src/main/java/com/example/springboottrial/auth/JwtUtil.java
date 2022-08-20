package com.example.springboottrial.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Date;

public final class JwtUtil {
    /**
     * 署名鍵
     * 他のプロジェクトでは使用するときは必ず変更する
     *
     * @implNote 署名鍵には署名アルゴリズムのビット長以上の十分長いものを使用する
     */
    private static final String SIGNING_KEY = "+@yL;#cxcbR{FS5n>Cy>Umb!7)nPeoSX9RTU7s;O[x;(AeWsH85;dkoAH6SNWFGx";

    /**
     * トークンを作成します．
     *
     * @param userid ユーザーID
     * @return 作成したトークン
     */
    public static String createToken(String userid) {
        // 有効時間は10分（ミリ秒で指定）
        final long VALID_DURATION_MS = 10 * 60 * 1000L;

        Date issuedAt = new Date();
        Date notBefore = issuedAt;
        Date expiresAt = new Date(issuedAt.getTime() + VALID_DURATION_MS);

        var algorithm = Algorithm.HMAC256(SIGNING_KEY);
        var token = JWT.create()
                .withSubject(userid)
                .withIssuedAt(issuedAt)
                .withNotBefore(notBefore)
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return token;
    }

    /**
     * トークンの検証を行います．
     * トークンで指定されたuseridを持つユーザーが存在するかは確認しません．
     *
     * @param token トークン
     * @return トークンの検証結果
     */
    public static TokenValidationResult validateToken(String token) {
        var algorithm = Algorithm.HMAC256(SIGNING_KEY);
        var verifier = JWT.require(algorithm)
                .withClaimPresence("sub")
                .build();

        // JWTをデコード
        DecodedJWT decodedJWT;
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new BadCredentialsException("Invalid token.", ex);
        }

        // ユーザーIDの形式が不正であれば検証エラー
        var userid = decodedJWT.getSubject();
        if (userid == null || userid.isEmpty()) {
            throw new BadCredentialsException("Userid must not be empty.");
        }

        return new TokenValidationResult(userid);
    }
}
