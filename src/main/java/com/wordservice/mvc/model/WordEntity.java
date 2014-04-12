package com.wordservice.mvc.model;

import com.wordservice.mvc.examples.LineItem;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ilja on 3/31/2014.
 */

@NodeEntity
public class WordEntity {

    @JsonIgnore
    @GraphId
    private Long id;

    @Indexed(unique = true, indexName = "word")
    private String word;

/*
    @JsonIgnore
    @RelatedTo(type = "IS_FOLLOWED_BY")
    private Set<WordEntity> followedAfterWords;
*/

    @JsonIgnore
    @RelatedToVia(type = "IS_FOLLOWED_BY")
    private Set<WordRelationship> relationships = new HashSet<>();

    private WordEntity() {
    }

    public WordEntity(String word) {
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    /*public Set<WordEntity> getFollowedAfterWords() {
        return followedAfterWords;
    }*/

    public Set<WordRelationship> getRelationships() {
        return relationships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordEntity that = (WordEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WordEntity{" +
                "id=" + id +
                ", word='" + word + '\'' +
                '}';
    }
}
