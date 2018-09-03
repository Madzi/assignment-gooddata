package com.gooddata.domain;

import com.gooddata.dao.WordsRepository;
import com.gooddata.domain.impl.WordsServiceImpl;
import com.gooddata.domain.model.Category;
import com.gooddata.domain.model.TestWord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WordsServiceTests {

    @Autowired
    WordsService wordsService;

    @Test
    @DisplayName("WordsService successfully autowired")
    void testWordsServiceAutowired() {
        Assertions.assertNotNull(wordsService);
    }

    @Test
    @DisplayName("All known words can be received")
    void testGetAllWords() {
        // Given
        wordsService.addWord(new TestWord("Test", Category.NOUN));
        var expectedResults = wordsService.getAllWords();

        // When
        var actualResults = wordsService.getAllWords();

        // Then
        Assertions.assertEquals(expectedResults, actualResults);
    }

    @Test
    @DisplayName("Word can be added and then received")
    void testAddAndGetWord() {
        // Given
        var expectedResult = new TestWord("new-word-test", Category.ADJECTIVE);
        wordsService.addWord(expectedResult);

        // When
        var actualResult = wordsService.getWordsForName("new-word-test");

        // Then
        Assertions.assertTrue(actualResult.size() == 1);
        Assertions.assertTrue(actualResult.contains(expectedResult));
    }

}
