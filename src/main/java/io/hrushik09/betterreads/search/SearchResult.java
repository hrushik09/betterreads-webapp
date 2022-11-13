package io.hrushik09.betterreads.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents the structure of the search result returned by the Open Library API.
 */
@Getter
@Setter
public class SearchResult {
    private int numFound;
    private List<SearchResultBook> docs;
}
