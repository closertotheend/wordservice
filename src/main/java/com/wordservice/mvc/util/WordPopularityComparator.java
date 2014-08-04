package com.wordservice.mvc.util;


import com.wordservice.mvc.model.WordEntity;

import java.util.Comparator;

public class WordPopularityComparator implements Comparator<WordEntity> {

    @Override
    public int compare(WordEntity wordEntity, WordEntity wordEntity2) {
        return wordEntity2.getPopularity() < wordEntity.getPopularity() ? -1 : wordEntity.getPopularity() == wordEntity2.getPopularity() ? 0 : 1;
    }
}
