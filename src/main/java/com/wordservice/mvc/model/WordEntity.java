package com.wordservice.mvc.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

@NodeEntity
public class WordEntity {

    @JsonIgnore
    @GraphId
    private Long id;

    @Indexed(indexType = IndexType.FULLTEXT, numeric = false,  unique = true, indexName = "word")
    private String word;

    @Indexed(indexName = "popularity")
    private Long popularity = 0l;

    public WordEntity() {
    }

    public WordEntity(String word) {
        this.word = word;
    }

    public void incrementPopularity() {
        this.popularity++;
    }

    // GETTERS SETTERS
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordEntity that = (WordEntity) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WordEntity{" +
                ", word='" + word + '\'' +
                '}';
    }
}
