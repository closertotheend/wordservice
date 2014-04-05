package com.wordservice.mvc.model;

import com.wordservice.mvc.TestApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.repository.GraphRepository;
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

    @Test
    @Transactional
    public void persistedMovieShouldBeRetrievableFromGraphDb() {
        WordEntity gump = new WordEntity("Gump");
        WordEntity forrest = new WordEntity("Forrest");
        WordEntity lump = new WordEntity("Lump");
        WordRelationship wordRelationship = new WordRelationship(forrest,gump);
        WordRelationship wordRelationship2 = new WordRelationship(forrest,lump);

        template.save(gump);
        template.save(forrest);
        template.save(lump);
        template.save(wordRelationship);
        template.save(wordRelationship2);

        assertEquals("retrieved id matches persisted one", gump, template.findOne(gump.getId(), WordEntity.class));
        assertEquals("retrieved word matches persisted one", gump.getWord(), template.findOne(gump.getId(), WordEntity.class).getWord());
        assertEquals("retrieved id matches persisted one", forrest, template.findOne(forrest.getId(), WordEntity.class));
        assertEquals("retrieved word matches persisted one", forrest.getWord(), template.findOne(forrest.getId(), WordEntity.class).getWord());

        GraphRepository<WordEntity> wordRepository = template.repositoryFor(WordEntity.class);
        assertEquals(gump, wordRepository.findAllByPropertyValue("word", "Gump").single());
        assertEquals(2,template.findOne(forrest.getId(), WordEntity.class).getFollowedWords().size());
    }

}
