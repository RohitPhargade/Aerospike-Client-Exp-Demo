package com.example;

import com.aerospike.client.exp.Exp;
import com.example.utils_using_switch.ExpBuilder;
import com.google.gson.Gson;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@MicronautTest
class ExpTest {

    @Inject
    private Gson gson;

    @Test
    void toJavaCode_BinTypeExp_ReturnsExp() {
        com.example.utils_using_switch.Exp exp = new com.example.utils_using_switch.Exp();
        exp.setName("Bin1");
        exp.setType("INT");

        com.aerospike.client.exp.Exp actualResult = exp.toJavaCode();
        com.aerospike.client.exp.Exp expectedResult = ExpBuilder.getBinTypeExp("Bin1", "INT");

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult),gson.toJson(actualResult));
    }

    @Test
    void toJavaCode_ValueFromObject_ReturnsExp() {
        com.example.utils_using_switch.Exp exp = new com.example.utils_using_switch.Exp();
        exp.setVal(123);

        com.aerospike.client.exp.Exp actualResult = exp.toJavaCode();
        com.aerospike.client.exp.Exp expectedResult = ExpBuilder.getValueFromObject(123);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult),gson.toJson(actualResult));
    }

    @Test
    void toJavaCode_OperationExpWithTwoArgs_ReturnsExp() {
        com.example.utils_using_switch.Exp exp1 = new com.example.utils_using_switch.Exp();
        exp1.setName("Bin1");
        exp1.setType("INT");

        com.example.utils_using_switch.Exp exp2 = new com.example.utils_using_switch.Exp();
        exp2.setName("Bin2");
        exp2.setType("INT");

        com.example.utils_using_switch.Exp exp = new com.example.utils_using_switch.Exp();
        exp.setCmd("EQ");
        exp.setExps(List.of(exp1, exp2));

        com.aerospike.client.exp.Exp actualResult = exp.toJavaCode();
        com.aerospike.client.exp.Exp expectedResult = ExpBuilder.getOperationExp("EQ", ExpBuilder.getBinTypeExp("Bin1", "INT"), ExpBuilder.getBinTypeExp("Bin2", "INT"));

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult),gson.toJson(actualResult));
    }


    @Test
    void toJavaCode_OperationExpWithSingleArg_ReturnsExp() {
        com.example.utils_using_switch.Exp exp1 = new com.example.utils_using_switch.Exp();
        exp1.setName("Bin1");
        exp1.setType("INT");

        com.example.utils_using_switch.Exp exp = new com.example.utils_using_switch.Exp();
        exp.setCmd("NOT");
        exp.setExps(Collections.singletonList(exp1));

        Exp actualExp = ExpBuilder.getOperationExpByArray("NOT", ExpBuilder.getBinTypeExp("Bin1", "INT"));

        assertNotNull(actualExp);
        assertEquals(gson.toJson(exp), gson.toJson(actualExp));
    }

    @Test
    void toJavaCode_OperationExpWithVarArgs_ReturnsExp() {
        com.example.utils_using_switch.Exp exp1 = new com.example.utils_using_switch.Exp();
        exp1.setName("Bin1");
        exp1.setType("INT");

        com.example.utils_using_switch.Exp exp2 = new com.example.utils_using_switch.Exp();
        exp2.setName("Bin2");
        exp2.setType("INT");

        com.example.utils_using_switch.Exp exp3 = new com.example.utils_using_switch.Exp();
        exp3.setName("Bin3");
        exp3.setType("INT");

        com.example.utils_using_switch.Exp exp = new com.example.utils_using_switch.Exp();
        exp.setCmd("AND");
        exp.setExps(Arrays.asList(exp1, exp2, exp3));

        Exp actualExp = ExpBuilder.getOperationExpByExp("AND", ExpBuilder.getBinTypeExp("Bin1", "INT"), ExpBuilder.getBinTypeExp("Bin2", "INT"), ExpBuilder.getBinTypeExp("Bin3", "INT"));

        assertNotNull(actualExp);
        assertEquals(gson.toJson(exp), gson.toJson(actualExp));
    }

}
