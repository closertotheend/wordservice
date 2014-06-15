package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SentenceRepositoryIT extends IntegrationTestsBase{


    @Rollback
    @Test
    public void testGetSentencesWithRelationshipId() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja! I am neo4j server. You should test wordrelationship I am twice!");

        WordEntity hello = wordRepositoryFixedIndexesSearch.findByWord("Hello");
        WordEntity ilja = wordRepositoryFixedIndexesSearch.findByWord("Ilja");
        WordEntity i = wordRepositoryFixedIndexesSearch.findByWord("I");
        WordEntity am = wordRepositoryFixedIndexesSearch.findByWord("am");
        WordEntity neo4j = wordRepositoryFixedIndexesSearch.findByWord("neo4j");
        WordEntity server = wordRepositoryFixedIndexesSearch.findByWord("server");
        WordEntity you = wordRepositoryFixedIndexesSearch.findByWord("You");
        WordEntity should = wordRepositoryFixedIndexesSearch.findByWord("should");
        WordEntity test = wordRepositoryFixedIndexesSearch.findByWord("test");
        WordEntity wordrelationship = wordRepositoryFixedIndexesSearch.findByWord("wordrelationship");
        WordEntity twice = wordRepositoryFixedIndexesSearch.findByWord("twice");
        WordEntity nonexisting = wordRepositoryFixedIndexesSearch.findByWord("nonexisting");

        assertNotNull(hello);
        assertNotNull(ilja);
        assertNotNull(i);
        assertNotNull(am);
        assertNotNull(neo4j);
        assertNotNull(server);
        assertNotNull(you);
        assertNotNull(should);
        assertNotNull(test);
        assertNotNull(wordrelationship);
        assertNotNull(twice);
        assertNull(nonexisting);

        assertNotNull(wordRelationshipRepository.getRelationshipBetween(hello,ilja));
        assertNotNull(wordRelationshipRepository.getRelationshipBetween(i, am));
        assertNull(wordRelationshipRepository.getRelationshipBetween(hello, hello));


        WordRelationship one = wordRelationshipRepository.getRelationshipBetween(hello,ilja);
        assertNotNull(one);
        List<Sentence> sentences1 = sentenceRepository.getSentencesWithRelationshipId(one.getId());
        assertEquals(1, sentences1.size());

        WordRelationship two = wordRelationshipRepository.getRelationshipBetween(neo4j,server);
        assertNotNull(two);
        List<Sentence> sentences2 = sentenceRepository.getSentencesWithRelationshipId(one.getId());
        assertEquals(1, sentences2.size());

        WordRelationship three = wordRelationshipRepository.getRelationshipBetween(i,am);
        assertNotNull(three);
        List<Sentence> sentences3 = sentenceRepository.getSentencesWithRelationshipId(three.getId());
        assertEquals(2, sentences3.size());

        // process first result
        Long idOfRealtionshipIam = wordRelationshipRepository.getRelationshipBetween(i, am).getId();
        WordRelationship wordrelationship1 = wordRelationshipRepository.findOne(sentences3.get(0).getWordRelationships().get(
                sentences3.get(0).getWordRelationships().indexOf(idOfRealtionshipIam)
        ));
        assertEquals("I", wordRepository.findOne(wordrelationship1.getStartWord().getId()).getWord());
        assertEquals("am", wordRepository.findOne(wordrelationship1.getSecondWord().getId()).getWord());



        // process second result
        WordRelationship wordrelationship2 = wordRelationshipRepository.findOne(sentences3.get(1).getWordRelationships().get(
                sentences3.get(1).getWordRelationships().indexOf(idOfRealtionshipIam)
        ));
        assertEquals("I", wordRepository.findOne(wordrelationship2.getStartWord().getId()).getWord());
        assertEquals("am", wordRepository.findOne(wordrelationship2.getSecondWord().getId()).getWord());
    }
}
