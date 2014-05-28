package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.repository.WordRepositoryFixedIndexesSearch;
import com.wordservice.mvc.service.wordfinder.SentenceContextWordFinderService;
import com.wordservice.mvc.service.wordsaver.WordEntitySaverService;
import com.wordservice.mvc.util.WordPopularityComparator;
import org.springframework.beans.factory.annotation.Autowired;
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
        return wordRepository.getTop10WordsAfter(word);
    }

    @RequestMapping(value = "getTopFor/{word1}/{word2}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word1, @PathVariable String word2) {
        return wordRepository.getTop10WordsAfter(word1, word2);
    }

    @RequestMapping(value = "getSentence/{word1}/{word2}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getSentence(@PathVariable String word1, @PathVariable String word2) {
        return sentenceContextWordFinderService.getNextWords(word1, word2);
    }

    @RequestMapping(value = "getWordStartingWith/{string}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getWordStartingWith(@PathVariable String string) {
        List<WordEntity> startingWords = wordRepositoryFixedIndexesSearch.findByWordStartingWith(string);
        Collections.sort(startingWords, new WordPopularityComparator());
        return startingWords;
    }

    @RequestMapping(value = "getWordContaining/{string}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getWordContaining(@PathVariable String string) {
        List<WordEntity> containingWords = wordRepository.findByWordContaining(string);
        Collections.sort(containingWords, new WordPopularityComparator());
        return containingWords;
    }

    @RequestMapping(value = "wordApi", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody String jsonString) {
        wordEntitySaverService.saveToRepo(jsonString);
    }
}
