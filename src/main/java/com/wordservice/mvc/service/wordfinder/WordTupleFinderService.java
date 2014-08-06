package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.dao.WordEntityDAO;
import com.wordservice.mvc.dao.WordRelationshipDAO;
import com.wordservice.mvc.dao.WordRelationshipTupleDAO;
import com.wordservice.mvc.model.*;
import com.wordservice.mvc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class WordTupleFinderService {

    @Autowired
    private WordRelationshipDAO wordRelationshipDAO;

    @Autowired
    private WordRelationshipTupleDAO wordRelationshipTupleDAO;

    @Autowired
    private WordEntityDAO wordEntityDAO;

    @Autowired
    private WordEntityRepository wordEntityRepository;

    public List<WordEntity> getNextWordsViaTuple(String first, String second) {

        WordEntity firstWord = wordEntityDAO.findByWord(first);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordEntityDAO.findByWord(second);
        if (secondWord == null) return Collections.emptyList();

        List<WordRelationshipTuple> relationships =
                wordRelationshipTupleDAO.getRelationshipsBetweenAsList(firstWord, secondWord);

        List<WordEntity> nextWords = new ArrayList<>();
        for (WordRelationshipTuple relationship : relationships) {
            nextWords.add(wordEntityDAO.findById(relationship.getThird()));
        }

        return nextWords;
    }

    public List<WordEntity> getNextWordsViaTuple(String first, String second, String third) {

        WordEntity firstWord = wordEntityDAO.findByWord(first);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordEntityDAO.findByWord(second);
        if (secondWord == null) return Collections.emptyList();
        WordEntity thirdWord = wordEntityDAO.findByWord(third);
        if (thirdWord == null) return Collections.emptyList();

        List<WordRelationshipTuple> relationships =
                wordRelationshipTupleDAO.getRelationshipsBetweenAsList(firstWord, secondWord, thirdWord);

        List<WordEntity> nextWords = new ArrayList<>();
        for (WordRelationshipTuple relationship : relationships) {
            nextWords.add(wordEntityDAO.findById(relationship.getFourth()));
        }

        return nextWords;
    }


}
