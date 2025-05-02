package com.quickpick.ureca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickpick.ureca.auth.config.JwtProperties;
import com.quickpick.ureca.auth.domain.RefreshToken;
import com.quickpick.ureca.auth.dto.CreateAccessTokenRequest;
import com.quickpick.ureca.auth.repository.RefreshTokenRepository;
import com.quickpick.ureca.config.jwt.JwtFactory;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TokenControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        userRepository.deleteAll();
    } // mockMvcSetUp

    @DisplayName("createNewAccessToken : 새로운 액세스 토큰을 발급한다.")
    @Test
    public void createNewAccessToken() throws Exception {
        final String url = "/api/token";
        User testUser = userRepository.save( User.builder()
                .id("user@gmail.com")
                .password("test")
                .name("test")
                .age(123)
                .gender("male")
                .build() );
        String refreshToken = JwtFactory.builder()
                .claims( Map.of( "user_id", testUser.getUserId() ) )
                .build()
                .createToken(jwtProperties);
        refreshTokenRepository.save( new RefreshToken(testUser.getUserId(), refreshToken) );

        CreateAccessTokenRequest request = new CreateAccessTokenRequest();
        request.setRefreshToken(refreshToken);
        final String requestBody = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform( post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody) );

        resultActions
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.accessToken").isNotEmpty() );
    } // createNewAccessToken

} // class
