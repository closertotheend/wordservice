package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.model.WordTuple;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WordTupleRepositoryIT extends IntegrationTestsBase {

    @Test
    public void getTuple() throws Exception {
        WordEntity hello = new WordEntity("Hello");
        WordEntity ilja = new WordEntity("Ilja");
        WordEntity familyname = new WordEntity("Guzhovski");

        wordRepository.save(hello);
        wordRepository.save(ilja);
        wordRepository.save(familyname);

        WordRelationship helloIlja = new WordRelationship(hello, ilja);
        WordRelationship iljaGuzovski = new WordRelationship(ilja, familyname);
        wordRelationshipRepository.save(helloIlja);
        wordRelationshipRepository.save(iljaGuzovski);


        WordTuple wordTuple = new WordTuple(helloIlja.getId(), iljaGuzovski.getId());
        wordTupleRepository.save(wordTuple);

        assertEquals(1, wordTupleRepository.count());

        WordTuple one = wordTupleRepository.findOne(wordTuple.getId());
        assertEquals(helloIlja.getId(), one.getfWordRelationshipId());
        assertEquals(iljaGuzovski.getId(), one.getSecondWordRelationshipId());
    }

    @Test
    public void shouldFindTuple() {
        WordEntity hello = new WordEntity("Hello");
        WordEntity ilja = new WordEntity("Ilja");
        WordEntity familyname = new WordEntity("Guzhovski");

        wordRepository.save(hello);
        wordRepository.save(ilja);
        wordRepository.save(familyname);

        WordRelationship helloIlja = new WordRelationship(hello, ilja);
        WordRelationship iljaGuzovski = new WordRelationship(ilja, familyname);
        wordRelationshipRepository.save(helloIlja);
        wordRelationshipRepository.save(iljaGuzovski);


        WordTuple wordTuple = new WordTuple(helloIlja.getId(), iljaGuzovski.getId());
        wordTupleRepository.save(wordTuple);

        WordTuple tupleWithRelationShipIds = wordTupleRepository.getTupleWithRelationShipIds(helloIlja.getId(), iljaGuzovski.getId());

        assertEquals(wordTuple,tupleWithRelationShipIds);
    }

}