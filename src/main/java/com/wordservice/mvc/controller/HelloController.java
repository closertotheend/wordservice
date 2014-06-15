package com.wordservice.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HelloController {

    @Autowired
    private Neo4jTemplate neo4jTemplatel;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        return "hello";
    }

    @RequestMapping(value = "shutdb", method = RequestMethod.GET)
    @ResponseBody
    public void shutdb() {
        neo4jTemplatel.getGraphDatabaseService().shutdown();
    }
}