package com.org.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SortInfo implements Serializable {

    private String sortFieldName;
    private String sortOrder;

    public SortInfo() {

    }

    public SortInfo(String sortFieldName, String sortOrder) {
        this.sortFieldName = sortFieldName;
        this.sortOrder = sortOrder;
    }

    public String getSortFieldName() {
        return sortFieldName;
    }

    public void setSortFieldName(String sortFieldName) {
        this.sortFieldName = sortFieldName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    //最关键的方法：格式化排序列的数据
    public List<SortInfo> initSortFieldName(String sortFieldName) {
        if(sortFieldName == null) {
            return new ArrayList<SortInfo>(0);
        }else{
            List<SortInfo> sortInfos = new ArrayList<SortInfo>();
            String[] strings = sortFieldName.split(",");
            for(int i = 0; i < strings.length; i ++) {
                String[] strings1 = strings[i].split("//s+");
                SortInfo sortInfo = new SortInfo();
                sortInfo.setSortFieldName(strings1[0]);
                sortInfo.setSortOrder(strings1.length == 2 ? strings1[1] : null);
                sortInfos.add(sortInfo);
            }
            return sortInfos;
        }
    }
}
