package com.bigsuika.onlineshop.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Test
    public void testTokenGenerationAndValidation() {
        // 设置测试密钥
        jwtTokenUtil.setSecret("TestSecretKeyWithEnoooooooooooooooughLength");
        jwtTokenUtil.setExpiration(3600L);

        // 生成令牌
        String token = jwtTokenUtil.generateToken("testuser");
        assertNotNull(token);

        // 提取用户名
        String username = jwtTokenUtil.getUsernameFromToken(token);
        assertEquals("testuser", username);

        // 验证令牌
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");
        assertTrue(jwtTokenUtil.validateToken(token, userDetails));

        // 测试无效令牌
        assertFalse(jwtTokenUtil.validateToken("invalid.token", userDetails));
    }
}
