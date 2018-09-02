package com.gooddata.web;

import com.gooddata.domain.SentencesService;
import com.gooddata.web.model.WebSentence;
import java.util.List;
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

    @GetMapping("/v1/sentences")
    public List<WebSentence> listAllSentences() {
        var knownSentences = sentencesService.getAllSentences();
        return null;
    }

    @GetMapping("/v1/sentences/{sententceId}")
    public WebSentence getSentenceById(@PathVariable String sentenceId) {
        return sentencesService.getSentenceById(sentenceId).map(WebSentence::new).get();
    }

    @GetMapping("/v1/sentences/{sentencesId}/yodaTalk")
    public WebSentence getYodaSentenceById(@PathVariable String sentenceId) {
        return sentencesService.getSentenceById(sentenceId).map(snt -> new WebSentence(snt, true)).get();
    }

    @PostMapping("/v1/sentences/generate")
    public WebSentence generateSentence() {
        var sentence = sentencesService.generate();
        return new WebSentence(sentence);
    }

}
