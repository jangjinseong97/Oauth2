package com.green.greengram;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {
    // 파라미터 dateTime 으로 넘어오는 값이 DB에 저장된 dateTime 값 2024-12-30 11:32:23
    public static void assertCurrenTimeStamp(String dateTime){

        LocalDateTime expectedNow = LocalDateTime.now();
        // 2024-12-30T11:32:23 이런식으로 나와서 변환이 필요
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 달과 분을 구분하기 위해 대소문자 구분이 필요함
        // HH는 대문자는 24시간까지 hh는 12시간까지
        LocalDateTime actualNow = LocalDateTime.parse(dateTime,formatter);
//        assertTrue(expectedNow.isAfter(actualNow)); expectedNow 가 actualNow보다 더 뒤인지 확인
        assertTrue(Duration.between(expectedNow,actualNow).getSeconds()<=1);
        // 두 객체의 시간차이를 초로 계산 후 1초 이하인지
    }
}
