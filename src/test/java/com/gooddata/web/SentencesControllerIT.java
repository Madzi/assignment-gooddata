package com.gooddata.web;

import com.gooddata.dao.SentenceEntity;
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
public class SentencesControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WordsRepository wordsRepository;

    @Autowired
    private SentencesRepository sentencesRepository;


    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("The list of sentences can be empty")
    void testGetEmptySentenceList() throws Exception {
        sentencesRepository.deleteAll();

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/sentences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(document("sentences/all-empty"))
                .andReturn();
    }

    @Test
    @DisplayName("The all known sentences must be returned")
    void testGetFullSentenceList() throws Exception {
        sentencesRepository.deleteAll();
        wordsRepository.deleteAll();
        var noun1 = registerWord("noun1", WordCategory.NOUN);
        var noun2 = registerWord("noun2", WordCategory.NOUN);
        var verb1 = registerWord("verb1", WordCategory.VERB);
        var verb2 = registerWord("verb2", WordCategory.VERB);
        var adjective1 = registerWord("adjective1", WordCategory.ADJECTIVE);
        var adjective2 = registerWord("adjective2", WordCategory.ADJECTIVE);
        sentencesRepository.save(new SentenceEntity(noun1, verb1, adjective1));
        sentencesRepository.save(new SentenceEntity(noun2, verb2, adjective2));

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/sentences"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].text", is("noun1 verb1 adjective1")))
                .andExpect(jsonPath("$[0].showDisplayCount", is(1)))
                .andExpect(jsonPath("$[1].text", is("noun2 verb2 adjective2")))
                .andExpect(jsonPath("$[1].showDisplayCount", is(1)))
                .andDo(document("sentences/all-full"))
                .andReturn();
    }

    @Test
    @DisplayName("The sentence can be accessed by id")
    void testGetSentenceById() throws Exception {
        sentencesRepository.deleteAll();
        wordsRepository.deleteAll();
        var noun = registerWord("noun", WordCategory.NOUN);
        var verb = registerWord("verb", WordCategory.VERB);
        var adjective = registerWord("adjective", WordCategory.ADJECTIVE);
        var sentence = sentencesRepository.save(new SentenceEntity(noun, verb, adjective));

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/sentences/{sentenceId}", sentence.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(document("sentences/get-by-id-ok"))
                .andReturn();
    }

    @Test
    @DisplayName("If sentence not found we raise exception")
    void testExceptionalAccessToSentence() throws Exception {
        sentencesRepository.deleteAll();
        wordsRepository.deleteAll();

        mockMvc.perform(RestDocumentationRequestBuilders
                .get("/sentences/{sentenceId}", 1L))
                .andExpect(status().isBadRequest())
                .andDo(document("sentences/get-by-id-fail"))
                .andReturn();
    }

    @Test
    @DisplayName("Standard generation rules by hand")
    void testGenerationSentences() throws Exception {
        sentencesRepository.deleteAll();
        wordsRepository.deleteAll();
        var noun1 = registerWord("noun1", WordCategory.NOUN);
        var noun2 = registerWord("noun2", WordCategory.NOUN);
        var verb1 = registerWord("verb1", WordCategory.VERB);
        var verb2 = registerWord("verb2", WordCategory.VERB);
        var adjective1 = registerWord("adjective1", WordCategory.ADJECTIVE);
        var adjective2 = registerWord("adjective2", WordCategory.ADJECTIVE);

        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/sentences/generate"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(document("sentences/generate-ok"))
                .andReturn();
    }

    @Test
    @DisplayName("Generatstatus().isOk()e sentence without words")
    void testGenerationWithoutWords() throws Exception {
        sentencesRepository.deleteAll();
        wordsRepository.deleteAll();

        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/sentences/generate"))
                .andExpect(status().isBadRequest())
                .andDo(document("sentences/generate-fail"))
                .andReturn();
    }

    private WordEntity registerWord(final String name, final WordCategory category) {
        var word = new WordEntity(name, category);
        wordsRepository.save(word);
        return word;
    }

}
