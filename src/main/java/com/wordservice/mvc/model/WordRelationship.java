package com.wordservice.mvc.model;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import scala.util.parsing.combinator.testing.Str;

@RelationshipEntity(type = "IS_FOLLOWED_BY")
public class WordRelationship {
    public static String relationshipType = "IS_FOLLOWED_BY";

    @GraphId
    private Long id;

    @StartNode
    private WordEntity startWord;

    @EndNode
    private WordEntity secondWord;

    private long popularity;

    private WordRelationship() {
    }

    public WordRelationship(WordEntity startWord, WordEntity secondWord) {
        this.startWord = startWord;
        this.secondWord = secondWord;
    }

    public WordEntity getStartWord() {
        return startWord;
    }

    public WordEntity getSecondWord() {
        return secondWord;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public void incrementPopularity() {
        this.popularity++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordRelationship that = (WordRelationship) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WordRelationship{" +
                "id=" + id +
                ", startWord=" + startWord +
                ", secondWord=" + secondWord +
                ", popularity=" + popularity +
                '}';
    }
}