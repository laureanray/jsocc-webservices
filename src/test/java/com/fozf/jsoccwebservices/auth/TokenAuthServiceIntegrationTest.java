package com.fozf.jsoccwebservices.auth;


import com.fozf.jsoccwebservices.data.InitialDataLoader;
import com.fozf.jsoccwebservices.domain.Admin;
import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.AdminRepository;
import com.fozf.jsoccwebservices.repositories.InstructorRepository;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import com.fozf.jsoccwebservices.student.StudentIntegrationTest;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TokenAuthServiceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    final String password = "P@$$w0rd";
    final static String ACCEPT  = "application/json;charset=UTF-8";

    @Test
    public void shouldNotAllowAccessToDisabledUsers() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "WEB_CLIENT");
        params.add("username", "student_disabled");
        params.add("password", password);

        mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("WEB_CLIENT","SECRET"))
                .accept(ACCEPT))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(get("/api/v1/students"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnTokenForAdmin() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "WEB_CLIENT");
        params.add("username", "admin");
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("WEB_CLIENT","SECRET"))
                .accept(ACCEPT))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token = jsonParser.parseMap(resultString).get("access_token").toString();

        // Test if token have access in protected routes

        mvc.perform(get("/api/v1/students/")
            .accept(ACCEPT)
            .header("Authorization", "Bearer ".concat(token)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTokenForStudentAndroidClient() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "ANDROID_CLIENT");
        params.add("username", "admin");
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("ANDROID_CLIENT","SECRET"))
                .accept(ACCEPT))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token = jsonParser.parseMap(resultString).get("access_token").toString();

        // Test if token have access in protected routes

        mvc.perform(get("/api/v1/students/")
                .accept(ACCEPT)
                .header("Authorization", "Bearer ".concat(token)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTokenForStudent() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "WEB_CLIENT");
        params.add("username", "student");
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("WEB_CLIENT","SECRET"))
                .accept(ACCEPT))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token = jsonParser.parseMap(resultString).get("access_token").toString();

        // Test if token have access in protected routes

        mvc.perform(get("/api/v1/students/")
                .accept(ACCEPT)
                .header("Authorization", "Bearer ".concat(token)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTokenForInstructor() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "WEB_CLIENT");
        params.add("username", "instructor");
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("WEB_CLIENT", "SECRET"))
                .accept(ACCEPT))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token = jsonParser.parseMap(resultString).get("access_token").toString();

        // Test if token have access in protected routes
        mvc.perform(get("/api/v1/students/")
                .accept(ACCEPT)
                .header("Authorization", "Bearer ".concat(token)))
                .andExpect(status().isOk());
        }
    }
