package com.org.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchInfo implements Serializable {

    private String searchFieldName;
    private String searchContent;

    public SearchInfo() {
    }

    public SearchInfo(String searchFieldName, String searchContent) {
        this.searchFieldName = searchFieldName;
        this.searchContent = searchContent;
    }

    public String getSearchFieldName() {
        return searchFieldName;
    }

    public void setSearchFieldName(String searchFieldName) {
        this.searchFieldName = searchFieldName;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    //最关键的方法：格式化搜索列的数据
    public static List<SearchInfo> initSortFieldName(String searchFieldData) {
        if(searchFieldData == null) {
            return new ArrayList<SearchInfo>(0);
        }else{
            List<SearchInfo> searchInfos = new ArrayList<SearchInfo>();
            String[] strings = searchFieldData.split(",");
            for(int i = 0; i < strings.length; i ++) {
                String[] strings1 = strings[i].split("\\s+");
                SearchInfo searchInfo = new SearchInfo();
                searchInfo.setSearchFieldName(strings1[0]);
                searchInfo.setSearchContent(strings1.length == 2 ? strings1[1] : null);
                searchInfos.add(searchInfo);
            }
            return searchInfos;
        }
    }
}
