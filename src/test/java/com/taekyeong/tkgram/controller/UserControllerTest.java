package com.taekyeong.tkgram.controller;

import com.taekyeong.tkgram.dto.UserInfoResponseDto;
import com.taekyeong.tkgram.dto.UserJoinRequestDto;
import com.taekyeong.tkgram.dto.UserLoginRequestDto;
import com.taekyeong.tkgram.entity.User;
import com.taekyeong.tkgram.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
public class UserControllerTest {
    /*
    @Autowired
    private MockMvc mvc;

    @Test
    public void hello_function() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(hello));
    }
    */
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private MockHttpServletRequest mockHttpServletRequest;

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void 내정보_가져오기() throws Exception {
        String email = "xorud1541@test.com";
        String password = "123";
        String username = "jeon";

        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/api/v1/join";

        //회원 가입
        ResponseEntity<HttpStatus> joinResponseEntity = restTemplate.postForEntity(url, userJoinRequestDto, HttpStatus.class);
        if(joinResponseEntity.getStatusCode() == HttpStatus.OK) {

            url = "http://localhost:" + port + "/api/v1/login";
            UserLoginRequestDto userLoginRequestDto = UserLoginRequestDto.builder()
                    .email(email)
                    .password(password)
                    .secretkey("secret")
                    .build();

            //로그인
            ResponseEntity<String> loginResponseEntity = restTemplate.postForEntity(url, userLoginRequestDto, String.class);

            assertThat(loginResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            String token = loginResponseEntity.getBody();

            if(token != null)
            {
                url = "http://localhost:" + port + "/api/v1/my";

                mockHttpServletRequest.addHeader("Authorization", "Bearer " + token);
                //ResponseEntity<UserInfoResponseDto> entity = restTemplate.exchange(url, UserInfoResponseDto.class, mockHttpServletRequest);
            }
        }
    }
}
