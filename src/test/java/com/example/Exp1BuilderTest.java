package com.example;

import com.aerospike.client.exp.Exp;
import com.example.utils_using_switch.ExpBuilder;
import com.google.gson.Gson;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class Exp1BuilderTest {

    @Inject
    private Gson gson;

    private Exp firstValue;
    private Exp secondValue;

    @BeforeEach
    void setup() {
        firstValue = Exp.intBin("Bin1");
        secondValue = Exp.intBin("Bin2");
    }

    @Test
    void getBinTypeExp_ValidType_ReturnsExp() {
        String binName = "Bin1";
        Exp actualResult = ExpBuilder.getBinTypeExp(binName, "INT");
        Exp expectedResult = Exp.intBin(binName);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    }

    @Test
    void getBinTypeExp_InvalidType_ThrowsException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp("binName", "INVALID")),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp("", "INT")),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp(null, "INT")),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp("binName", "")),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp("", null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp(null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp("binName", null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp("", "")),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getBinTypeExp(null, ""))
        );
    }

    @Test
    void getValueFromObject_Boolean_ReturnsExp() {
        long value = 345678900654356L;
        Exp actualResult = ExpBuilder.getValueFromObject(value);
        Exp expectedResult = Exp.val(value);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    }

    @Test
    void getValueFromObject_List_ReturnsExp() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Exp actualResult = ExpBuilder.getValueFromObject(list);
        Exp expectedResult = Exp.val(list);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    }

    @Test
    void getValueFromObject_Map_ReturnsExp() {
        Map<String, String> map = Map.of("key1", "value1", "key2", "value2");
        Exp actualResult = ExpBuilder.getValueFromObject(map);
        Exp expectedResult = Exp.val(map);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    }

    @Test
    void getValueFromObject_Calendar_ReturnsExp() {
        Calendar calendar = Calendar.getInstance();
        Exp actualResult = ExpBuilder.getValueFromObject(calendar);
        Exp expectedResult = Exp.val(calendar);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    }

    @Test
    void getValueFromObject_UnsupportedDataType_ThrowsException() {
        assertAll(
                () -> assertThrows(UnsupportedOperationException.class, () -> ExpBuilder.getValueFromObject(new Object())),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getValueFromObject(null))
        );
    }

    @Test
    void getOperationExp_ValidOperation_ReturnsExp() {
        Exp actualResult = ExpBuilder.getOperationExp("GT", firstValue, secondValue);
        Exp expectedResult = Exp.gt(firstValue, secondValue);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    }

    @Test
    void getOperationExp_InvalidOperation_ThrowsException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExp("", firstValue, secondValue)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExp("INVALID", firstValue, secondValue)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExp("EQ", null, secondValue)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExp("EQ", firstValue, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExp("EQ", null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExp(null, null, null))
        );
    }

    @Test
    void getOperationExpByArray_ValidOperation_ReturnsExp() {
        Exp actualResult = ExpBuilder.getOperationExpByArray("NOT", firstValue);
        Exp expectedResult = Exp.not(firstValue);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    }

    @Test
    void getOperationExpByArray_InvalidOperation_ThrowsException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByArray("ABS", null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByArray(null, firstValue)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByArray(null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByArray("INVALID", firstValue))
        );
    }

    @Test
    void getOperationExpByExp_ValidOperation_ReturnsExp() {
        Exp actualResult = ExpBuilder.getOperationExpByExp("AND", firstValue, secondValue);
        Exp expectedResult = Exp.and(firstValue, secondValue);

        assertNotNull(actualResult);
        assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    }

    @Test
    void getOperationExpByExp_InvalidOperation_ThrowsException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByExp("INVALID", firstValue, secondValue)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByExp("AND")),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByExp("AND", null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByExp(null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ExpBuilder.getOperationExpByExp(null))
        );
    }
}
