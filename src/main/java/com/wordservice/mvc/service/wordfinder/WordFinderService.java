package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.dao.WordEntityDAO;
import com.wordservice.mvc.dao.WordRelationshipDAO;
import com.wordservice.mvc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WordFinderService {

    @Autowired
    private WordRelationshipDAO wordRelationshipDAO;

    @Autowired
    private WordEntityDAO wordEntityDAO;

    public List<WordEntity> getNextWordsViaTuple(String first, String second) {

        WordEntity firstWord = wordEntityDAO.findByWordViaIndexAndRegex(first);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordEntityDAO.findByWordViaIndexAndRegex(second);
        if (secondWord == null) return Collections.emptyList();

        List<WordEntity> nextWords = new ArrayList<>();
        for (WordRelationshipTuple relationship : wordRelationshipDAO.getRelationshipsBetweenAsIterable(firstWord, secondWord)) {
            long third = relationship.getThird();
            if(third != 0) {
                nextWords.add(wordEntityDAO.findById(third));
            }
        }

        return nextWords;
    }

    public List<WordEntity> getNextWordsViaTuple(String first, String second, String third) {

        WordEntity firstWord = wordEntityDAO.findByWordViaIndexAndRegex(first);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordEntityDAO.findByWordViaIndexAndRegex(second);
        if (secondWord == null) return Collections.emptyList();
        WordEntity thirdWord = wordEntityDAO.findByWordViaIndexAndRegex(third);
        if (thirdWord == null) return Collections.emptyList();

        List<WordRelationshipTuple> relationships =
                wordRelationshipDAO.getRelationshipsBetweenAsList(firstWord, secondWord, thirdWord);

        List<WordEntity> nextWords = new ArrayList<>();
        for (WordRelationshipTuple relationship : relationships) {
            long fourth = relationship.getFourth();
            if(fourth != 0) {
                nextWords.add(wordEntityDAO.findById(fourth));
            }
        }

        return nextWords;
    }


}
