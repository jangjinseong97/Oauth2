package com.green.greengram;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//라이프 사이클 지정, 런타임동안 사용가능
@WithSecurityContext(factory = WithAuthMockUserSecurityContextFactory.class)
public @interface WithAuthUser {
    long signedUserId() default 1L;
    String[] roles() default {"ROLE_USER","ROLE_ADMIN"};
    // 권한 채크용(여기선 안쓰임 > greenGramTdd 에선 인가처리 안했으므로)
}
