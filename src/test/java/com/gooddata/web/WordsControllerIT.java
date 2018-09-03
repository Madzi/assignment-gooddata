package com.gooddata.web;

import com.gooddata.dao.WordsRepository;
import com.gooddata.dao.model.WordEntity;
import com.gooddata.domain.model.Category;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WordsControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WordsRepository wordsRepository;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("The list of words can be empty")
    void testGetEmptyWords() throws Exception {
        wordsRepository.deleteAll();

        mockMvc.perform(get("/words"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
    }

    @Test
    @DisplayName("The all words must be returned")
    void testGetAllWords() throws Exception {
        wordsRepository.deleteAll();
        wordsRepository.saveAll(Arrays.asList(
                new WordEntity("noun", Category.NOUN),
                new WordEntity("verb", Category.VERB),
                new WordEntity("adjective", Category.ADJECTIVE)
        ));

        mockMvc.perform(get("/words"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("noun")))
                .andExpect(jsonPath("$[1].name", is("verb")))
                .andExpect(jsonPath("$[2].name", is("adjective")))
                .andExpect(jsonPath("$[0].category", is("NOUN")))
                .andExpect(jsonPath("$[1].category", is("VERB")))
                .andExpect(jsonPath("$[2].category", is("ADJECTIVE")))
                .andReturn();
    }

    @Test
    @DisplayName("If requested word is exists it's must be returned")
    void testGetWord() throws Exception {
        wordsRepository.deleteAll();
        wordsRepository.save(new WordEntity("wheel", Category.NOUN));

        mockMvc.perform(get("/words/{name}", "wheel"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("wheel")))
                .andExpect(jsonPath("$[0].category", is("NOUN")))
                .andReturn();
    }

    @Test
    @DisplayName("It can return more than one word")
    void testGetMultiplyWord() throws Exception {
        wordsRepository.deleteAll();
        wordsRepository.saveAll(Arrays.asList(
                new WordEntity("word", Category.NOUN),
                new WordEntity("word", Category.ADJECTIVE)
        ));

        mockMvc.perform(get("/words/{name}", "word"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("word")))
                .andExpect(jsonPath("$[0].category", is("NOUN")))
                .andExpect(jsonPath("$[1].name", is("word")))
                .andExpect(jsonPath("$[1].category", is("ADJECTIVE")))
                .andReturn();
    }

    @Test
    @DisplayName("The new word can be added")
    void testAddWord() throws Exception {
        wordsRepository.deleteAll();

        mockMvc.perform(post("/words/{name}", "message")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content("{\"category\":\"NOUN\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("message")))
                .andExpect(jsonPath("$.category", is("NOUN")))
                .andReturn();

        Assertions.assertEquals(1, wordsRepository.findAll().size());
    }

    @Test
    @DisplayName("Will be error when category incorrect")
    void testAddIncorrectCategory() throws Exception {
        wordsRepository.deleteAll();

        mockMvc.perform(post("/words/{name}", "message")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content("{\"category\":\"UNKNOWN\"}"))
                .andExpect(jsonPath("$.message", is("message")))
                .andExpect(jsonPath("$.status", is("NOUN")))
                .andReturn();
    }

}
