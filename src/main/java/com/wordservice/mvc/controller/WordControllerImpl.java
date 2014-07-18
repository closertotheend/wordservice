package com.wordservice.mvc.controller;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.repository.WordRepositoryFixedIndexesSearch;
import com.wordservice.mvc.service.wordfinder.WordTupleFinderService;
import com.wordservice.mvc.service.wordsaver.SentencesToWords;
import com.wordservice.mvc.service.wordsaver.TextSaverService;
import com.wordservice.mvc.service.wordsaver.TextToSentences;
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

@Controller
public class WordControllerImpl {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;

    @Autowired
    private TextSaverService textSaverService;

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
        return wordRepository.getTop10WordsAfter(clean(f), clean(s));
    }

    @RequestMapping(value = "context/{f}/{s}/{t}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<WordEntity> getByFirstTwo(@PathVariable String f, @PathVariable String s, @PathVariable String t) {
        return wordTupleFinderService.getNextWords(clean(f), clean(s), clean(t));
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
        saveWithShortTransaction(text);
    }

    private void saveWithShortTransaction(String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(sentence);
            textSaverService.saveToRepo(words);
        }
    }

    @RequestMapping(value = "saveFromFile", method = RequestMethod.GET)
    @ResponseBody
    public void saveFromFile() throws IOException {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        saveWithShortTransaction(readFile("big.txt", StandardCharsets.UTF_8));
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static String clean(String word) {
        return word.replaceAll("/[^A-Za-z0-9 ]/", "").trim();
    }
}
