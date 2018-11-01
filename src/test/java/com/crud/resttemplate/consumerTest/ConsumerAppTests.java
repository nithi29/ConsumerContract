package com.crud.resttemplate.consumerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(value = SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {
        "com.demo.SpringContract:+:stubs:8080" }, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ConsumerAppTests {

    /*
     * private String subject =
     * ProfileValidationApi(RestTemplate(),"http://localhost:8080/users/44");
     */
    @Autowired
    MockMvc mockMvc;

    @Test
    public void ConsumerTest() throws Exception {
        // When
        ResultActions result = mockMvc.perform(get("/users/44"));
        // Then
        result.andExpect(status().isOk()).andExpect(content()
                .json("{\"userid\":44,\"firstName\":\"nithii\",\"lastName\":\"nithi\",,\"email\":\"nith@gmail.com\"}"));

    }
}