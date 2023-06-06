package com.example.controller;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.exp.Expression;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.query.RegexFlag;
import com.example.entity.Employee;
import com.example.config.AeroMapperConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.GregorianCalendar;
import java.util.List;

@Controller("/data")
public class DataController {

    @Inject
    private AeroMapperConfiguration configuration;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    //fetch data using json
    @Post(value = "/read-data-by-json-filter", produces = MediaType.APPLICATION_JSON, consumes = MediaType.TEXT_PLAIN)
    public Object readData(String jsonString) {

        com.example.utils_using_switch.Exp exp = gson.fromJson(jsonString, com.example.utils_using_switch.Exp.class);

        // Convert Java objects to Java code
        Exp finalExpObject = exp.toJavaCode();

        // Automatically Deserialize Exp Object from json data
        System.out.println(gson.toJson(finalExpObject));

        QueryPolicy queryPolicy = new QueryPolicy();
        queryPolicy.filterExp = Exp.build(finalExpObject);
        try {
            return configuration.getMapper().query(queryPolicy, Employee.class, null);
        } catch (
                Exception e) {
            return "Expression build successfully\n" + gson.toJson(finalExpObject);
        }

    }


    @Post("/add-emp")
    @Produces(MediaType.APPLICATION_JSON)
    public String addEmployee(@Body Employee employee) throws JsonProcessingException {
//        configuration.getMapper().save(employee);

        ClientPolicy policy = new ClientPolicy();
        AerospikeClient client = new AerospikeClient(policy, "localhost", 3000);

        // Create a key for the Employee record using the "test" namespace and "employees" set
        Key key = new Key("test", "employees", employee.getEid());

        Bin eid = new Bin("eid", employee.getEid());
        Bin name = new Bin("name", employee.getName());
        Bin email = new Bin("email", employee.getEmail());
        client.put(null, key, eid, name, email);

        // Close the Aerospike client connection
        client.close();
        return "Employee Added Successfully";
    }

    @Post("/get-emp-list")
    public List<Employee> getEmpListByEmail(@QueryValue String email) {
        //expression
        // email == "example@gmail.com"
        QueryPolicy queryPolicy = new QueryPolicy();
        //If secondary index is present on given bin then it will work
        queryPolicy.filterExp = Exp.build(Exp.eq(Exp.stringBin("email"), Exp.val(email)));
        return configuration.getMapper().query(queryPolicy, Employee.class, null);
    }


}
