package com.wordservice.mvc.controller;

import com.wordservice.mvc.service.wordsaver.SentencesToWords;
import com.wordservice.mvc.service.wordsaver.TextSaverService;
import com.wordservice.mvc.service.wordsaver.TextToSentences;
import com.wordservice.mvc.util.CleanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/")
public class TextSaveController {
    @Autowired
    private TextSaverService textSaverService;

    @RequestMapping(value = "wordApi", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            textSaverService.saveToRepo(SentencesToWords.transform(CleanUtil.prepareTextForSave(sentence)));
        }
    }

    @RequestMapping(value = "saveFromFile", method = RequestMethod.GET)
    @ResponseBody
    public void saveFromFile() throws IOException {
        save(readFile("martin-eden.txt", StandardCharsets.UTF_8));
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
