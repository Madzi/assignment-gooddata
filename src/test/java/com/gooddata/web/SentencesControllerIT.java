package com.gooddata.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gooddata.SentenceApiApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SentenceApiApplication.class)
public class SentencesControllerIT {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setUp() {
        this.mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    @DisplayName("The all sentences must be returned")
    void testGetAllSentences() throws Exception {
        mockMvc.perform(get("/v1/sentences"))
                .andReturn();
    }

    @Test
    @DisplayName("The new sentence must be generated")
    void testGenerateNewSentence() throws Exception {
        mockMvc.perform(post("/v1/sentences/generate"))
                .andReturn();
    }

    @Test
    @DisplayName("The specific sentence must be returned")
    void testGetSentenceById() throws Exception {
        mockMvc.perform(get("/v1/sentences/knownid"))
                .andReturn();
    }

    @Test
    @DisplayName("The specific sentence can be changed for Yoda")
    void testGetYodaSentenceById() throws Exception {
        mockMvc.perform(get("/v1/sentence/knownid/yodaTalk"))
                .andReturn();
    }

}
