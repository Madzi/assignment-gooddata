package com.gooddata.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gooddata.domain.SentencesService;
import com.gooddata.domain.model.Sentence;
import com.gooddata.web.dto.WebSentence;

@RestController
public class SentencesController {

    @Autowired
    private SentencesService sentencesService;

    @GetMapping(value = "/sentences", produces = "application/json")
    public List<WebSentence> getSentenceList() {
        return toWebSentences(sentencesService.findAll());
    }

    @GetMapping(value = "/sentences/{sentenceId}", produces = "application/json")
    public WebSentence getSentence(@PathVariable final Long sentenceId) {
        return toWebSentence(sentencesService.findById(sentenceId), false);
    }

    @GetMapping(value = "/sentences/{sentenceId}/yodaTalk", produces = "application/json")
    public WebSentence getYodaSentence(@PathVariable final Long sentenceId) {
        return toWebSentence(sentencesService.findById(sentenceId), true);
    }

    @PostMapping(value = "/sentences/generate")
    public WebSentence generate() {
        return new WebSentence(sentencesService.generate());
    }

    private List<WebSentence> toWebSentences(final List<Sentence> sentences) {
        return sentences.stream().map(WebSentence::new).collect(Collectors.toList());
    }

    private WebSentence toWebSentence(final Optional<Sentence> sentence, final boolean yodaTalk) {
        return sentence.map(it -> new WebSentence(it, yodaTalk)).orElseThrow();
    }

}
