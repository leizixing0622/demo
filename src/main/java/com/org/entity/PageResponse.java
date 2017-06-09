package com.org.entity;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PageResponse<T> implements Iterable<T> {

    private PageRequest pageRequest;
    private int totalCount;
    private List<T> result;


    public Iterator<T> iterator() {
        return result == null ? Collections.EMPTY_LIST.iterator() : result.iterator();
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
