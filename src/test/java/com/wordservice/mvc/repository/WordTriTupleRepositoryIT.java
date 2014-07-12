package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.model.WordTriTuple;
import com.wordservice.mvc.model.WordTuple;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WordTriTupleRepositoryIT extends IntegrationTestsBase {

    @Test
    public void getTupleByRelationships() {
        WordEntity hello = new WordEntity("Hello");
        WordEntity ilja = new WordEntity("Ilja");
        WordEntity familyname = new WordEntity("Guzhovski");
        WordEntity junior = new WordEntity("Junior");

        wordRepository.save(hello);
        wordRepository.save(ilja);
        wordRepository.save(familyname);
        wordRepository.save(junior);

        WordRelationship helloIlja = new WordRelationship(hello, ilja);
        WordRelationship iljaGuzovski = new WordRelationship(ilja, familyname);
        WordRelationship guzovskiJunior = new WordRelationship(familyname, junior);
        wordRelationshipRepository.save(helloIlja);
        wordRelationshipRepository.save(iljaGuzovski);
        wordRelationshipRepository.save(guzovskiJunior);


        WordTriTuple wordTriTuple = new WordTriTuple(helloIlja.getId(), iljaGuzovski.getId(), guzovskiJunior.getId());
        wordTriTupleRepository.save(wordTriTuple);

        assertEquals(wordTriTuple, wordTriTupleRepository.getWithRelationShipIds(helloIlja.getId(), iljaGuzovski.getId(), guzovskiJunior.getId()));
        assertNull(wordTriTupleRepository.getWithRelationShipIds(iljaGuzovski.getId(), helloIlja.getId(), guzovskiJunior.getId()));

    }

    @Test
    public void getTuple() throws Exception {
        WordEntity hello = new WordEntity("Hello");
        WordEntity ilja = new WordEntity("Ilja");
        WordEntity familyname = new WordEntity("Guzhovski");
        WordEntity junior = new WordEntity("Junior");

        wordRepository.save(hello);
        wordRepository.save(ilja);
        wordRepository.save(familyname);
        wordRepository.save(junior);

        WordRelationship helloIlja = new WordRelationship(hello, ilja);
        WordRelationship iljaGuzovski = new WordRelationship(ilja, familyname);
        WordRelationship guzovskiJunior = new WordRelationship(familyname, junior);
        wordRelationshipRepository.save(helloIlja);
        wordRelationshipRepository.save(iljaGuzovski);
        wordRelationshipRepository.save(guzovskiJunior);


        WordTriTuple wordTriTuple = new WordTriTuple(helloIlja.getId(), iljaGuzovski.getId(), guzovskiJunior.getId());
        wordTriTupleRepository.save(wordTriTuple);

        assertEquals(1, wordTriTupleRepository.count());

        WordTriTuple one = wordTriTupleRepository.findOne(wordTriTuple.getId());
        assertEquals(helloIlja.getId(), one.getFirstWordRelationshipId());
        assertEquals(iljaGuzovski.getId(), one.getSecondWordRelationshipId());
        assertEquals(guzovskiJunior.getId(), one.getThirdWordRelationshipId());
    }

    @Test
    public void testGetWithRelationShipIds() throws Exception {
        textSaverService.saveToRepo("This is yellow car. This is yellow bus. This is black ledbetter.");

        WordRelationship thisIs =
                wordRelationshipRepository.getRelationshipBetween(wordRepositoryFixedIndexesSearch.findByWord("This"),
                wordRepositoryFixedIndexesSearch.findByWord("is"));

        WordRelationship isYellow =
                wordRelationshipRepository.getRelationshipBetween(wordRepositoryFixedIndexesSearch.findByWord("is"),
                wordRepositoryFixedIndexesSearch.findByWord("yellow"));


        List<WordTriTuple> withRelationShipIds =
                wordTriTupleRepository.getWithRelationShipIds(thisIs.getId(), isYellow.getId());

        assertEquals(2, withRelationShipIds.size());
    }
}