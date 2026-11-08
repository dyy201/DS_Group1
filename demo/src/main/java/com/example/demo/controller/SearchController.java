package com.example.demo.controller;

import com.example.demo.service.TavilySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private TavilySearchService tavilySearchService;

    @GetMapping("/api/search")
    public Map<String, String> search(@RequestParam("query") String query) {
        return tavilySearchService.search(query);
    }
}