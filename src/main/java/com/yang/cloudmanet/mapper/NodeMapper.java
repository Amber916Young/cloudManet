package com.yang.cloudmanet.mapper;

import com.yang.cloudmanet.entity.Global_Pool;
import com.yang.cloudmanet.entity.NodeInfo;
import com.yang.cloudmanet.utils.Page;

import java.util.HashMap;
import java.util.List;

public interface NodeMapper {
    void registerNode(HashMap<String, Object> param);

    NodeInfo loginNode(HashMap<String, Object> param);

    void updateNodeInfo(HashMap<String, Object> param);


    List<HashMap> pageQueryNodeInfoData(Page page);

    int pageQueryNodeInfoCount(Page page);

    void deleteNodeInfosByid(int id);


    NodeInfo queryNodeInfo(HashMap<String, Object> param);

    Global_Pool queryGlobal_pool();

    void updateGlobal_pool(Global_Pool global_pool);

    HashMap<String, Object> queryViewMANETNode(HashMap<String, Object> param);

    HashMap<String, Object> queryViewGlobal_node(HashMap<String, Object> param);

    void insertBradcast(HashMap<String, Object> param);

    int pageQueryBroadcastCount(Page page);

    List<HashMap> pageQueryBroadcastData(Page page);
}
