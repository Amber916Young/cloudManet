package com.yang.cloudmanet.service;

import com.yang.cloudmanet.entity.Global_Pool;
import com.yang.cloudmanet.entity.NodeInfo;
import com.yang.cloudmanet.mapper.NodeMapper;
import com.yang.cloudmanet.mapper.PermissionMapper;
import com.yang.cloudmanet.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class NodeService {
    @Autowired
    NodeMapper nodeMapper;

    public void registerNode(HashMap<String, Object> param) {
        nodeMapper.registerNode(param);
    }

    public NodeInfo loginNode(HashMap<String, Object> param) {
        return nodeMapper.loginNode(param);
    }

    public void updateNodeInfo(HashMap<String, Object> param) {
        nodeMapper.updateNodeInfo(param);
    }


    public List<HashMap> pageQueryNodeInfoData(Page page) {
        return nodeMapper.pageQueryNodeInfoData(page);
    }

    public int pageQueryNodeInfoCount(Page page) {
        return nodeMapper.pageQueryNodeInfoCount(page);
    }

    public void deleteNodeInfosByid(int id) {
        nodeMapper.deleteNodeInfosByid(id);
    }


    public NodeInfo queryNodeInfo(HashMap<String, Object> param) {
        return nodeMapper.queryNodeInfo(param);
    }

    public Global_Pool queryGlobal_pool() {
        return nodeMapper.queryGlobal_pool();
    }

    public void updateGlobal_pool(Global_Pool global_pool) {
        nodeMapper.updateGlobal_pool(global_pool);
    }

    public HashMap<String, Object> queryViewMANETNode(HashMap<String, Object> param) {
        return nodeMapper.queryViewMANETNode(param);
    }

    public HashMap<String, Object> queryViewGlobal_node(HashMap<String, Object> param) {
        return nodeMapper.queryViewGlobal_node(param);
    }


    public void insertBradcast(HashMap<String, Object> param) {
        nodeMapper.insertBradcast(param);
    }

    public List<HashMap> pageQueryBroadcastData(Page page) {
        return nodeMapper.pageQueryBroadcastData(page);
    }

    public int pageQueryBroadcastCount(Page page) {
        return nodeMapper.pageQueryBroadcastCount(page);
    }
}
