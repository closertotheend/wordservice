package com.wordservice.mvc.service.wordfinder;

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
    private WordRelationshipRepository wordRelationshipRepository;

    @Autowired
    private WordTupleRepository wordTupleRepository;

    @Autowired
    private WordTriTupleRepository wordTriTupleRepository;

    @Autowired
    private WordRelationshipTupleDAO wordRelationshipTupleDAO;

    @Autowired
    private WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;

    @Autowired
    private WordRepository wordRepository;


    public List<WordEntity> getNextWords(String word1, String word2) {

        WordEntity firstWord = wordRepositoryFixedIndexesSearch.findByWord(word1);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordRepositoryFixedIndexesSearch.findByWord(word2);
        if (secondWord == null) return Collections.emptyList();
        WordRelationship relationshipBetween = wordRelationshipRepository.getRelationshipBetween(firstWord, secondWord);
        if (relationshipBetween == null) return Collections.emptyList();

        List<WordTuple> tuplesWithRelationShipIds = wordTupleRepository.getTuplesWithRelationShipIds(relationshipBetween.getId());

        List<WordEntity> words = new ArrayList<>();
        for (WordTuple tuplesWithRelationShipId : tuplesWithRelationShipIds) {
            WordRelationship nextWordRelationship = wordRelationshipRepository.findOne(tuplesWithRelationShipId.getSecondWordRelationshipId());
            words.add(wordRepository.findOne(
                    nextWordRelationship.getSecondWord().getId()
            ));
        }

        return words;
    }

    public List<WordEntity> getNextWords(String word1, String word2, String word3) {

        WordEntity firstWord = wordRepositoryFixedIndexesSearch.findByWord(word1);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordRepositoryFixedIndexesSearch.findByWord(word2);
        if (secondWord == null) return Collections.emptyList();
        WordEntity thirdWord = wordRepositoryFixedIndexesSearch.findByWord(word3);
        if (thirdWord == null) return Collections.emptyList();

        WordRelationship relationshipBetween1 = wordRelationshipRepository.getRelationshipBetween(firstWord, secondWord);
        if (relationshipBetween1 == null) return Collections.emptyList();

        WordRelationship relationshipBetween2 = wordRelationshipRepository.getRelationshipBetween(secondWord, thirdWord);
        if (relationshipBetween2 == null) return Collections.emptyList();

        List<WordTriTuple> tuplesWithRelationShipIds =
                wordTriTupleRepository.getWithRelationShipIds(relationshipBetween1.getId(), relationshipBetween2.getId());

        List<WordEntity> words = new ArrayList<>();
        for (WordTriTuple tuplesWithRelationShipId : tuplesWithRelationShipIds) {
            WordRelationship nextWordRelationship = wordRelationshipRepository.findOne(tuplesWithRelationShipId.getThirdWordRelationshipId());
            words.add(wordRepository.findOne(
                    nextWordRelationship.getSecondWord().getId()
            ));
        }

        return words;
    }

    public List<WordEntity> getNextWordsViaTuple(String word1, String word2, String word3) {

        WordEntity firstWord = wordRepositoryFixedIndexesSearch.findByWord(word1);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordRepositoryFixedIndexesSearch.findByWord(word2);
        if (secondWord == null) return Collections.emptyList();
        WordEntity thirdWord = wordRepositoryFixedIndexesSearch.findByWord(word3);
        if (thirdWord == null) return Collections.emptyList();

        WordRelationship relationshipBetween1 = wordRelationshipRepository.getRelationshipBetween(firstWord, secondWord);
        if (relationshipBetween1 == null) return Collections.emptyList();

        WordRelationship relationshipBetween2 = wordRelationshipRepository.getRelationshipBetween(secondWord, thirdWord);
        if (relationshipBetween2 == null) return Collections.emptyList();

        List<WordTriTuple> tuplesWithRelationShipIds =
                wordTriTupleRepository.getWithRelationShipIds(relationshipBetween1.getId(), relationshipBetween2.getId());

        List<WordEntity> words = new ArrayList<>();
        for (WordTriTuple tuplesWithRelationShipId : tuplesWithRelationShipIds) {
            WordRelationship nextWordRelationship = wordRelationshipRepository.findOne(tuplesWithRelationShipId.getThirdWordRelationshipId());
            words.add(wordRepository.findOne(
                    nextWordRelationship.getSecondWord().getId()
            ));
        }

        return words;
    }


}
