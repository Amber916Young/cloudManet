package com.yang.cloudmanet.service;

import com.yang.cloudmanet.entity.DHCP_Pool;
import com.yang.cloudmanet.entity.MANETs;
import com.yang.cloudmanet.mapper.MANETMapper;
import com.yang.cloudmanet.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MANETService {
    @Autowired
    MANETMapper manetMapper;

    public void registerMANET(HashMap<String, Object> param) {
        manetMapper.registerMANET(param);
    }

    public List<HashMap> pageQueryMANETsData(Page page) {
       return manetMapper.pageQueryMANETsData(page);
    }

    public int pageQueryMANETsCount(Page page) {
        return manetMapper.pageQueryMANETsCount(page);
    }

    public void deleteMANETsByid(String MANET_SSID) {
        manetMapper.deleteMANETsByid(MANET_SSID);
    }

    public MANETs queryMANETs(HashMap<String, Object> param) {
        return manetMapper.queryMANETs(param);
    }

    public void updateMANET(HashMap<String, Object> param) {
        manetMapper.updateMANET(param);
    }



    public void insertDHCP_pool(HashMap<String, Object> param) {
        manetMapper.insertDHCP_pool(param);
    }

    public DHCP_Pool queryDHCPIP(HashMap<String, Object> param) {
        return manetMapper.queryDHCPIP(param);
    }

    public void updateDHCPIP(HashMap<String, Object> param) {
        manetMapper.updateDHCPIP(param);
    }

    public void updateDHCPIPByMANET(HashMap<String, Object> param) {
        manetMapper.updateDHCPIPByMANET(param);

    }


    public List<Integer> queryDHCPIPByCapacity(int capacity) {
        return manetMapper.queryDHCPIPByCapacity(capacity);

    }

    public void updateDHCPIP2(HashMap<String, Object> param) {
        manetMapper.updateDHCPIP2(param);
    }

    public List<DHCP_Pool> queryAllDHCPIP(HashMap<String, Object> param) {
        return manetMapper.queryAllDHCPIP(param);

    }

    public void insertGlobalDHCP_pool(HashMap<String, Object> param) {
        manetMapper.insertGlobalDHCP_pool(param);
    }

    public List<HashMap<String, Object>> queryMembership(HashMap<String, Object> param) {
        return manetMapper.queryMembership(param);
    }

    public List<MANETs> queryAllMAENTs() {
        return manetMapper.queryAllMAENTs();
    }

    public List<HashMap<String, Object>> queryView_node_MANET() {
        return manetMapper.queryView_node_MANET();
    }

    public int pageQueryDHCP_PoolCount(Page page) {
        return manetMapper.pageQueryDHCP_PoolCount(page);
    }

    public List<HashMap> pageQueryDHCP_PoolData(Page page) {
        return manetMapper.pageQueryDHCP_PoolData(page);
    }
    public int pageQueryGlobal_IP_PoolCount(Page page) {
        return manetMapper.pageQueryGlobal_IP_PoolCount(page);
    }

    public List<HashMap> pageQueryGlobal_IP_PoolData(Page page) {
        return manetMapper.pageQueryGlobal_IP_PoolData(page);
    }

    public void updateNumberMANET(HashMap<String, Object> param) {
        manetMapper.updateNumberMANET(param);
    }


    public void updateGlobal_IP(HashMap<String, Object> param) {
        manetMapper.updateGlobal_IP(param);
    }

    public List<HashMap> pageQueryMembershipData(Page page) {
        return manetMapper.pageQueryMembershipData(page);
    }

    public int pageQueryMembershipCount(Page page) {
        return manetMapper.pageQueryMembershipCount(page);
    }

    public DHCP_Pool queryDHCPIP2(HashMap temp) {
        return manetMapper.queryDHCPIP2(temp);
    }

    public List<DHCP_Pool> queryAllDHCPIPByMANETs(HashMap<String, Object> map) {
        return manetMapper.queryAllDHCPIPByMANETs(map);
    }
}
