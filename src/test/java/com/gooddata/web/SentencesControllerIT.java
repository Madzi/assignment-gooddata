package com.gooddata.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.gooddata.dao.SentenceEntity;
import com.gooddata.dao.WordEntity;
import com.gooddata.domain.SentencesService;
import com.gooddata.domain.model.WordCategory;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SentencesControllerIT {

    private MockMvc mockMvc;

    @MockBean
    private SentencesService sentencesService;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("The list of sentences can be empty")
    void testGetEmptySentenceList() throws Exception {
        when(sentencesService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/sentences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("The all known sentences must be returned")
    void testGetFullSentenceList() throws Exception {
        when(sentencesService.findAll()).thenReturn(Arrays.asList(
                new SentenceEntity(
                        new WordEntity("noun", WordCategory.NOUN),
                        new WordEntity("verb", WordCategory.VERB),
                        new WordEntity("adjective", WordCategory.ADJECTIVE)
                ),
                new SentenceEntity(
                        new WordEntity("bird", WordCategory.NOUN),
                        new WordEntity("fly", WordCategory.VERB),
                        new WordEntity("fast", WordCategory.ADJECTIVE)
                )
        ));


        mockMvc.perform(get("/sentences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].text", is("noun verb adjective")))
                .andExpect(jsonPath("$[0].showDisplayCount", is(0)))
                .andExpect(jsonPath("$[1].text", is("bird fly fast")))
                .andExpect(jsonPath("$[1].showDisplayCount", is(0)))
                .andReturn();
    }

}
