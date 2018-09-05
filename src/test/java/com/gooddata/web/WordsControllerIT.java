package com.gooddata.web;

import com.gooddata.dao.SentencesRepository;
import com.gooddata.dao.WordEntity;
import com.gooddata.dao.WordsRepository;
import com.gooddata.domain.model.WordCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith({ SpringExtension.class, RestDocumentationExtension.class })
@SpringBootTest
class WordsControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private SentencesRepository sentencesRepository;
    @Autowired
    private WordsRepository wordsRepository;

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
        sentencesRepository.deleteAll();
    }

    @Test
    @DisplayName("The list of words can be empty")
    void testGetEmptyWordList() throws Exception {
        wordsRepository.deleteAll();

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/words"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(json().isEqualTo("[]"))
                .andDo(document("words/all-words-missing"))
                .andReturn();
    }

    @Test
    @DisplayName("The all known words must be returned")
    void testGetFullWordList() throws Exception {
        wordsRepository.deleteAll();
        wordsRepository.save(new WordEntity("noun", WordCategory.NOUN));
        wordsRepository.save(new WordEntity("verb", WordCategory.VERB));
        wordsRepository.save(new WordEntity("adjective", WordCategory.ADJECTIVE));

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/words"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].word", is("noun")))
                .andExpect(jsonPath("$[1].word", is("verb")))
                .andExpect(jsonPath("$[2].word", is("adjective")))
                .andExpect(jsonPath("$[0].wordCategory", is("NOUN")))
                .andExpect(jsonPath("$[1].wordCategory", is("VERB")))
                .andExpect(jsonPath("$[2].wordCategory", is("ADJECTIVE")))
                .andExpect(json().isArray())
                .andExpect(json().node("[0]").isObject())
                .andExpect(json().node("[1]").isObject())
                .andExpect(json().node("[2]").isObject())
                .andExpect(json().node("[0].word").isStringEqualTo("noun"))
                .andDo(document("words/all-words-ok"))
                .andReturn();
    }

    @Test
    @DisplayName("Get one word by word name")
    void testOneWordFromList() throws Exception {
        String word = "go";
        wordsRepository.deleteAll();
        wordsRepository.save(new WordEntity(word, WordCategory.NOUN));

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/words/{wordId}", word))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].word", is(word)))
                .andExpect(jsonPath("$[0].wordCategory", is("NOUN")))
                .andDo(document("words/get-by-id-one"))
                .andReturn();
    }

    @Test
    @DisplayName("Get two words by word name")
    void testFourWordsFromList() throws Exception {
        String word = "go";
        wordsRepository.deleteAll();
        wordsRepository.save(new WordEntity(word, WordCategory.NOUN));
        wordsRepository.save(new WordEntity(word, WordCategory.VERB));

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/words/{wordId}", "go"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].word", is("go")))
                .andExpect(jsonPath("$[0].wordCategory", is("NOUN")))
                .andExpect(jsonPath("$[1].word", is("go")))
                .andExpect(jsonPath("$[1].wordCategory", is("VERB")))
                .andDo(document("words/get-ms"))
                .andReturn();
    }

    @Test
    @DisplayName("The new word can be added")
    void testAddNewWord() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/words/{wordId}", "sun")
                .contentType(MediaType.APPLICATION_JSON_UTF8).content("{\"wordCategory\":\"NOUN\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.word", is("sun")))
                .andExpect(jsonPath("$.wordCategory", is("NOUN")))
                .andExpect(json().isEqualTo("{\"word\":\"sun\",\"wordCategory\":\"NOUN\"}"))
                .andDo(document("words/add-new-word"))
                .andReturn();
    }

    @Test
    @DisplayName("Try to add forbidden word")
    void testAddForbiddenWord() throws Exception {

        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/words/{wordId}", "cat")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"wordCategory\":\"NOUN\"}"))

                .andExpect(status().isBadRequest())
                .andDo(document("words/add-forbidden-word"))
                .andReturn();
    }

    @Test
    @DisplayName("When category incorrect the error must be generated")
    void testFailDuringAddNewWord() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/words/{wordId}", "sun")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"wordCategory\":\"UNKNOWN\"}")
                )
                .andExpect(status().isBadRequest())
                .andDo(document("words/must be generated"))
                .andReturn();
    }

}
