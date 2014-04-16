package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepositoryImpl;
import com.wordservice.mvc.service.wordsaver.WordEntitySaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ilja on 4/8/2014.
 */
@Controller
@RequestMapping("/word-completion-api/")
public class WordControllerImpl {

    @Autowired
    private WordRepositoryImpl wordRepositoryCached;

    @Autowired
    private WordEntitySaverService wordEntitySaverService;

    @RequestMapping(value = "getTopFor/{word}", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word) {
        return wordRepositoryCached.getTop10WordsAfter(word);
    }

    @RequestMapping(value = "getTopFor/{word1}/{word2}", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word1, @PathVariable String word2) {
        return wordRepositoryCached.getTop10WordsAfter(word1,word2);
    }

    @RequestMapping(value = "wordApi" , method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody String jsonString) {
        wordEntitySaverService.saveToRepo(jsonString);
    }
}
