package com.yang.cloudmanet.mapper;

import com.yang.cloudmanet.entity.DHCP_Pool;
import com.yang.cloudmanet.entity.MANETs;
import com.yang.cloudmanet.utils.Page;

import java.util.HashMap;
import java.util.List;

public interface MANETMapper {
    void registerMANET(HashMap<String, Object> param);

    List<HashMap> pageQueryMANETsData(Page page);

    int pageQueryMANETsCount(Page page);

    void deleteMANETsByid(String MANET_SSID);

    MANETs queryMANETs(HashMap<String, Object> param);

    void updateMANET(HashMap<String, Object> param);



    void insertDHCP_pool(HashMap<String, Object> param);

    DHCP_Pool queryDHCPIP(HashMap<String, Object> param);

    void updateDHCPIP(HashMap<String, Object> param);

    void updateDHCPIPByMANET(HashMap<String, Object> param);



    List<Integer> queryDHCPIPByCapacity(int capacity);

    void updateDHCPIP2(HashMap<String, Object> param);

    List<DHCP_Pool> queryAllDHCPIP(HashMap<String, Object> param);

    void insertGlobalDHCP_pool(HashMap<String, Object> param);

    List<HashMap<String, Object>> queryMembership(HashMap<String, Object> param);

    List<MANETs> queryAllMAENTs();

    List<HashMap<String, Object>> queryView_node_MANET();

    List<HashMap> pageQueryDHCP_PoolData(Page page);

    int pageQueryDHCP_PoolCount(Page page);

    int pageQueryGlobal_IP_PoolCount(Page page);

    List<HashMap> pageQueryGlobal_IP_PoolData(Page page);

    void updateNumberMANET(HashMap<String, Object> param);

    void updateGlobal_IP(HashMap<String, Object> param);

    List<HashMap> pageQueryMembershipData(Page page);

    int pageQueryMembershipCount(Page page);

    DHCP_Pool queryDHCPIP2(HashMap temp);

    List<DHCP_Pool> queryAllDHCPIPByMANETs(HashMap<String, Object> map);
}
