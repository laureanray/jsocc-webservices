package com.fozf.jsoccwebservices.controller;

import com.fozf.jsoccwebservices.data.DBBootstrapper;
import com.fozf.jsoccwebservices.controllers.StudentController;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import com.fozf.jsoccwebservices.services.StudentService;
import com.fozf.jsoccwebservices.storage.StorageService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mvc;

    //@MockBean
    //private StudentController studentController;

    //@MockBean
    //private StorageService storageService;

    @MockBean
    private StudentRepository studentRepository;

    @Before
    public void clearDatabase(){
        studentRepository.deleteAll();
    }

    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "testjwtclientid");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("testjwtclientid","XY7kmzoNzl100"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void shouldNotAllowAccessIfTokenNotProvided() throws  Exception {
        // given(studentController.getAllStudents()).willReturn(DBBootstrapper.students);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/students"))
            .andExpect(status().isUnauthorized());

    }

    @Test
    public void shouldReturnAllStudentsIfAdmin() throws Exception {

        String token = obtainAccessToken("admin", "test");
        System.out.println(token);
        mvc.perform(get("/api/v1/students/")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldSuccessfullyRegisterStudentAndHaveRole() throws Exception {
        Student student = new Student();
        student.setFirstName("Test Student");
        student.setLastName("Lastname");
        student.setEmail("email@example.com");
        student.setEnabled(false);
        student.setUsername("tester");


        mvc.perform(post("/api/v1/students/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(student)))
                .andExpect(status().isCreated());

    }
}
