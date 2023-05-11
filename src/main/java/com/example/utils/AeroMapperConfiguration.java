package com.example.utils;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.mapper.tools.AeroMapper;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AeroMapperConfiguration {


    AerospikeClient aerospikeClient = null;

    AeroMapper mapper = null;

    @PostConstruct
    public void aerospikeClient() {
        ClientPolicy policy = new ClientPolicy();

        policy.writePolicyDefault.durableDelete = true;
        policy.writePolicyDefault.sendKey = true;

        this.aerospikeClient = new AerospikeClient(policy, "localhost", 3000);
        mapper = new AeroMapper.Builder(aerospikeClient).build();
    }

    public AeroMapper getMapper() {
        return this.mapper;
    }

    public AerospikeClient getClient() {
        return this.aerospikeClient;
    }
}