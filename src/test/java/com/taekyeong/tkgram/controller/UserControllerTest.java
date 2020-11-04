package com.taekyeong.tkgram.controller;

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

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }
/*
    @Test
    public HttpStatus 회원가입() throws Exception {
        String email = "xorud1541@test.com";
        String username = "jeon";
        String password = "123";
        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/api/v1/join";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, userJoinRequestDto, Long.class);

        return responseEntity.getStatusCode();
        //then
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // List<User> all = userRepository.findAll();
    }
 */

    @Test
    public void 로그인() throws Exception {
        String email = "xorud1541@test.com";
        String password = "123";
        String username = "jeon";

        UserJoinRequestDto userJoinRequestDto = UserJoinRequestDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/api/v1/join";

        //when
        ResponseEntity<Long> joinResponseEntity = restTemplate.postForEntity(url, userJoinRequestDto, Long.class);
        if(joinResponseEntity.getStatusCode() == HttpStatus.OK) {

            url = "http://localhost:" + port + "/api/v1/login";
            UserLoginRequestDto userLoginRequestDto = UserLoginRequestDto.builder()
                    .email(email)
                    .password(password)
                    .secretkey("111")
                    .build();

            ResponseEntity<String> loginResponseEntity = restTemplate.postForEntity(url, userLoginRequestDto, String.class);

            assertThat(loginResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            String token = loginResponseEntity.getBody();
            System.out.println(token);
        }
    }
}
