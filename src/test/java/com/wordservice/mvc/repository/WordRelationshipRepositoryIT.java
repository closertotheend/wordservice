package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static junit.framework.Assert.*;

public class WordRelationshipRepositoryIT extends IntegrationTestsBase {


    @Test
    @Rollback
    public void getTuple() throws Exception {
        textSaverService.saveToRepo("Good news everyone here! Good news everyone there!");

        WordEntity good = wordEntityDAO.findByWordViaIndexAndRegex("Good");
        assertNotNull(good);
        WordEntity news = wordEntityDAO.findByWordViaIndexAndRegex("news");
        assertNotNull(news);
        WordEntity everyone = wordEntityDAO.findByWordViaIndexAndRegex("everyone");
        assertNotNull(everyone);
        WordEntity here = wordEntityDAO.findByWordViaIndexAndRegex("here");
        assertNotNull(here);

        assertNotNull(wordRelationshipRepository.getTuple(good.getId(), news.getId(), everyone.getId(), here.getId()));

        assertEquals(2, wordRelationshipRepository.getTuple(good.getId(), news.getId(), everyone.getId()).size());

    }


}
