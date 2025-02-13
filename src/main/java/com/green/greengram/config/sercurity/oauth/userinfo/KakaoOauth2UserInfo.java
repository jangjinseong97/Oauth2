package com.green.greengram.config.sercurity.oauth.userinfo;


import java.util.Map;

public class KakaoOauth2UserInfo extends Oauth2UserInfo {
    public KakaoOauth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return properties==null ? "":properties.get("nickname").toString();
    }
    /*
    { "id" : "122341",
      "account_email" : "adsf@asdf.com", 형태로 넘어 왔었음(지금은 없음)
      "properties" : {
          "nickname" : "홍길동",
          "thumbnail_image" : "abc.jpg"
        }
    }
    이런식으로 자료가 넘어와서 추가로 map을 사용하여 추출
     */

    @Override
    public String getEmail() {
        String email = (String) attributes.get("account_email");
        return email==null ? "":email;
    }

    @Override
    public String getProfileImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        Object a = properties.get("thumbnail_image");
        return a==null ? "": a.toString();
    }
}
