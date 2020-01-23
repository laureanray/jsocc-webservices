package com.fozf.jsoccwebservices.controller;

import com.fozf.jsoccwebservices.bootstrap.DataBootstrapper;
import com.fozf.jsoccwebservices.controllers.CourseCategoryController;
import com.fozf.jsoccwebservices.controllers.StudentController;
import com.fozf.jsoccwebservices.storage.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {



    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentController studentController;

    @MockBean
    private StorageService storageService;

    @Test
    public void getAllStudents() throws  Exception {
        given(studentController.getAllStudents()).willReturn(DataBootstrapper.students);


    mvc.perform(get(StudentController.BASE_URL.concat("/"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].firstName", is(DataBootstrapper.students.get(0).getFirstName())));
    }
}
