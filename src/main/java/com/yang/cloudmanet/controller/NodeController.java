package com.yang.cloudmanet.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yang.cloudmanet.entity.DHCP_Pool;
import com.yang.cloudmanet.entity.Global_Pool;
import com.yang.cloudmanet.entity.MANETs;
import com.yang.cloudmanet.entity.NodeInfo;
import com.yang.cloudmanet.service.MANETService;
import com.yang.cloudmanet.service.NodeService;
import com.yang.cloudmanet.service.PermissionService;
import com.yang.cloudmanet.utils.AJAXReturn;
import com.yang.cloudmanet.utils.Page;
import com.yang.cloudmanet.utils.RandomID;
import com.yang.cloudmanet.utils.TimeParse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@RequestMapping("/node")
@RestController
public class NodeController {
    @Autowired
    NodeService nodeService;
    @Autowired
    MANETService manetService;
    @Value("${Delcode.key}")
    private String Delcode;



    @SneakyThrows
    @ResponseBody
    @GetMapping("/broadcast/queryAll")
    public Object broadcastQueryAll(Page page, @RequestParam("limit") int limit, HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        page.setRows(limit);
        page.setKeyWord(keyword);
        List<HashMap> lists = nodeService.pageQueryBroadcastData(page);
        int totals=nodeService.pageQueryBroadcastCount(page);
        return  AJAXReturn.success("successfully",lists,totals);
    }
    @SneakyThrows
    @ResponseBody
    @PostMapping("/broadcast")
    public Object NodeBroadcast(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=","");
        JSONObject json = JSONObject.parseObject(jsonData);
        JSONArray ids = json.getJSONArray("nodeIDs");
        JSONArray allNodes = json.getJSONArray("allNodes");
        String MANET_SSID = json.getString("MANET_SSID");
        TimeParse timeParse = new TimeParse();
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < allNodes.size(); i++) set.add((int)allNodes.get(i));
        for (int i = 0; i < ids.size(); i++) {
            HashMap<String,Object> param = new HashMap<>();
            int id =  (int)ids.get(i);
            String create_time = timeParse.convertDate2String(new Date(),"yyyy-MM-dd HH:mm:ss");
            param.put("nodeID",id);
            param.put("response_time",create_time);
            param.put("status","no");
            if(set.contains(id)) param.put("status","yes");
            param.put("MANET_SSID",MANET_SSID);
            nodeService.insertBradcast(param);
        }
        return  AJAXReturn.success("Response successfully");
    }


    @SneakyThrows
    @ResponseBody
    @GetMapping("/manet/queryAll")
    public Object MANET_nodes_query() {
        HashMap<String, Object> res = new HashMap<>();
        //MANET
        List<MANETs> maneTsList = manetService.queryAllMAENTs();
        res.put("maneTsList",maneTsList);
        //MANET_nodes_ip
        List<HashMap<String, Object>> nodesList = manetService.queryView_node_MANET();

        for(MANETs list : maneTsList){
            String MANET_SSID = list.getMANET_SSID();
            List<HashMap<String, Object>> nodes = new ArrayList<>();
            for(int i =0;i<nodesList.size();i++){
                HashMap<String, Object> temp = nodesList.get(i);
                if(MANET_SSID.equals(temp.get("MANET_SSID"))){
                    nodes.add(temp);
                }
            }
            list.setNodeInfos(nodes);
        }
        return  AJAXReturn.success("successfully",maneTsList);
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/register")
    public Object NodeRegister(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=","");
        HashMap<String, Object> param = JSONObject.parseObject(jsonData, HashMap.class);
        TimeParse timeParse = new TimeParse();
        String create_time = timeParse.convertDate2String(new Date(),"yyyy-MM-dd HH:mm:ss");
        param.put("registerTime",create_time);
        param.put("status", NodeInfo.Status.OFFLINE);
        Global_Pool global_pool = nodeService.queryGlobal_pool();
        if(global_pool.getIpAddress().isEmpty() || global_pool.getIpAddress()==null)
            return  AJAXReturn.error("The global IP has been assigned and can no longer be registered");
        int nodeID = RandomID.genIDWorker();
        param.put("id",nodeID);
        nodeService.registerNode(param);
        global_pool.setNodeID(nodeID);
        nodeService.updateGlobal_pool(global_pool);
        return  AJAXReturn.success("register successfully");
    }


    @SneakyThrows
    @ResponseBody
    @RequestMapping(value = "/login", produces = "application/json; charset=utf-8")
    public Object NodeLogin(@RequestBody String jsonData) {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> param = JSONObject.parseObject(jsonData, HashMap.class);
        NodeInfo nodeInfo = nodeService.loginNode(param);
        if (nodeInfo != null) {
            param.put("status", NodeInfo.Status.ACTIVE);
            TimeParse timeParse = new TimeParse();
            String create_time = timeParse.convertDate2String(new Date(),"yyyy-MM-dd HH:mm:ss");
            param.put("loginTime",create_time);
            nodeService.updateNodeInfo(param);
            HashMap<String, Object> res= nodeService.queryViewGlobal_node(param);
            return AJAXReturn.success("Log in successfully", res);


//            nodeInfo = nodeService.loginNode(param);
//            return AJAXReturn.success("Log in successfully", nodeInfo);
        }
        return AJAXReturn.error("The account or password is incorrect!");
    }

    @SneakyThrows
    @ResponseBody
    @PostMapping("/logout/username/{username}")
    public Object NodeLogout(@PathVariable("username") String username) {
//        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=","");
        HashMap<String, Object> param = new HashMap<>();
        param.put("username",username);
//        HashMap<String, Object> param = JSONObject.parseObject(jsonData, HashMap.class);
        param.put("status", NodeInfo.Status.OFFLINE);
        nodeService.updateNodeInfo(param);
        return  AJAXReturn.success("Log out successfully");
    }
    @SneakyThrows
    @ResponseBody
    @GetMapping("/queryAll")
    public Object CPULOGLoad(Page page, @RequestParam("limit") int limit, HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        page.setRows(limit);
        page.setKeyWord(keyword);
        List<HashMap> lists = nodeService.pageQueryNodeInfoData(page);
        for(HashMap map :lists){
            String MANET_SSID = "";
            HashMap temp = new HashMap();
            temp.put("nodeID",map.get("id"));
            DHCP_Pool pool = manetService.queryDHCPIP2(temp);
            if (pool!=null) MANET_SSID = pool.getMANET_SSID();
            map.put("MANET_SSID",MANET_SSID);
        }
        int totals=nodeService.pageQueryNodeInfoCount(page);
        return  AJAXReturn.success("successfully",lists,totals);
    }
    @ResponseBody
    @DeleteMapping(value = "/deletes")
    public Object NodeDeletes(@RequestBody String jsonData)  throws UnsupportedEncodingException {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=","");
        JSONObject json = JSONObject.parseObject(jsonData);
        JSONArray ids = json.getJSONArray("ids");
        String code = json.getString("code");
        if (code.equals(Delcode)) {
            for (int i = 0; i < ids.size(); i++) {
                HashMap<String,Object> param = new HashMap<>();
                int id =  (int)ids.get(i);
                nodeService.deleteNodeInfosByid(id);
                param.put("nodeID",id);
                DHCP_Pool pool = manetService.queryDHCPIP(param);
                if(pool!=null){
                    manetService.updateGlobal_IP(param);
                    param.put("nodeID",null);
                    param.put("ip",pool.getIpAddress());
                    param.put("used","0");
                    manetService.updateDHCPIP(param);
                    param.put("MANET_SSID",pool.getMANET_SSID());
                    manetService.updateNumberMANET(param);
                }
            }
            return AJAXReturn.success("successfully");
        } else return AJAXReturn.error("Code is wrong，delete fail!");
    }
    @ResponseBody
    @PostMapping(value = "/leave")
    public Object userLeaves(@RequestBody String jsonData)  throws UnsupportedEncodingException {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> param = JSONObject.parseObject(jsonData, HashMap.class);
        MANETs maneTs = manetService.queryMANETs(param);
        int num = maneTs.getNumber() -1 < 0 ? 0:maneTs.getNumber();
        param.put("number",num-1);
        manetService.updateMANET(param);

        DHCP_Pool dhcp_pool = manetService.queryDHCPIP(param);
        param.put("used","0");
        param.put("nodeID",null);
        param.put("MANET_SSID",null);
        param.put("id",dhcp_pool.getId());
        manetService.updateDHCPIP(param);
        return AJAXReturn.success("leave successfully!");
    }
    @ResponseBody
    @PostMapping(value = "/join")
    public Object NodeJoin(@RequestBody String jsonData)  throws UnsupportedEncodingException {
        jsonData = URLDecoder.decode(jsonData, "utf-8").replaceAll("=", "");
        HashMap<String, Object> param = JSONObject.parseObject(jsonData, HashMap.class);
        MANETs maneTs = manetService.queryMANETs(param);
        if(maneTs.getNumber()>=maneTs.getCapacity())
            return AJAXReturn.error("The maximum capacity of MANET has been reached");
        param.put("used","0");
        DHCP_Pool dhcp_pool = manetService.queryDHCPIP(param);
        param.put("used","1");
        param.put("id",dhcp_pool.getId());
        if(dhcp_pool==null)
            return AJAXReturn.error("IP has been allocated, cannot join!");
        param.put("id",dhcp_pool.getId());
        manetService.updateDHCPIPByMANET(param);
        param.put("ip",dhcp_pool.getIpAddress());
        int num =  maneTs.getNumber()<= 0 ? 0:maneTs.getNumber();
        param.put("number",num+1);
        manetService.updateMANET(param);
        return AJAXReturn.success("join successfully");
    }

}
