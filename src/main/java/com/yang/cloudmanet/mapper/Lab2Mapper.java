package com.yang.cloudmanet.mapper;

import com.yang.cloudmanet.utils.Page;

import java.util.HashMap;
import java.util.List;

public interface Lab2Mapper {
    void insertResult(HashMap<String, Object> map);

    List<HashMap> pageQueryData(Page page);

    int pageQueryCount(Page page);

    void deletesByid(String id);
}
