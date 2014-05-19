package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class SentenceRepositoryIT extends IntegrationTestsBase{


    @Rollback
    @Test
    public void testGetSentencesWithRelationshipId() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja! I am neo4j server. You should test wordrelationship I am twice!");

        WordEntity hello = wordRepositoryImpl.findByWord("Hello");
        WordEntity ilja = wordRepositoryImpl.findByWord("Ilja");
        WordEntity i = wordRepositoryImpl.findByWord("I");
        WordEntity am = wordRepositoryImpl.findByWord("am");
        WordEntity neo4j = wordRepositoryImpl.findByWord("neo4j");
        WordEntity server = wordRepositoryImpl.findByWord("server");
        WordEntity you = wordRepositoryImpl.findByWord("You");
        WordEntity should = wordRepositoryImpl.findByWord("should");
        WordEntity test = wordRepositoryImpl.findByWord("test");
        WordEntity wordrelationship = wordRepositoryImpl.findByWord("wordrelationship");
        WordEntity twice = wordRepositoryImpl.findByWord("twice");
        WordEntity nonexisting = wordRepositoryImpl.findByWord("nonexisting");

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
        assertEquals("I", wordRepositoryImpl.findOne(wordrelationship1.getStartWord().getId()).getWord());
        assertEquals("am", wordRepositoryImpl.findOne(wordrelationship1.getSecondWord().getId()).getWord());



        // process second result
        WordRelationship wordrelationship2 = wordRelationshipRepository.findOne(sentences3.get(1).getWordRelationships().get(
                sentences3.get(1).getWordRelationships().indexOf(idOfRealtionshipIam)
        ));
        assertEquals("I", wordRepositoryImpl.findOne(wordrelationship2.getStartWord().getId()).getWord());
        assertEquals("am", wordRepositoryImpl.findOne(wordrelationship2.getSecondWord().getId()).getWord());

        // process second result
        WordRelationship wordrelationship3 = wordRelationshipRepository.findOne(sentences3.get(1).getWordRelationships().get(
                sentences3.get(1).getWordRelationships().indexOf(idOfRealtionshipIam) +1
        ));
        assertEquals("am", wordRepositoryImpl.findOne(wordrelationship3.getStartWord().getId()).getWord());
        assertEquals("twice", wordRepositoryImpl.findOne(wordrelationship3.getSecondWord().getId()).getWord());

    }
}
