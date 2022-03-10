package com.yang.cloudmanet.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class MANETs {
    private String MANET_SSID;
    private int ownerID;
    private List<HashMap<String, Object>> nodeInfos;
    private String createTime;
    private int capacity;
    private int MANET_range;
    private int number;
}
