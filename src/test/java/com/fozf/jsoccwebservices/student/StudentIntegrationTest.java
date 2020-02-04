package com.fozf.jsoccwebservices.student;

import com.fozf.jsoccwebservices.data.DBBootstrapper;
import com.fozf.jsoccwebservices.controllers.StudentController;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import com.fozf.jsoccwebservices.services.StudentService;
import com.fozf.jsoccwebservices.storage.StorageService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class StudentIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private StudentRepository studentRepository;
    //@MockBean
    //private StudentController studentController;

//    @MockBean
//    private StudentRepository studentRepository;

    //@MockBean
    //private StorageService storageService;

    private Student student1 = new Student();
    private Student student2 = new Student();
    final static String ACCEPT  = "application/json;charset=UTF-8";
    private String adminToken;

    public StudentIntegrationTest() throws Exception {
        //studentRepository.deleteAll();
    }

    //
//
    @Before
    public void clearDatabase() throws Exception {
        //studentRepository.deleteAll();
        student1.setFirstName("test student");
        student1.setLastName("lastname");
        student1.setEmail("email@example.com");
        student1.setUsername("testers");
        adminToken = obtainAccessToken("admin", "P@$$w0rd");
    }
//
//
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
                .accept(ACCEPT))
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
        mvc.perform(get("/api/v1/students/")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ACCEPT))
                .andExpect(status().isOk());


    }

    @Test
    public void shouldNotRegisterIfRequiredIsMissing() throws Exception {
        mvc.perform(post("/api/v1/students/register/")
                .accept(ACCEPT)
                .characterEncoding("utf-8")
                .content(new Gson().toJson(student2))
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    public void shouldSuccessfullyRegisterStudentAndHaveRole() throws Exception {
        String payload = new Gson().toJson(student1);
        System.out.println(payload);
        this.mvc.perform(post("/api/v1/students/register")
                .accept("application/json;charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
                .andExpect(status().isCreated());


        Student student = studentRepository.findByUsername(student1.getUsername());

        Assert.assertEquals(student1.getId(), student.getId());
    }

    @Test
    public void shouldReturnStudent() throws Exception {

        mvc.perform(get("/api/v1/students/find/laureanray")
                .accept(ACCEPT)
                .header("Authorization", "Bearer ".concat(adminToken)))
                .andExpect(status().isOk());




    }
}
