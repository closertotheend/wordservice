package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.dao.WordEntityDAO;
import com.wordservice.mvc.dao.WordRelationshipDAO;
import com.wordservice.mvc.dao.WordRelationshipTupleDAO;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.model.WordTriTuple;
import com.wordservice.mvc.model.WordTuple;
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
    private WordTupleRepository wordTupleRepository;

    @Autowired
    private WordTriTupleRepository wordTriTupleRepository;

    @Autowired
    private WordRelationshipTupleDAO wordRelationshipTupleDAO;

    @Autowired
    private WordEntityDAO wordEntityDAO;

    @Autowired
    private WordRepository wordRepository;


    public List<WordEntity> getNextWords(String word1, String word2) {

        WordEntity firstWord = wordEntityDAO.findByWord(word1);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordEntityDAO.findByWord(word2);
        if (secondWord == null) return Collections.emptyList();
        WordRelationship relationshipBetween = wordRelationshipDAO.getRelationshipBetween(firstWord, secondWord);
        if (relationshipBetween == null) return Collections.emptyList();

        List<WordTuple> tuplesWithRelationShipIds = wordTupleRepository.getTuplesWithRelationShipIds(relationshipBetween.getId());

        List<WordEntity> words = new ArrayList<>();
        for (WordTuple tuplesWithRelationShipId : tuplesWithRelationShipIds) {
            WordRelationship nextWordRelationship = wordRelationshipDAO.findOne(tuplesWithRelationShipId.getSecondWordRelationshipId());
            words.add(wordRepository.findOne(
                    nextWordRelationship.getSecondWord().getId()
            ));
        }

        return words;
    }

    public List<WordEntity> getNextWords(String word1, String word2, String word3) {

        WordEntity firstWord = wordEntityDAO.findByWord(word1);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordEntityDAO.findByWord(word2);
        if (secondWord == null) return Collections.emptyList();
        WordEntity thirdWord = wordEntityDAO.findByWord(word3);
        if (thirdWord == null) return Collections.emptyList();

        WordRelationship relationshipBetween1 = wordRelationshipDAO.getRelationshipBetween(firstWord, secondWord);
        if (relationshipBetween1 == null) return Collections.emptyList();

        WordRelationship relationshipBetween2 = wordRelationshipDAO.getRelationshipBetween(secondWord, thirdWord);
        if (relationshipBetween2 == null) return Collections.emptyList();

        List<WordTriTuple> tuplesWithRelationShipIds =
                wordTriTupleRepository.getWithRelationShipIds(relationshipBetween1.getId(), relationshipBetween2.getId());

        List<WordEntity> words = new ArrayList<>();
        for (WordTriTuple tuplesWithRelationShipId : tuplesWithRelationShipIds) {
            WordRelationship nextWordRelationship = wordRelationshipDAO.findOne(tuplesWithRelationShipId.getThirdWordRelationshipId());
            words.add(wordRepository.findOne(
                    nextWordRelationship.getSecondWord().getId()
            ));
        }

        return words;
    }

//    public List<WordEntity> getNextWordsViaTuple(String first, String second, String third) {
//
//        WordEntity firstWord = wordEntityDAO.findByWord(first);
//        if (firstWord == null) return Collections.emptyList();
//        WordEntity secondWord = wordEntityDAO.findByWord(second);
//        if (secondWord == null) return Collections.emptyList();
//        WordEntity thirdWord = wordEntityDAO.findByWord(third);
//        if (thirdWord == null) return Collections.emptyList();
//
//        wordRelationshipTupleDAO.getRelationshipsBetweenAsList()
//
//        List<WordTriTuple> tuplesWithRelationShipIds =
//                wordTriTupleRepository.getWithRelationShipIds(relationshipBetween1.getId(), relationshipBetween2.getId());
//
//        List<WordEntity> words = new ArrayList<>();
//        for (WordTriTuple tuplesWithRelationShipId : tuplesWithRelationShipIds) {
//            WordRelationship nextWordRelationship = wordRelationshipDAO.findOne(tuplesWithRelationShipId.getThirdWordRelationshipId());
//            words.add(wordRepository.findOne(
//                    nextWordRelationship.getSecondWord().getId()
//            ));
//        }
//
//        return words;
//    }


}
