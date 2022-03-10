package com.yang.cloudmanet.service;

import com.yang.cloudmanet.entity.NodeInfo;
import com.yang.cloudmanet.mapper.PermissionMapper;
import com.yang.cloudmanet.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    public void updateNodeInfo(NodeInfo node) {
        permissionMapper.updateNodeInfo(node);
    }

    public NodeInfo authenticate(HashMap param) {
        return permissionMapper.authenticate(param);

    }

    public List<HashMap> pageQueryUserData(Page page) {
        return permissionMapper.pageQueryUserData(page);
    }

    public int pageQueryUserCount(Page page) {
        return permissionMapper.pageQueryUserCount(page);
    }

    public void deleteUsersByid(int id) {
        permissionMapper.deleteUsersByid(id);
    }
}
