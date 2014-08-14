package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordEntityRepository;
import com.wordservice.mvc.service.wordfinder.WordTupleFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.wordservice.mvc.util.CleanUtil.*;

@Controller
@RequestMapping("/")
public class WordController {

    @Autowired
    private WordEntityRepository wordEntityRepository;

    @Autowired
    private WordTupleFinderService wordTupleFinderService;


    @RequestMapping(value = "getTopFor/{word}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> get10TopWordsAfter(@PathVariable String word) {
        List<WordEntity> top10WordsAfter = new ArrayList<>(wordEntityRepository.getTop10WordsAfter(clean(word)));
        return top10WordsAfter;
    }

    @RequestMapping(value = "context/{f}/{s}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s) {
        List<WordEntity> nextWordsViaTuple = wordTupleFinderService.getNextWordsViaTuple(clean(f), clean(s));
        return nextWordsViaTuple;
    }

    @RequestMapping(value = "context/{f}/{s}/{t}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s, @PathVariable String t) {
        List<WordEntity> nextWordsViaTuple = wordTupleFinderService.getNextWordsViaTuple(clean(f), clean(s), clean(t));
        return nextWordsViaTuple;
    }

}
