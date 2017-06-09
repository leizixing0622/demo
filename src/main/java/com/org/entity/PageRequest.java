package com.org.entity;

import java.io.Serializable;

public class PageRequest implements Serializable {

    private int pageNumber;
    private int eachPageCount;
    private String sortField;
    private String searchContent;
    private int startIndex;

    public PageRequest(){

    }

    public PageRequest(int pageNumber, int eachPageCount) {
        this(pageNumber, eachPageCount, null, null);
    }

    public PageRequest(int pageNumber, int eachPageCount, String sortField, String searchContent) {
        this.pageNumber = pageNumber;
        this.eachPageCount = eachPageCount;
        this.sortField = sortField;
        this.searchContent = searchContent;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getEachPageCount() {
        return eachPageCount;
    }

    public void setEachPageCount(int eachPageCount) {
        this.eachPageCount = eachPageCount;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public int getStartIndex() {
        if(this.eachPageCount <= 0) {
            throw new IllegalArgumentException("eachPageCount must greater than zero");
        }else{
            return (this.getPageNumber() - 1) * eachPageCount;
        }
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageNumber=" + pageNumber +
                ", eachPageCount=" + eachPageCount +
                ", sortField='" + sortField + '\'' +
                ", searchContent='" + searchContent + '\'' +
                ", startIndex=" + startIndex +
                '}';
    }
}
