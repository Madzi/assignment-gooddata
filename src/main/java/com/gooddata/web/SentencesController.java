package com.gooddata.web;

import com.gooddata.domain.SentencesService;
import com.gooddata.web.model.WebSentence;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Access point for to Sentences service.
 */
@RestController
public class SentencesController {

    @Autowired
    private SentencesService sentencesService;

    @GetMapping("/sentences")
    public List<WebSentence> listAllSentences() {
        return sentencesService.getAllSentences().stream().map(WebSentence::new).collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/sentences/{sententceId}")
    public WebSentence getSentenceById(@PathVariable String sentenceId) {
        return sentencesService.getSentenceById(sentenceId).map(WebSentence::new).get();
    }

    @GetMapping("/sentences/{sentencesId}/yodaTalk")
    public WebSentence getYodaSentenceById(@PathVariable String sentenceId) {
        return sentencesService.getSentenceById(sentenceId).map(snt -> new WebSentence(snt, true)).get();
    }

    @PostMapping("/sentences/generate")
    public WebSentence generateSentence() {
        var sentence = sentencesService.generate();
        return new WebSentence(sentence);
    }

}
