package com.yang.cloudmanet.entity;

import lombok.Data;

@Data
//@Entity
public class DHCP_Pool extends Global_Pool {
    private int id;
    private String MANET_SSID;
    private String used;
}
