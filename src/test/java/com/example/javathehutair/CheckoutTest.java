package com.example.javathehutair;

import com.example.javathehutair.checkout.Checkout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.DateTimeException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        System.out.println("\nTest: creditCheckAllCorrectInfo\nPassed!" +
                "\nExpected: True, Returned: True");
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
        System.out.println("\nTest: creditCheckNoMiddleName\nPassed!" +
                "\nExpected: True, Returned: True");
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
        System.out.println("\nTest: creditCheckCreditNumNot16\nPassed!" +
                "\nExpected: False, Returned: False");
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
        System.out.println("\nTest: creditCheckCVCNot3\nPassed!" +
                "\nExpected: False, Returned: False");
    }

//    @Test
//    void creditCheckExpDateWrongFormat() {
//        String firstName = "John";
//        String middleName = "Patrick";
//        String lastName = "Doe";
//        long creditNumber = Long.parseLong("123456789012345");
//        long cvc = Long.parseLong("1234");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
//        YearMonth expDate = YearMonth.parse("1228", formatter);
//
//        var checkout = new Checkout();
//
//        assertThrowsExactly(DateTimeParseException.class, () ->
//                checkout.creditCheck(firstName, middleName, lastName, creditNumber, cvc, expDate));
//
//        System.out.println("\nTest: creditCheckExpDateWrongFormat\nPassed!" +
//                "\nExpected: DateTimeParseException, Returned: DateTimeParseException");
//    }


}