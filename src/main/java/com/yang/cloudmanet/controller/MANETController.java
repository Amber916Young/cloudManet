package com.yang.cloudmanet.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yang.cloudmanet.entity.DHCP_Pool;
import com.yang.cloudmanet.entity.MANETs;
import com.yang.cloudmanet.entity.NodeInfo;
import com.yang.cloudmanet.service.MANETService;
import com.yang.cloudmanet.service.NodeService;
import com.yang.cloudmanet.utils.AJAXReturn;
import com.yang.cloudmanet.utils.OrderNumberFactory;
import com.yang.cloudmanet.utils.Page;
import com.yang.cloudmanet.utils.TimeParse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/MANET")
@RestController
public class MANETController {
    @Autowired
    MANETService manetService;
    @Value("${Delcode.key}")
    private String Delcode;
    @Autowired
    NodeService nodeService;

    @SneakyThrows
    @ResponseBody
    @GetMapping("/pool")
    public Object createIP_pool() {

        int times = 0;
        HashMap<String, Object> param = new HashMap<>();
        param.put("used","0");
//        while (times < 10) {
//            for (int i = 1; i <= 30; i++) {
//                String ip = "192.168.";
//                ip = ip+times+"."+i;
//                param.put("ipAddress",ip);
//                manetService.insertDHCP_pool(param);
//            }
//            times++;
//        }
        return  AJAXReturn.success("creating done!");
    }


    @ResponseBody
    @GetMapping("/global/pool")
    public Object createGlobalIP_pool() {
        int times = 20;
        HashMap<String, Object> param = new HashMap<>();
        while (times > 15) {
            for (int i = 1; i <= 254; i++) {
                String ip = "192.168.";
                ip = ip+times+"."+i;
                param.put("ipAddress",ip);
//                manetService.insertGlobalDHCP_pool(param);
            }
            times--;
        }
        return  AJAXReturn.success("creating done!");
    }


    @SneakyThrows
    @ResponseBody
    @PostMapping("/split")
    public Object MANETSplit(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> param = JSONObject.parseObject(jsonData, HashMap.class);
        TimeParse timeParse = new TimeParse();
        String create_time = timeParse.convertDate2String(new Date(), "yyyy-MM-dd HH:mm:ss");
        param.put("createTime", create_time);
        String newMANET_SSID = OrderNumberFactory.getRandomOrderNumber();
        String oldMANET_SSID = param.get("MANET_SSID").toString();
        MANETs maneTs= manetService.queryMANETs(param);
        int oldNumber = maneTs.getNumber();
        int oldCapacity = maneTs.getCapacity();
        while (oldMANET_SSID.equals(newMANET_SSID)){
            newMANET_SSID = OrderNumberFactory.getRandomOrderNumber();
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("MANET_SSID",oldMANET_SSID);
        List<DHCP_Pool> dhcp_pools = manetService.queryAllDHCPIPByMANETs(map);
        map = new HashMap<>();
        for (DHCP_Pool pool : dhcp_pools){
            int id = pool.getId();
            map.put("id",id);
            map.put("MANET_SSID",newMANET_SSID);
            manetService.updateDHCPIPByMANET(map);
        }
        map = new HashMap<>();
        int newNumber = dhcp_pools.size();
        map.put("createTime",create_time);
        map.put("MANET_SSID",newMANET_SSID);
        map.put("capacity",newNumber);
        map.put("MANET_range",maneTs.getMANET_range());
        map.put("ownerID",dhcp_pools.get(0).getNodeID());

        manetService.registerMANET(map);
        map = new HashMap<>();
        oldNumber = oldNumber - newNumber;
        int capacity = oldCapacity-newNumber;
        map.put("MANET_SSID",oldMANET_SSID);
        map.put("capacity",capacity);
        map.put("number",oldNumber);
        manetService.updateMANET(map);
        return  AJAXReturn.success("Split operation completed");

    }


    @SneakyThrows
    @ResponseBody
    @PostMapping("/merge")
    public Object MANETMerge(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> param = JSONObject.parseObject(jsonData, HashMap.class);
        //MANET_SSID1 MANET_SSID2
        HashMap<String, Object> key = new HashMap<>();
        key.put("MANET_SSID",param.get("MANET_SSID1"));
        MANETs maneTs1= manetService.queryMANETs(key);
        key.put("MANET_SSID",param.get("MANET_SSID2"));
        MANETs maneTs2 = manetService.queryMANETs(key);
        manetService.deleteMANETsByid(maneTs2.getMANET_SSID());
        int newNumber = maneTs1.getNumber()+maneTs2.getNumber();
        int newCapacity = maneTs1.getCapacity()+maneTs2.getCapacity();
        key.put("MANET_SSID",param.get("MANET_SSID1"));
        key.put("number",newNumber);
        key.put("capacity",newCapacity);
        manetService.updateMANET(key);
        manetService.updateDHCPIP2(param);
        return  AJAXReturn.success("Merge operation completed");
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/create")
    public Object MANETCreate(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=","");
        HashMap<String, Object> param = JSONObject.parseObject(jsonData, HashMap.class);
        TimeParse timeParse = new TimeParse();
        HashMap<String, Object> map = new HashMap();
        map.put("id", param.get("nodeID"));
        map.put("node_role",NodeInfo.Role.OWNER);

        NodeInfo nodeInfo = nodeService.queryNodeInfo(map);
        if(nodeInfo!=null)  return AJAXReturn.warn("This device has already created MANET");
        int capacity = Integer.parseInt(param.get("capacity").toString());
        String create_time = timeParse.convertDate2String(new Date(),"yyyy-MM-dd HH:mm:ss");
        param.put("createTime",create_time);
        param.put("MANET_SSID", OrderNumberFactory.getRandomOrderNumber());
        param.put("ownerID", param.get("nodeID"));

        manetService.registerMANET(param);
        map = new HashMap();
        map.put("node_role", NodeInfo.Role.OWNER);
        map.put("id", param.get("nodeID"));
        nodeService.updateNodeInfo(map);
        List<Integer> ids = manetService.queryDHCPIPByCapacity(capacity);
        int loop = 0;
        for(int id : ids){
            map = new HashMap();
            map.put("id",id);
            map.put("MANET_SSID", param.get("MANET_SSID"));
            map.put("nodeID",  param.get("nodeID"));
            map.put("used", '1');
            if(loop>0){
                map.put("nodeID", null);
                map.put("used", '0');
            }
            manetService.updateDHCPIPByMANET(map);
            loop++;
        }
        return  AJAXReturn.success("MANET creates successfully");
    }

    @SneakyThrows
    @ResponseBody
    @GetMapping("/IP/queryAll")
    public Object IPQueryAll(Page page, @RequestParam("limit") int limit, HttpServletRequest request) {
        page.setRows(limit);
        List<HashMap> lists = manetService.pageQueryDHCP_PoolData(page);
        int totals=manetService.pageQueryDHCP_PoolCount(page);
        return  AJAXReturn.success("successfully",lists,totals);
    }
    @SneakyThrows
    @ResponseBody
    @GetMapping("/Global_IP/queryAll")
    public Object Global_IPQueryAll(Page page, @RequestParam("limit") int limit, HttpServletRequest request) {
        page.setRows(limit);
        List<HashMap> lists = manetService.pageQueryGlobal_IP_PoolData(page);
        int totals=manetService.pageQueryGlobal_IP_PoolCount(page);
        return  AJAXReturn.success("successfully",lists,totals);
    }

    @SneakyThrows
    @ResponseBody
    @GetMapping("/queryAll")
    public Object MANETQueryAll(Page page, @RequestParam("limit") int limit, HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        page.setRows(limit);
        page.setKeyWord(keyword);
        List<HashMap> lists = manetService.pageQueryMANETsData(page);
        int totals=manetService.pageQueryMANETsCount(page);
        return  AJAXReturn.success("successfully",lists,totals);
    }


    @ResponseBody
    @PostMapping(value = "/deletes")
    public Object MANETDeletes(@RequestBody String jsonData)  throws UnsupportedEncodingException {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=","");
        JSONObject json = JSONObject.parseObject(jsonData);
        JSONArray ids = json.getJSONArray("ids");
        String code = json.getString("code");
        if (code.equals(Delcode)) {
            for (int i = 0; i < ids.size(); i++) {
                String MANET_SSID  =  ids.get(i).toString();
                manetService.deleteMANETsByid(MANET_SSID);
            }
            return AJAXReturn.success("successfully");
        } else return AJAXReturn.error("Code is wrong，delete fail!");
    }
    @ResponseBody
    @GetMapping(value = "/membership")
    public Object MANETMembership(Page page, @RequestParam("limit") int limit, HttpServletRequest request)  {
        String keyword = request.getParameter("keyword");
        page.setRows(limit);
        page.setKeyWord(keyword);
        List<HashMap> lists = manetService.pageQueryMembershipData(page);
        int totals=manetService.pageQueryMembershipCount(page);
        return AJAXReturn.success("MANET's membership query successfully" ,lists,totals);
    }
}
