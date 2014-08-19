package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.dao.WordEntityDAO;
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
@RequestMapping("/")
public class WordCompletionController {
    @Autowired
    private WordEntityDAO wordEntityDAO;


    @RequestMapping(value = "getWordStartingWith/{wordStart}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getWordStartingWith(@PathVariable String wordStart) {
        List<WordEntity> startingWords = wordEntityDAO.findByWordStartingWithViaIndex(clean(wordStart));
        Collections.sort(startingWords);
        return startingWords;
    }

    @RequestMapping(value = "getWordContaining/{sequence}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getWordContaining(@PathVariable String sequence) {
        List<WordEntity> containingWords = wordEntityDAO.findByWordContainingViaIndex(clean(sequence));
        Collections.sort(containingWords);
        return containingWords;
    }

}
