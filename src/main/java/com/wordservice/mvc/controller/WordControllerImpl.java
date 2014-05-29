package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.repository.WordRepositoryFixedIndexesSearch;
import com.wordservice.mvc.service.wordfinder.SentenceContextWordFinderService;
import com.wordservice.mvc.service.wordsaver.WordEntitySaverService;
import com.wordservice.mvc.util.WordPopularityComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class WordControllerImpl {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;

    @Autowired
    private WordEntitySaverService wordEntitySaverService;

    @Autowired
    private SentenceContextWordFinderService sentenceContextWordFinderService;

    @RequestMapping(value = "getTopFor/{word}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word) {
        return wordRepository.getTop10WordsAfter(clean(word));
    }

    @RequestMapping(value = "getTopFor/{previous}/{last}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String previous, @PathVariable String last) {
        return wordRepository.getTop10WordsAfter(clean(previous), clean(last));
    }

    @RequestMapping(value = "getSentence/{previous}/{last}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getSentence(@PathVariable String previous, @PathVariable String last) {
        return sentenceContextWordFinderService.getNextWords(clean(previous), clean(last));
    }

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

    @RequestMapping(value = "wordApi", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody String text) {
        wordEntitySaverService.saveToRepo(clean(text));
    }

    private static String clean(String word) {
        return word.replaceAll("/[^A-Za-z0-9 ]/", "").trim();
    }
}
