package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.repository.WordRelationshipRepository;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.repository.WordRepositoryFixedIndexesSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordTupleFinderService {

    @Autowired
    private WordRelationshipRepository wordRelationshipRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;


//    public List<WordEntity> getNextWords(String word1, String word2){
//        WordEntity firstWord = wordRepositoryFixedIndexesSearch.findByWord(word1);
//        if (firstWord == null) return Collections.emptyList();
//        WordEntity secondWord = wordRepositoryFixedIndexesSearch.findByWord(word2);
//        if (secondWord == null) return Collections.emptyList();
//        WordRelationship relationshipBetween1and2 = wordRelationshipRepository.getRelationshipBetween(firstWord, secondWord);
//        if (relationshipBetween1and2 == null) return Collections.emptyList();
//
//        List<Sentence> sentencesWithRelationshipId = sentenceRepository.getSentencesWithRelationshipId(relationshipBetween1and2.getId());
//
//        List<WordEntity> words = new ArrayList<>();
//        for (Sentence sentence : sentencesWithRelationshipId) {
//            int indexOfRelationship = sentence.getWordRelationships().indexOf(relationshipBetween1and2.getId());
//            if (relationshipIsNotLastInSentece(sentence, indexOfRelationship)){
//                Long idOfNextRelationship = sentence.getWordRelationships().get(indexOfRelationship + 1);
//                WordRelationship nextWordRelationship = wordRelationshipRepository.findOne(idOfNextRelationship);
//                words.add(wordRepository.findOne(
//                        nextWordRelationship.getSecondWord().getId()
//                ));
//            }
//        }
//
//        return words;
//    }

    private boolean relationshipIsNotLastInSentece(Sentence sentence, int indexOfRelationship) {
        return sentence.getWordRelationships().size() > indexOfRelationship + 1;
    }
}
