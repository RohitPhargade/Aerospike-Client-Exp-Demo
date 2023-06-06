package com.example.utils_using_map;

import com.aerospike.client.exp.Exp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ExpBuilder {
    private static final Map<String, Function<String, Exp>> binTypeExpMap = createBinTypeExpMap();
    private static final Map<Class<?>, Function<Object, Exp>> valueFromObjectMap = createValueFromObjectMap();
    private static final Map<String, BiFunction<Exp, Exp, Exp>> operationExpMap = createOperationExpMap2();
    private static final Map<String, Function<Exp, Exp>> operationExpByArrayMap = createOperationExpByArrayMap();
    private static final Map<String, Function<Exp[], Exp>> operationExpByExpMap = createOperationExpByExpMap();

    public static Exp getBinTypeExp(String name, String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type parameter cannot be null or empty");
        }
        Function<String, Exp> binTypeExpFunction = binTypeExpMap.get(type.toUpperCase());
        if (binTypeExpFunction == null) {
            throw new IllegalArgumentException("Type parameter should be valid");
        }
        return binTypeExpFunction.apply(name);
    }

    public static Exp getValueFromObject(Object object) {
        Class<?> aClass = object.getClass();
        Function<Object, Exp> valueFromObjectFunction = valueFromObjectMap.get(aClass);
        if (valueFromObjectFunction == null) {
            throw new UnsupportedOperationException("Datatype Not Found");
        }
        return valueFromObjectFunction.apply(object);
    }

    public static Exp getOperationExp(String operation, Exp firstValue, Exp secondValue) {
        BiFunction<Exp, Exp, Exp> operationExpFunction = operationExpMap.get(operation.toUpperCase());
        if (operationExpFunction == null) {
            throw new IllegalArgumentException("Operation should be valid");
        }
        return operationExpFunction.apply(firstValue, secondValue);
    }

    public static Exp getOperationExpByArray(String operation, Exp value) {
        Function<Exp, Exp> operationExpByArrayFunction = operationExpByArrayMap.get(operation.toUpperCase());
        if (operationExpByArrayFunction == null) {
            throw new IllegalArgumentException("Operation should be valid");
        }
        return operationExpByArrayFunction.apply(value);
    }

    public static Exp getOperationExpByExp(String operation, Exp... values) {
        Function<Exp[], Exp> operationExpByExpFunction = operationExpByExpMap.get(operation.toUpperCase());
        if (operationExpByExpFunction == null) {
            throw new IllegalArgumentException("Operation should be valid");
        }
        return operationExpByExpFunction.apply(values);
    }

    private static Map<String, Function<String, Exp>> createBinTypeExpMap() {
        Map<String, Function<String, Exp>> map = new HashMap<>();
        map.put("INT", Exp::intBin);
        map.put("FLOAT", Exp::floatBin);
        map.put("STRING", Exp::stringBin);
        map.put("BOOL", Exp::boolBin);
        map.put("BLOB", Exp::blobBin);
        map.put("GEO", Exp::geoBin);
        map.put("LIST", Exp::listBin);
        map.put("MAP", Exp::mapBin);
        map.put("HLL", Exp::hllBin);
        return map;
    }

    private static Map<Class<?>, Function<Object, Exp>> createValueFromObjectMap() {
        Map<Class<?>, Function<Object, Exp>> map = new HashMap<>();
        map.put(Boolean.class, value -> Exp.val((boolean) value));
        map.put(Long.class, value -> Exp.val((long) value));
        map.put(Integer.class, value -> Exp.val((int) value));
        map.put(Double.class, value -> Exp.val((double) value));
        map.put(Float.class, value -> Exp.val((float) value));
        map.put(String.class, value -> Exp.val((String) value));
        map.put(Character.class, value -> Exp.val(String.valueOf(value)));
        map.put(byte[].class, value -> Exp.val((byte[]) value));
        map.put(List.class, value -> Exp.val((List<?>) value));
        map.put(Map.class, value -> Exp.val((Map<?, ?>) value));
        map.put(Calendar.class, value -> Exp.val((Calendar) value));
        return map;
    }

    private static Map<String, BiFunction<Exp, Exp, Exp>> createOperationExpMap2() {
        Map<String, BiFunction<Exp, Exp, Exp>> map = new HashMap<>();
        map.put("EQ", Exp::eq);
        map.put("NE", Exp::ne);
        map.put("GT", Exp::gt);
        map.put("GE", Exp::ge);
        map.put("LT", Exp::lt);
        map.put("LE", Exp::le);
        map.put("POW", Exp::pow);
        map.put("LOG", Exp::log);
        map.put("MOD", Exp::mod);
        map.put("INT_LSHIFT", Exp::lshift);
        map.put("INT_RSHIFT", Exp::rshift);
        map.put("INT_ARSHIFT", Exp::arshift);
        map.put("INT_LSCAN", Exp::lscan);
        map.put("INT_RSCAN", Exp::rscan);
        return map;
    }


    private static Map<String, Function<Exp, Exp>> createOperationExpByArrayMap() {
        Map<String, Function<Exp, Exp>> map = new HashMap<>();
        map.put("NOT", Exp::not);
        map.put("ABS", Exp::abs);
        map.put("FLOOR", Exp::floor);
        map.put("CEIL", Exp::ceil);
        map.put("TO_INT", Exp::toInt);
        map.put("TO_FLOAT", Exp::toFloat);
        map.put("INT_NOT", Exp::intNot);
        map.put("INT_COUNT", Exp::count);
        return map;
    }

    private static Map<String, Function<Exp[], Exp>> createOperationExpByExpMap() {
        Map<String, Function<Exp[], Exp>> map = new HashMap<>();
        map.put("AND", Exp::and);
        map.put("OR", Exp::or);
        map.put("EXCLUSIVE", Exp::exclusive);
        map.put("ADD", Exp::add);
        map.put("SUB", Exp::sub);
        map.put("MUL", Exp::mul);
        map.put("DIV", Exp::div);
        map.put("INT_AND", Exp::intAnd);
        map.put("INT_OR", Exp::intOr);
        map.put("INT_XOR", Exp::intXor);
        map.put("MIN", Exp::min);
        map.put("MAX", Exp::max);
        map.put("COND", Exp::cond);
        return map;
    }
}
