package com.example.javathehutair;

import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    @Test
    void creditCheckAllCorrectInfo() {
        String firstName = "John";
        String middleName = "Patrick";
        String lastName = "Doe";
        long creditNumber = Long.parseLong("1234567890123456");
        long cvc = Long.parseLong("123");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expDate = YearMonth.parse("12/28", formatter);

        var checkout = new Checkout();

        assertTrue(checkout.creditCheck(firstName, middleName, lastName, creditNumber, cvc, expDate));
    }

    @Test
    void creditCheckNoMiddleName() {
        String firstName = "John";
        String middleName = " ";
        String lastName = "Doe";
        long creditNumber = Long.parseLong("1234567890123456");
        long cvc = Long.parseLong("123");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expDate = YearMonth.parse("12/28", formatter);

        var checkout = new Checkout();

        assertTrue(checkout.creditCheck(firstName, middleName, lastName, creditNumber, cvc, expDate));
    }

    @Test
    void creditCheckCreditNumNot16() {
        String firstName = "John";
        String middleName = "Patrick";
        String lastName = "Doe";
        long creditNumber = Long.parseLong("123456789012345");
        long cvc = Long.parseLong("123");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expDate = YearMonth.parse("12/28", formatter);

        var checkout = new Checkout();

        assertFalse(checkout.creditCheck(firstName, middleName, lastName, creditNumber, cvc, expDate));
    }

    @Test
    void creditCheckCVCNot3() {
        String firstName = "John";
        String middleName = "Patrick";
        String lastName = "Doe";
        long creditNumber = Long.parseLong("123456789012345");
        long cvc = Long.parseLong("1234");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expDate = YearMonth.parse("12/28", formatter);

        var checkout = new Checkout();

        assertFalse(checkout.creditCheck(firstName, middleName, lastName, creditNumber, cvc, expDate));
    }

    @Test
    void creditCheckExpDateWrongFormat() {
        String firstName = "John";
        String middleName = "Patrick";
        String lastName = "Doe";
        long creditNumber = Long.parseLong("123456789012345");
        long cvc = Long.parseLong("1234");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth expDate = YearMonth.parse("1228", formatter);

        var checkout = new Checkout();

        assertFalse(checkout.creditCheck(firstName, middleName, lastName, creditNumber, cvc, expDate));
    }


}