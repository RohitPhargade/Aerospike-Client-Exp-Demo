package com.example.entity;

import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;

import java.io.Serializable;

@AerospikeRecord(namespace = "test", set = "employees")
public class Employee {
    @AerospikeKey
    private int eid;
    private String name;
    private String email;

    public Employee(int eid, String name, String email) {
        this.eid = eid;
        this.name = name;
        this.email = email;
    }

    public Employee() {
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}