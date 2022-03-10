package com.yang.cloudmanet.mapper;


import com.yang.cloudmanet.entity.NodeInfo;
import com.yang.cloudmanet.utils.Page;

import java.util.HashMap;
import java.util.List;

public interface PermissionMapper {
    void updateNodeInfo(NodeInfo node);

    NodeInfo authenticate(HashMap param);

    List<HashMap> pageQueryUserData(Page page);

    int pageQueryUserCount(Page page);

    void deleteUsersByid(int id);
}
