package com.gooddata.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gooddata.SentenceApiApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SentenceApiApplication.class)
public class WordsControllerIT {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setUp() {
        this.mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    @DisplayName("The all words must be returned")
    void testGetAllWords() throws Exception {
        mockMvc.perform(get("/v1/words"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("The specified word must be returned")
    void testGetWord() throws Exception {
        mockMvc.perform(get("/v1/words/knownword"))
                .andReturn();
    }

    @Test
    @DisplayName("The new word can be added")
    void testAddWord() throws Exception {
        mockMvc.perform(post("/v1/words/neword"))
                .andReturn();
    }

}
