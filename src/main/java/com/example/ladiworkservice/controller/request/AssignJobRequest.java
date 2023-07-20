package com.example.ladiworkservice.controller.request;

import java.util.List;

public class AssignJobRequest {
    public List<DataRequest> dataList;

    public AssignJobRequest() {
    }

    public AssignJobRequest(List<DataRequest> dataList) {
        this.dataList = dataList;
    }

    public List<DataRequest> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataRequest> dataList) {
        this.dataList = dataList;
    }


}
