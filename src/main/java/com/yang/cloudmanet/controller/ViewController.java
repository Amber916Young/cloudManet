package com.yang.cloudmanet.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yang.cloudmanet.entity.NodeInfo;
import com.yang.cloudmanet.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class ViewController {
    @Autowired
    NodeService nodeService;
    @GetMapping("/")
    public Object DashBoard(){
        return "index";
    }
    @GetMapping("/index/lab2")
    public Object IndexNode(){
        return "lab2_index";
    }
    @GetMapping("/DB/MANET")
    public Object DBMANET(){
        return "MANET_index";
    }

    @GetMapping("/listform/id/{id}")
    public Object listform(@PathVariable("id") int id , Model model){
        HashMap<String, Object> param = new HashMap<>();
        param.put("id",id);
        NodeInfo nodeInfo = nodeService.queryNodeInfo(param);
        model.addAttribute("nodeInfo",nodeInfo);
        return "listform";
    }
}
