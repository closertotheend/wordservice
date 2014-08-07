package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordEntityRepository;
import com.wordservice.mvc.service.wordfinder.WordTupleFinderService;
import com.wordservice.mvc.util.WordPopularityComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
        Collections.sort(top10WordsAfter, new WordPopularityComparator());
        return top10WordsAfter;
    }

    @RequestMapping(value = "context/{f}/{s}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s) {
        List<WordEntity> nextWordsViaTuple = wordTupleFinderService.getNextWordsViaTuple(clean(f), clean(s));
        Collections.sort(nextWordsViaTuple, new WordPopularityComparator());
        return nextWordsViaTuple;
    }

    @RequestMapping(value = "context/{f}/{s}/{t}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s, @PathVariable String t) {
        List<WordEntity> nextWordsViaTuple = wordTupleFinderService.getNextWordsViaTuple(clean(f), clean(s), clean(t));
        Collections.sort(nextWordsViaTuple, new WordPopularityComparator());
        return nextWordsViaTuple;
    }

}
