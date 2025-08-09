package com.bigsuika.onlineshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ContextLoadTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        // 验证Spring上下文是否成功加载
        assertNotNull(context);
    }
}