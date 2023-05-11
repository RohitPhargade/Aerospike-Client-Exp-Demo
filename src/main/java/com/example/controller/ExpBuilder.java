package com.example.controller;

import com.aerospike.client.exp.Exp;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ExpBuilder {


    public static Exp getBinTypeExp(String name, String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type parameter cannot be null or empty");
        }
        switch(type.toUpperCase()){
            case "INT" -> {
                return Exp.intBin(name);
            }
            case "FLOAT" -> {
                return Exp.floatBin(name);
            }
            case "STRING" -> {
                return Exp.stringBin(name);
            }
            case "BOOL" -> {
                return Exp.boolBin(name);
            }
            case "BLOB" -> {
                return Exp.blobBin(name);
            }
            case "GEO" -> {
                return Exp.geoBin(name);
            }
            case "LIST" -> {
                return Exp.listBin(name);
            }
            case "MAP" -> {
                return Exp.mapBin(name);
            }
            case "HLL" -> {
                return Exp.hllBin(name);
            }
            default -> {
                try {
                    Exp.bin(name, Exp.Type.valueOf(type));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Type parameter should be valid");
                }
            }
        }
        return null;
    }

    public static Exp getValueFromObject(Object object) {
        Class<?> aClass = object.getClass();
        String className = aClass.getSimpleName();
        return switch(className){
            case "Boolean" -> Exp.val((boolean) object);
            case "Long" -> Exp.val((long) object);
            case "Integer" -> Exp.val((int) object);
            case "Double" -> Exp.val((double) object);
            case "Float" -> Exp.val((float) object);
            case "String" -> Exp.val((String) object);
            case "Character" -> Exp.val(String.valueOf(object));
            case "byte[]" -> Exp.val((byte[]) object);
            default -> {
                if (className.contains("List")) {
                    yield Exp.val((List<?>) object);
                } else if (className.contains("Map")) {
                    yield Exp.val((Map<?, ?>) object);
                } else if (className.contains("Calendar")) {
                    yield Exp.val((Calendar) object);
                } else {
                    throw new UnsupportedOperationException("Datatype Not Found");
                }
            }
        };
    }


    //     Boolean Operator
    public static Exp getOperationExp(String operation, Exp firstValue, Exp secondValue) {

        return switch(operation.toUpperCase()){
            case "EQ" -> Exp.eq(firstValue, secondValue);
            case "NE" -> Exp.ne(firstValue, secondValue);
            case "GT" -> Exp.gt(firstValue, secondValue);
            case "GE" -> Exp.ge(firstValue, secondValue);
            case "LT" -> Exp.lt(firstValue, secondValue);
            case "LE" -> Exp.le(firstValue, secondValue);
            case "POW" -> Exp.pow(firstValue, secondValue);
            case "LOG" -> Exp.log(firstValue, secondValue);
            case "MOD" -> Exp.mod(firstValue, secondValue);
            case "INT_LSHIFT" -> Exp.lshift(firstValue, secondValue);
            case "INT_RSHIFT" -> Exp.rshift(firstValue, secondValue);
            case "INT_ARSHIFT" -> Exp.arshift(firstValue, secondValue);
            case "INT_LSCAN" -> Exp.lscan(firstValue, secondValue);
            case "INT_RSCAN" -> Exp.rscan(firstValue, secondValue);
            default -> throw new IllegalArgumentException("Operation should be valid");
        };
    }

    public static Exp getOperationExpByArray(String operation, Exp value) {

        switch(operation.toUpperCase()){
            case "NOT" -> {
                return Exp.not(value);
            }
            case "ABS" -> {
                return Exp.abs(value);
            }
            case "FLOOR" -> {
                return Exp.floor(value);
            }
            case "CEIL" -> {
                return Exp.ceil(value);
            }
            case "TO_INT" -> {
                return Exp.toInt(value);
            }
            case "TO_FLOAT" -> {
                return Exp.toFloat(value);
            }
            case "INT_NOT" -> {
                return Exp.intNot(value);
            }
            case "INT_COUNT" -> {
                return Exp.count(value);
            }
            default -> {
                throw new IllegalArgumentException("Operation should be valid");
            }
        }
    }

    public static Exp getOperationExpByExp(String operation, Exp... value) {

        switch(operation.toUpperCase()){
            case "AND" -> {
                return Exp.and(value);
            }
            case "OR" -> {
                return Exp.or(value);
            }
            case "EXCLUSIVE" -> {
                return Exp.exclusive(value);
            }
            case "ADD" -> {
                return Exp.add(value);
            }
            case "SUB" -> {
                return Exp.sub(value);
            }
            case "MUL" -> {
                return Exp.mul(value);
            }
            case "DIV" -> {
                return Exp.div(value);
            }
            case "INT_AND" -> {
                return Exp.intAnd(value);
            }
            case "INT_OR" -> {
                return Exp.intOr(value);
            }
            case "INT_XOR" -> {
                return Exp.intXor(value);
            }
            case "MIN" -> {
                return Exp.min(value);
            }
            case "MAX" -> {
                return Exp.max(value);
            }
            case "COND" -> {
                return Exp.cond(value);
            }
            default -> {
                throw new IllegalArgumentException("Operation should be valid");
            }
        }
    }

}
