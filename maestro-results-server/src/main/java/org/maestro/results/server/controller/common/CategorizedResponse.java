package org.maestro.results.server.controller.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CategorizedResponse<T> implements Response {
    @JsonProperty("Categories")
    Set<String> categories = new TreeSet<>();

    @JsonProperty("Pairs")
    List<T> pairs = new LinkedList<>();

    public Set<String> getCategories() {
        return categories;
    }

    public List<T> getPairs() {
        return pairs;
    }

    public static String categoryName(int testId, int testNumber, String name) {
        return String.format("%d/%d %s", testId, testNumber, name);
    }
}
