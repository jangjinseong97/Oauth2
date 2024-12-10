package com.green.greengram.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class MyFileUtilsTest {
    MyFileUtils myFileUtils;

    @BeforeEach
    // test 를 가진 메소드마다 아래의 객체를 만들어주는것
    void setUp(){
        myFileUtils = new MyFileUtils("D:/students/jang jin seong/download/greengram_ver3");
    }

    @Test
    void deleteFolder() {
        String path = String.format("%s/user/ddd",myFileUtils.getUploadPath());
        myFileUtils.deleteFolder(path, false);
    }
}