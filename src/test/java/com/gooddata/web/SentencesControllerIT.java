package com.gooddata.web;

import com.gooddata.dao.SentencesRepository;
import com.gooddata.dao.WordsRepository;
import com.gooddata.dao.model.SentenceEntity;
import com.gooddata.dao.model.WordEntity;
import com.gooddata.domain.model.Category;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
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
class SentencesControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private SentencesRepository sentencesRepository;
    @Autowired
    private WordsRepository wordsRepository;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        wordsRepository.deleteAll();
        wordsRepository.saveAll(Arrays.asList(
                new WordEntity("noun", Category.NOUN),
                new WordEntity("verb", Category.VERB),
                new WordEntity("adjective", Category.ADJECTIVE)
        ));
    }

    @Test
    @DisplayName("The list of sentences can be empty")
    void testEmptySentenceList() throws Exception {
        sentencesRepository.deleteAll();

        mockMvc.perform(get("/sentences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
    }

    private void addSentence() {
        var noun = wordsRepository.findByCategory(Category.NOUN).get(0);
        var verb = wordsRepository.findByCategory(Category.VERB).get(0);
        var adjective = wordsRepository.findByCategory(Category.ADJECTIVE).get(0);
        sentencesRepository.save(new SentenceEntity(noun, verb, adjective));
    }

    @Test
    @DisplayName("The list of all sentences")
    void testGetSentences() throws Exception {
        sentencesRepository.deleteAll();
        addSentence();

        mockMvc.perform(get("/sentences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].text", is("noun verb adjective")))
                .andReturn();
    }

    @Test
    @DisplayName("Try get not exists sentence")
    void testGetWrongSentence() throws Exception {
        sentencesRepository.deleteAll();

        mockMvc.perform(get("/sentences/{sentenceId}", "123"))
                .andReturn();
    }

    @Test
    @DisplayName("Try get exists sentence")
    void testGetCorrectSentence() throws Exception {
        sentencesRepository.deleteAll();
        addSentence();

        mockMvc.perform(get("/sentences/{sentenceId}", "1"))
                .andReturn();
    }

    void testGetYodaSentence() throws Exception {
        sentencesRepository.deleteAll();
        addSentence();

        mockMvc.perform(get("/sentences/{sentenceId}/yodaTalk", "1"))
                .andReturn();
    }

//    @Test
//    @DisplayName("The new sentence must be generated")
    void testGenerateNewSentence() throws Exception {
        mockMvc.perform(post("/sentences/generate"))
                .andReturn();
    }

    @Test
    @DisplayName("The specific sentence must be returned")
    void testGetSentenceById() throws Exception {
        mockMvc.perform(get("/sentences/knownid"))
                .andReturn();
    }

    @Test
    @DisplayName("The specific sentence can be changed for Yoda")
    void testGetYodaSentenceById() throws Exception {
        mockMvc.perform(get("/sentence/knownid/yodaTalk"))
                .andReturn();
    }

}
