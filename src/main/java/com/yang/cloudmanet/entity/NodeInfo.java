package com.yang.cloudmanet.entity;

import com.yang.cloudmanet.utils.AJAXReturn;
import lombok.Data;

@Data
public class NodeInfo {
    private int id;
    private String avatar;
    private String username;
    private String password;
    //    private String mid;
    private String loginTime;
    private String position;

    //    private String ip;
    public enum Role {
        MEMBER("MEMBER"),
        OWNER("OWNER");
        private final String  value;
        Role(String value) {
            this.value = value;
        }
        public String value() {
            return this.value;
        }

    }

    private String registerTime;
    public Role node_role;
    public Status status;
    public enum Status {
        OFFLINE("OFFLINE"),
        ACTIVE("ACTIVE"),
        BROKEN("BROKEN");
        private final String  value;
        Status(String value) {
            this.value = value;
        }
        public String value() {
            return this.value;
        }
    }

    private String category;

}

