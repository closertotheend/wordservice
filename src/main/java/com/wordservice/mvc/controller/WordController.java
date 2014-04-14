package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.service.WordEntitySaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ilja on 4/8/2014.
 */
@Controller
@RequestMapping("/word-completion-api/")
public class WordController {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    WordEntitySaverService wordEntitySaverService;

    @RequestMapping(value = "getTopFor/{word}", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word) {
        return wordRepository.getTop10WordsAfter(word);
    }

    @RequestMapping(value = "wordApi" , method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody String jsonString) {
        wordEntitySaverService.saveToRepo(jsonString);
    }
}
