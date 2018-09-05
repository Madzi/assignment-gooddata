package com.gooddata.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    private List<WebSentence> toWebSentences(final List<Sentence> sentences) {
        return sentences.stream().map(WebSentence::new).collect(Collectors.toList());
    }

}
