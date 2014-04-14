package com.wordservice.mvc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ilja on 4/14/2014.
 */
@Service
public class WordRepositoryCached {
    @Autowired
    WordRepository wordRepository;


}
