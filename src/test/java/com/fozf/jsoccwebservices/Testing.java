package com.fozf.jsoccwebservices;

import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Testing {
    public static String STUDENT_API_TOKEN = null;
    public static String INSTRUCTOR_API_TOKEN = null;
    public static String ADMIN_API_TOKEN = null;
}
