package com.fozf.jsoccwebservices.auth;

import com.fozf.jsoccwebservices.data.DBBootstrapper;
import com.fozf.jsoccwebservices.data.InitialDataLoader;
import com.fozf.jsoccwebservices.repositories.AdminRepository;
import com.fozf.jsoccwebservices.student.StudentIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TokenAuthServiceIntegrationTest {


    @Autowired
    private MockMvc mvc;

    final String admin = "admin";
    final String instructor = "juan";
    final String student = "laureanray";
    final String password = "P@$$w0rd";
    final static String ACCEPT  = "application/json;charset=UTF-8";




    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(get("/api/v1/students"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnTokenForAdmin() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "testjwtclientid");
        params.add("username", admin);
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("testjwtclientid","XY7kmzoNzl100"))
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
        params.add("client_id", "testjwtclientid");
        params.add("username", student);
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("testjwtclientid","XY7kmzoNzl100"))
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
        params.add("client_id", "testjwtclientid");
        params.add("username", instructor);
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("testjwtclientid", "XY7kmzoNzl100"))
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
