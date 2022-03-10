package com.yang.cloudmanet.Boradcast;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yang.cloudmanet.entity.DHCP_Pool;
import com.yang.cloudmanet.entity.MANETs;
import com.yang.cloudmanet.service.MANETService;
import com.yang.cloudmanet.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledExecutorService {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10); // 10 为线程数量  // 执行任务
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("Run Schedule：" + new Date());
        }, 1, 3, TimeUnit.SECONDS);             // 1s 后开始执行，每 3s 执行一次
    }
    @Autowired
    MANETService manetService;
    @Autowired
    NodeService nodeService;

//@Scheduled(cron = "0 0/1 * * * ?")
//    @Scheduled(cron = "0/5 * * * * ?")
    public void  boradcast(){
        System.err.println("Execution time of static scheduled task: " + LocalDateTime.now());
//        List<MANETs> list = manetService
        HashMap<String, Object> param = new HashMap<>();
        param.put("used","1");
        List<DHCP_Pool> pools = manetService.queryAllDHCPIP(param);
        for (DHCP_Pool dhcp_pool : pools){
            Integer nodeID = dhcp_pool.getNodeID();
            if (nodeID==null) continue;
            String ip =dhcp_pool.getIpAddress();
            String MANET_SSID = dhcp_pool.getMANET_SSID();
        }



    }
}
