package com.wordservice.mvc.model;

import com.wordservice.mvc.TestApplicationConfig;
import com.wordservice.mvc.repository.WordRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created by ilja on 3/31/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplicationConfig.class})
@Transactional
public class WordIntegrationTest {

    @Autowired
    Neo4jTemplate template;

    @Autowired
    WordRepositoryImpl wordRepositoryImpl;

    @Test
    @Transactional
    public void persistedMovieShouldBeRetrievableFromGraphDb() {
        WordEntity gump = new WordEntity("Gump");
        WordEntity forrest = new WordEntity("Forrest");
        WordEntity lump = new WordEntity("Lump");
        WordRelationship wordRelationship = new WordRelationship(forrest,gump);
        WordRelationship wordRelationship2 = new WordRelationship(forrest,lump);

        wordRepositoryImpl.save(gump);
        wordRepositoryImpl.save(forrest);
        wordRepositoryImpl.save(lump);
        template.save(wordRelationship);
        template.save(wordRelationship2);

        assertEquals("retrieved id matches persisted one", gump, wordRepositoryImpl.findOne(gump.getId()));
        assertEquals("retrieved word matches persisted one", gump.getWord(), wordRepositoryImpl.findOne(gump.getId()).getWord());
        assertEquals("retrieved id matches persisted one", forrest, wordRepositoryImpl.findOne(forrest.getId()));
        assertEquals("retrieved word matches persisted one", forrest.getWord(), wordRepositoryImpl.findOne(forrest.getId()).getWord());

        assertEquals(gump, wordRepositoryImpl.findAllByPropertyValue("word", "Gump").single());
       // assertEquals(2,wordRepository.findOne(forrest.getId()).getFollowedAfterWords().size());
       // assertEquals(2, wordRepositoryImpl.findOne(forrest.getId()).getRelationships().size());
    }

}
