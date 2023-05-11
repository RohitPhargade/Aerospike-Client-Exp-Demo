package com.example.utils;

import com.example.controller.ExpBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.controller.ExpBuilder.*;

public class Exp {
    private List<Exp> exps;
    private String cmd;
    private String name;
    private String type;
    private Object val;

    public List<Exp> getCustomExps() {
        return exps;
    }

    public void setCustomExps(List<Exp> exps) {
        this.exps = exps;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    // ------------------------------------------------------
    // Operations with two Exp arguments
    static List<String> list1 = List.of("EQ", "NE", "GT", "GE", "LT", "LE", "POW", "LOG", "MOD", "INT_LSHIFT", "INT_RSHIFT", "INT_ARSHIFT", "INT_LSCAN", "INT_RSCAN");
    // Operations with single Exp arguments
    static List<String> list2 = List.of("NOT", "ABS", "FLOOR", "CEIL", "TO_INT", "TO_FLOAT", "INT_NOT", "INT_COUNT");
    // Operations with Variable Number of Exp arguments
    static List<String> list3 = List.of("AND", "OR", "EXCLUSIVE", "ADD", "SUB", "MUL", "DIV", "INT_AND", "INT_OR", "INT_XOR", "MIN", "MAX", "COND");


    public com.aerospike.client.exp.Exp toJavaCode() {
        if (list1.contains(cmd)) {
            return getOperationExp(cmd, ExpBuilder.getBinTypeExp(exps.get(0).getName(), exps.get(0).getType()), getValueFromObject(exps.get(1).getVal()));
        } else if (list2.contains(cmd)) {
            return getOperationExpByArray(cmd, exps.get(0).toJavaCode());
        } else if (list3.contains(cmd)) {
            List<com.aerospike.client.exp.Exp> expList = new ArrayList<>();
            for (Exp expObj : exps) {
                com.aerospike.client.exp.Exp exp = expObj.toJavaCode();
                expList.add(exp);
            }
            com.aerospike.client.exp.Exp[] expArray = expList.toArray(new com.aerospike.client.exp.Exp[expList.size()]);
            return getOperationExpByExp(cmd, expArray);
        } else {
            throw new UnsupportedOperationException("Operation is not valid");
        }
    }
}
