package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.repository.WordRepositoryFixedIndexesSearch;
import com.wordservice.mvc.service.wordfinder.WordTupleFinderService;
import com.wordservice.mvc.service.wordsaver.SentencesToWords;
import com.wordservice.mvc.service.wordsaver.TextSaverService;
import com.wordservice.mvc.service.wordsaver.TextToSentences;
import com.wordservice.mvc.util.CleanUtil;
import com.wordservice.mvc.util.WordPopularityComparator;
import junit.framework.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static com.wordservice.mvc.util.CleanUtil.*;

@Controller
public class WordControllerImpl {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordTupleFinderService wordTupleFinderService;


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


    @RequestMapping(value = "context/{f}/{s}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s) {
        return wordTupleFinderService.getNextWords(clean(f), clean(s));
    }

    @RequestMapping(value = "context/{f}/{s}/{t}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s, @PathVariable String t) {
        return wordTupleFinderService.getNextWords(clean(f), clean(s), clean(t));
    }

}
