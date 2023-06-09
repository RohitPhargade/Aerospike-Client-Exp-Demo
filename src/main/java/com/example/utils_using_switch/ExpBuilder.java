package com.example.utils_using_switch;

import com.aerospike.client.exp.Exp;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ExpBuilder {

    //comment
    public static Exp getBinTypeExp(String name, String type) {
        if (type == null || type.isEmpty() || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Type parameter cannot be null or empty");
        }
        return switch(type.toUpperCase()){
            case "INT" -> Exp.intBin(name);
            case "FLOAT" -> Exp.floatBin(name);
            case "STRING" -> Exp.stringBin(name);
            case "BOOL" -> Exp.boolBin(name);
            case "BLOB" -> Exp.blobBin(name);
            case "GEO" -> Exp.geoBin(name);
            case "LIST" -> Exp.listBin(name);
            case "MAP" -> Exp.mapBin(name);
            case "HLL" -> Exp.hllBin(name);
            default -> {
                try {//comment
                    yield Exp.bin(name, Exp.Type.valueOf(type));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Type parameter should be valid");
                }
            }

        };
    }

    public static Exp getValueFromObject(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Value parameter cannot be null or empty");
        }
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
        if (operation == null || firstValue == null || secondValue == null) {
            throw new IllegalArgumentException("Operation or any parameter should not be null");
        }
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
        if (operation == null || value == null) {
            throw new IllegalArgumentException("Operation or any parameter should not be null");
        }
        return switch(operation.toUpperCase()){
            case "NOT" -> Exp.not(value);
            case "ABS" -> Exp.abs(value);
            case "FLOOR" -> Exp.floor(value);
            case "CEIL" -> Exp.ceil(value);
            case "TO_INT" -> Exp.toInt(value);
            case "TO_FLOAT" -> Exp.toFloat(value);
            case "INT_NOT" -> Exp.intNot(value);
            case "INT_COUNT" -> Exp.count(value);
            default -> throw new IllegalArgumentException("Operation should be valid");
        };
    }

    public static Exp getOperationExpByExp(String operation, Exp... value) {
        if (operation == null || value == null || value.length <1) {
            throw new IllegalArgumentException("Operation or any parameter should not be null");
        }
        return switch(operation.toUpperCase()){
            case "AND" -> Exp.and(value);
            case "OR" -> Exp.or(value);
            case "EXCLUSIVE" -> Exp.exclusive(value);
            case "ADD" -> Exp.add(value);
            case "SUB" -> Exp.sub(value);
            case "MUL" -> Exp.mul(value);
            case "DIV" -> Exp.div(value);
            case "INT_AND" -> Exp.intAnd(value);
            case "INT_OR" -> Exp.intOr(value);
            case "INT_XOR" -> Exp.intXor(value);
            case "MIN" -> Exp.min(value);
            case "MAX" -> Exp.max(value);
            case "COND" -> Exp.cond(value);
            default -> throw new IllegalArgumentException("Operation should be valid");

        };
    }

}
