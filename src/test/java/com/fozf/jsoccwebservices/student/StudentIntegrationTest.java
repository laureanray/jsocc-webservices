package com.fozf.jsoccwebservices.student;

import com.fozf.jsoccwebservices.domain.Admin;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.AdminRepository;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import com.google.gson.Gson;
import org.junit.*;
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
import static org.mockito.ArgumentMatchers.any;
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

    @Autowired
    private AdminRepository adminRepository;

    private Student student1 = new Student();
    private Student student2 = new Student();
    private Admin admin = new Admin();


    final static String ACCEPT  = "application/json;charset=UTF-8";
    private String adminToken;
    private final String password = "P@$$w0rd";

    public StudentIntegrationTest() throws Exception {
        //studentRepository.deleteAll();
        student1.setFirstName("test stu23dent");
        student1.setLastName("lastname");
        student1.setEmail("email@example.com");
        student1.setUsername("testers");
        student1.setPassword(password);
        // Set properties for admin
        admin.setFirstName("First Name");
        admin.setLastName("Last Name");
        admin.setUsername("admin");
        admin.setPassword(password);
        admin.setEmail("instructor@example.com");
    }

    @Before
    public void clearDatabase() throws Exception {
        adminToken = obtainAccessToken("admin", password);
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
                .accept(ACCEPT))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void shouldNotAllowAccessIfTokenNotProvided() throws  Exception {
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
        // Wipe DB here?
        String payload = new Gson().toJson(student1);
        System.out.println(payload);
        this.mvc.perform(post("/api/v1/students/register")
                .accept("application/json;charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
            .content(payload))
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(status().isCreated());


        Student student = studentRepository.findByUsername(student1.getUsername());
        Assert.assertEquals(student.getUsername(), student1.getUsername());
    }

    @Test
    public void shouldReturnStudent() throws Exception {
        Student student = new Student();
        student.setFirstName("Test Test");
        student.setLastName("Test");
        student.setPassword(password);
        student.setUsername("test");
        student.setEmail("test_student@example.com");


        this.mvc.perform(post("/api/v1/students/register")
                .accept("application/json;charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(student)))
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(status().isCreated());
        Student studentResult = studentRepository.findByUsername(student.getUsername());
        Assert.assertEquals(student.getUsername(), studentResult.getUsername());


        mvc.perform(get("/api/v1/students/find/".concat(studentResult.getUsername()))
                .accept(ACCEPT)
                .header("Authorization", "Bearer ".concat(adminToken)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateStudentDetails() throws Exception {
        // Get the student to update first

        Student studentToUpdate = studentRepository.findByUsername("student");
        studentToUpdate.setPassword("thisisanewpassword");

        if(studentToUpdate != null){
            this.mvc.perform(post("/api/v1/students/update/student")
                    .accept("application/json;charset=UTF-8")
                    .header("Authorization", "Bearer ".concat(adminToken))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new Gson().toJson(studentToUpdate)))
                    .andExpect(status().isOk());
        } else {
            throw new Exception("Student returned null");
        }

    }
}
