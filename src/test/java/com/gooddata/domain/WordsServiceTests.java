package com.gooddata.domain;

import com.gooddata.dao.WordEntity;
import com.gooddata.dao.WordsRepository;
import com.gooddata.domain.model.WordCategory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WordsServiceTests {

    @MockBean
    private WordsRepository wordsRepository;

    @Autowired
    private WordsService wordsService;

    @Test
    @DisplayName("Get all words")
    void testFindAll() {
        when(wordsRepository.findAll()).thenReturn(Collections.emptyList());

        var words = wordsService.findAll();

        Assertions.assertTrue(words.isEmpty());
    }

    @Test
    @DisplayName("Get specified word(s)")
    void testFindByWord() {
        when(wordsRepository.findAll()).thenReturn(Arrays.asList(
                makeNoun("noun"),
                makeVerb("verb"),
                makeAdjective("adjective")
        ));

        var words = wordsService.findAll();

        Assertions.assertEquals(3, words.size());
    }

    @Test
    @DisplayName("Add new word")
    void testAddNewWord() {
        var word = makeNoun("sun");
        when(wordsRepository.save(any())).thenReturn(new WordEntity(word));

        var result = wordsService.addWord(word);

        Assertions.assertEquals(word, result);
    }

    @Test
    @DisplayName("Try to add forbidden word")
    void testAddForbiddenWord() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            wordsService.addWord(makeNoun("cat"));
        });
    }

    @Test
    @DisplayName("Get words for specified category")
    void testFindByCategory() {
        when(wordsRepository.findByWordCategory(WordCategory.NOUN)).thenReturn(nounList("sun", "moon", "earth"));
        when(wordsRepository.findByWordCategory(WordCategory.VERB)).thenReturn(verbList("go"));

        var nouns = wordsService.getByCategory(WordCategory.NOUN);
        var verb = wordsService.getByCategory(WordCategory.VERB);

        Assertions.assertEquals(3, nouns.size());
        Assertions.assertEquals(1, verb.size());
    }

    @Test
    @DisplayName("Get random word")
    void testGetRandomWord() {
        when(wordsRepository.findByWordCategory(WordCategory.NOUN)).thenReturn(nounList("sun", "moon"));
        when(wordsRepository.findByWordCategory(WordCategory.VERB)).thenReturn(verbList("verb"));

        var noun = wordsService.randomWordByCategory(WordCategory.NOUN);
        var verb = wordsService.randomWordByCategory(WordCategory.VERB);

        Assertions.assertTrue("sun".equals(noun.getWord()) || "moon".equals(noun.getWord()));
        Assertions.assertEquals("verb", verb.getWord());
    }

    @Test
    @DisplayName("Get random from empty list")
    void testGetRandomWithException() {
        when(wordsRepository.findByWordCategory(WordCategory.ADJECTIVE)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(IllegalStateException.class, () -> {
            var word = wordsService.randomWordByCategory(WordCategory.ADJECTIVE);
        });
    }

    private List<WordEntity> nounList(String...words) {
        return Arrays.stream(words).map(this::makeNoun).collect(Collectors.toList());
    }

    private List<WordEntity> verbList(String...words) {
        return Arrays.stream(words).map(this::makeVerb).collect(Collectors.toList());
    }

    private List<WordEntity> adjectiveList(String...words) {
        return Arrays.stream(words).map(this::makeAdjective).collect(Collectors.toList());
    }

    private WordEntity makeNoun(final String name) {
        return new WordEntity(name, WordCategory.NOUN);
    }

    private WordEntity makeVerb(final String name) {
        return new WordEntity(name, WordCategory.VERB);
    }

    private WordEntity makeAdjective(final String name) {
        return new WordEntity(name, WordCategory.ADJECTIVE);
    }

}
