package com.example.utils_using_switch;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.example.utils_using_switch.ExpBuilder.*;

@Data
public class Exp {
    private List<Exp> exps;
    private String cmd;
    private String name;
    private String type;
    private Object val;

    // ------------------------------------------------------


    // Operations with two Exp arguments
    static List<String> list1 = List.of("EQ", "NE", "GT", "GE", "LT", "LE", "POW", "LOG", "MOD", "INT_LSHIFT", "INT_RSHIFT", "INT_ARSHIFT", "INT_LSCAN", "INT_RSCAN");

    // Operations with single Exp arguments
    static List<String> list2 = List.of("NOT", "ABS", "FLOOR", "CEIL", "TO_INT", "TO_FLOAT", "INT_NOT", "INT_COUNT");

    // Operations with Variable Number of Exp arguments (VarArg)
    static List<String> list3 = List.of("AND", "OR", "EXCLUSIVE", "ADD", "SUB", "MUL", "DIV", "INT_AND", "INT_OR", "INT_XOR", "MIN", "MAX", "COND");


    public com.aerospike.client.exp.Exp toJavaCode() {
        if (cmd == null) {
            if (type != null) {
                return getBinTypeExp(name, type);
            } else if (val != null) {
                return getValueFromObject(val);
            }
            throw new RuntimeException("Invalid Expression");
        }
        if (list1.contains(cmd)) {
            return getOperationExp(cmd, exps.get(0).toJavaCode(), exps.get(1).toJavaCode());
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
            throw new UnsupportedOperationException("Operation is not valid, Operation : " + cmd);
        }
    }
}
