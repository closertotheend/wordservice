package com.wordservice.mvc.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;

/**
 * Created by ilja on 3/31/2014.
 */

@NodeEntity
public class WordEntity {

    @GraphId
    private Long id;

    @Indexed(unique = true)
    private String word;

    @RelatedTo(type = "IS_FOLLOWED_BY")
    @Fetch
    private Set<WordEntity> followedAfterWords;

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

    public Set<WordEntity> getFollowedWords() {
        return followedAfterWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordEntity that = (WordEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WordEntity{" +
                "id=" + id +
                ", word='" + word + '\'' +
                '}';
    }
}
