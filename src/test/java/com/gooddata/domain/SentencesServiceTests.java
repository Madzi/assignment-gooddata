package com.gooddata.domain;

import com.gooddata.dao.SentenceEntity;
import com.gooddata.dao.SentencesRepository;
import com.gooddata.dao.WordEntity;
import com.gooddata.dao.WordsRepository;
import com.gooddata.domain.model.WordCategory;
import com.gooddata.web.dto.WebSentence;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SentencesServiceTests {

    @MockBean
    private SentencesRepository sentencesRepository;

    @MockBean
    private WordsRepository wordsRepository;

    @Autowired
    private SentencesService sentencesService;

    @Test
    @DisplayName("Find all sentences in empty list")
    void testFindAll() {
        when(sentencesRepository.findAll()).thenReturn(Collections.emptyList());

        var sentences = sentencesService.findAll();

        Assertions.assertTrue(sentences.isEmpty());
    }

    @Test
    @DisplayName("Find all sentences when they are present")
    void testFindAllCorrect() {
        var noun1 = new WordEntity("noun1", WordCategory.NOUN);
        var verb1 = new WordEntity("verb1", WordCategory.VERB);
        var adjective1 = new WordEntity("adjective1", WordCategory.ADJECTIVE);
        var noun2 = new WordEntity("noun2", WordCategory.NOUN);
        var verb2 = new WordEntity("verb2", WordCategory.VERB);
        var adjective2 = new WordEntity("adjective2", WordCategory.ADJECTIVE);
        var noun3 = new WordEntity("noun3", WordCategory.NOUN);
        var verb3 = new WordEntity("verb3", WordCategory.VERB);
        var adjective3 = new WordEntity("adjective3", WordCategory.ADJECTIVE);
        when(sentencesRepository.findAll()).thenReturn(Arrays.asList(
                new SentenceEntity(noun1, verb1, adjective1),
                new SentenceEntity(noun2, verb2, adjective2),
                new SentenceEntity(noun3, verb3, adjective3)
        ));

        var sentences = sentencesService.findAll();

        Assertions.assertEquals(3, sentences.size());
        sentences.forEach(sentence -> Assertions.assertEquals(1L, sentence.getShowCount().longValue()));
    }

    @Test
    @DisplayName("Find existing sentence by id")
    void testFindByIdOk() {
        var sentence = new SentenceEntity(
                new WordEntity("noun", WordCategory.NOUN),
                new WordEntity("verb", WordCategory.VERB),
                new WordEntity("adjective", WordCategory.ADJECTIVE)
        );
        sentence.setId(1L);
        sentence.setShowCount(1L);
        when(sentencesRepository.findById(1L)).thenReturn(Optional.of(sentence));

        var optSentence = sentencesService.findById(1L);
        Assertions.assertTrue(optSentence.isPresent());

        var snt = optSentence.get();
        Assertions.assertEquals("noun verb adjective", snt.getNoun().getWord() + " " + snt.getVerb().getWord() + " " + snt.getAdjective().getWord());
        Assertions.assertEquals(2L, snt.getShowCount().longValue());
    }

    @Test
    @DisplayName("Find non existing sentence by id")
    void testFindByIdFail() {
        when(sentencesRepository.findById(1L)).thenReturn(Optional.empty());

        var optSentence = sentencesService.findById(1L);

        Assertions.assertFalse(optSentence.isPresent());
    }

    @Test
    @DisplayName("Get same sentence twice, look at counter")
    void testShowCounter() {
        var sentence = new SentenceEntity(
                new WordEntity("noun", WordCategory.NOUN),
                new WordEntity("verb", WordCategory.VERB),
                new WordEntity("adjective", WordCategory.ADJECTIVE)
        );
        sentence.setId(1L);
        when(sentencesRepository.findById(1L)).thenReturn(Optional.of(sentence));

        var snt1 = sentencesService.findById(1L);
        Assertions.assertTrue(snt1.isPresent());
        Assertions.assertEquals(1L, snt1.get().getShowCount().longValue());
        var snt2 = sentencesService.findById(1L);
        Assertions.assertTrue(snt2.isPresent());
        Assertions.assertEquals(2L, snt2.get().getShowCount().longValue());
        var snt3 = sentencesService.findById(1L);
        Assertions.assertTrue(snt3.isPresent());
        Assertions.assertEquals(3L, snt3.get().getShowCount().longValue());
    }

    @Test
    @DisplayName("Generate new sentence")
    void testGenerateNewSentence() {
        when(wordsRepository.findByWordCategory(WordCategory.NOUN)).thenReturn(Collections.singletonList(new WordEntity("noun", WordCategory.NOUN)));
        when(wordsRepository.findByWordCategory(WordCategory.VERB)).thenReturn(Collections.singletonList(new WordEntity("verb", WordCategory.VERB)));
        when(wordsRepository.findByWordCategory(WordCategory.ADJECTIVE)).thenReturn(Collections.singletonList(new WordEntity("adjective", WordCategory.ADJECTIVE)));

        var sentence = sentencesService.generate();
        var webSentence = new WebSentence(sentence);

        Assertions.assertEquals(0L, webSentence.getShowDisplayCount().longValue());
    }

    @Test
    @DisplayName("Generate without words")
    void testGenerateIsFail() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            var sentence = sentencesService.generate();
        });
    }

}
