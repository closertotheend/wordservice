package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepositoryFixedIndexesSearch;
import com.wordservice.mvc.util.CleanUtil;
import com.wordservice.mvc.util.WordPopularityComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

import static com.wordservice.mvc.util.CleanUtil.*;

@Controller
public class WordCompletionController {
    @Autowired
    private WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;


    @RequestMapping(value = "getWordStartingWith/{wordStart}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getWordStartingWith(@PathVariable String wordStart) {
        List<WordEntity> startingWords = wordRepositoryFixedIndexesSearch.findByWordStartingWith(clean(wordStart));
        Collections.sort(startingWords, new WordPopularityComparator());
        return startingWords;
    }

    @RequestMapping(value = "getWordContaining/{sequence}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getWordContaining(@PathVariable String sequence) {
        List<WordEntity> containingWords = wordRepositoryFixedIndexesSearch.findByWordContaining(clean(sequence));
        Collections.sort(containingWords, new WordPopularityComparator());
        return containingWords;
    }

}
