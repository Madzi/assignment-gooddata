package com.gooddata.web;

import com.gooddata.domain.WordsService;
import com.gooddata.web.model.WebWord;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Access point to Words service.
 */
@RestController
public class WordsController {

    @Autowired
    private WordsService wordsService;

    @GetMapping(value = "/v1/words", produces = "application/json")
    public List<WebWord> getAllWords() {
        return wordsService.getAllWords().stream().map(WebWord::new).collect(Collectors.toList());
    }

    @GetMapping(value = "/v1/words/{name}", produces = "application/json")
    public List<WebWord> getWord(@PathVariable String name) {
        return wordsService.getWordsForName(name).stream().map(WebWord::new).collect(Collectors.toList());
    }

    @PostMapping(value = "/v1/words/{name}", consumes = "application/json", produces = "application/json")
    public WebWord addWord(@RequestBody WebWord webWord, @PathVariable String name) {
        webWord.setName(name);
        wordsService.addWord(webWord);
        return webWord;
    }

}
