package com.wordservice.mvc.model;

public class NullWordEntity extends WordEntity {

    @Override
    public Long getId() {
        return 0l;
    }
}
