package com.yang.cloudmanet.service;

import com.yang.cloudmanet.mapper.Lab2Mapper;
import com.yang.cloudmanet.mapper.NodeMapper;
import com.yang.cloudmanet.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class Lab2Service {
    @Autowired
    Lab2Mapper lab2Mapper;

    public void insertResult(HashMap<String, Object> map) {
        lab2Mapper.insertResult(map);
    }

    public List<HashMap> pageQueryData(Page page) {
        return lab2Mapper.pageQueryData(page);
    }

    public int pageQueryCount(Page page) {
        return lab2Mapper.pageQueryCount(page);
    }

    public void deletesByid(String id) {
        lab2Mapper.deletesByid(id);
    }
}
