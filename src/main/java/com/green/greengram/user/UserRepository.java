package com.green.greengram.user;

import com.green.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
                                              //<연결할 엔터티, pk타입>
    // JpaRepository 상속 받음으로 빈등록까지 됨(에노테이션 안넣어도 된다.)
    User findByUid(String uid);
    // 이를 메소드 쿼리 ( 메소드로 쿼리를 작성 해주는것 여기의 경우 uid를 불러와줌 단 모든 정보를 불러옴)
    // find > select / BY > where / Uid > where가 어디건지  따라서 where 부분에는 있는걸로 넣어줘야됨
    // 아니면 에러 발생
    // ex) User findByEmail(String uid); > 여기 기준 이거 실행시 email이란 튜플이 없어서 서버 기동부터 안됨

}
