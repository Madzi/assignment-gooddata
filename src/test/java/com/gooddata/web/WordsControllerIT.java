package com.gooddata.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.gooddata.domain.WordsService;
import com.gooddata.domain.model.BadWordCategoryException;
import com.gooddata.domain.model.WordCategory;
import com.gooddata.web.dto.WebWord;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WordsControllerIT {

    private MockMvc mockMvc;

    @MockBean
    private WordsService wordsService;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("The list of words can be empty")
    void testGetEmptyWordList() throws Exception {
        when(wordsService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/words"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("The all known words must be returned")
    void testGetFullWordList() throws Exception {
        when(wordsService.findAll()).thenReturn(Arrays.asList(
                new WebWord("noun", WordCategory.NOUN),
                new WebWord("verb", WordCategory.VERB),
                new WebWord("adjective", WordCategory.ADJECTIVE
        )));

        mockMvc.perform(get("/words"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].word", is("noun")))
                .andExpect(jsonPath("$[1].word", is("verb")))
                .andExpect(jsonPath("$[2].word", is("adjective")))
                .andExpect(jsonPath("$[0].wordCategory", is("NOUN")))
                .andExpect(jsonPath("$[1].wordCategory", is("VERB")))
                .andExpect(jsonPath("$[2].wordCategory", is("ADJECTIVE")))
                .andReturn();
    }

    @Test
    @DisplayName("Get one word by word name")
    void testOneWordFromList() throws Exception {
        var webWord = new WebWord("go", WordCategory.NOUN);
        when(wordsService.getByWord(anyString())).thenReturn(Arrays.asList(webWord));

        mockMvc.perform(get("/words/{wordId}", webWord.getWord()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].word", is("go")))
                .andExpect(jsonPath("$[0].wordCategory", is("NOUN")))
                .andReturn();
    }

    @Test
    @DisplayName("Get two words by word name")
    void testFourWordsFromList() throws Exception {
        when(wordsService.getByWord(anyString()))
                .thenReturn(Arrays.asList(new WebWord("go", WordCategory.NOUN), new WebWord("go", WordCategory.VERB)));

        mockMvc.perform(get("/words/{wordId}", "go"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].word", is("go")))
                .andExpect(jsonPath("$[0].wordCategory", is("NOUN")))
                .andExpect(jsonPath("$[1].word", is("go")))
                .andExpect(jsonPath("$[1].wordCategory", is("VERB")))
                .andReturn();
    }

    @Test
    @DisplayName("The new word can be added")
    void testAddNewWord() throws Exception {
        var payload = new WebWord("sun", WordCategory.NOUN);
        when(wordsService.addWord(any())).thenReturn(payload);

        mockMvc.perform(post("/words/{wordId}", "sun")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"wordCategory\":\"NOUN\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.word", is("sun")))
                .andExpect(jsonPath("$.wordCategory", is("NOUN")))
                .andReturn();
    }

    @Test
    @DisplayName("When category incorrect the error must be generated")
    void testFailDuringAddNewWord() throws Exception {
        when(wordsService.addWord(any())).thenThrow(new BadWordCategoryException("Unsupproted type yet."));

        mockMvc.perform(post("/words/{wordId}", "sun")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"wordCategory\":\"UNKNOWN\"}")
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
