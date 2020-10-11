package com.amaurote.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryDTO {

    private String name;
    private Integer parentId;
    private String categoryStr;
    private String path;
    private String pathPretty;

    public CategoryDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryStr() {
        return categoryStr;
    }

    public void setCategoryStr(String categoryStr) {
        this.categoryStr = categoryStr;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathPretty() {
        return pathPretty;
    }

    public void setPathPretty(String pathPretty) {
        this.pathPretty = pathPretty;
    }
}
