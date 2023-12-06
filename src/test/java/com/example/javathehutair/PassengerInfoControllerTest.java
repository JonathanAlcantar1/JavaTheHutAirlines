package com.example.javathehutair;

import com.example.javathehutair.Controllers.PassengerInfoController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerInfoControllerTest {

    @Test
    void cabinSeatDecrementerFirstClass()
    {
        int cabinID = 11;
        int firstClasseats = 10;
        int bussClassSeats = 10;
        int econClassSeats = 10;
        int result = 0;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.cabinSeatDecrementer(cabinID, firstClasseats,
                bussClassSeats, econClassSeats);

        assertEquals(9, result);
        System.out.println("\nTest: cabinSeatDecrementerFirstClass\nPassed!\nExpected: 9, Returned: 9");

    }

    @Test
    void cabinSeatDecrementerBussClass()
    {
        int cabinID = 22;
        int firstClasseats = 10;
        int bussClassSeats = 10;
        int econClassSeats = 10;
        int result = 0;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.cabinSeatDecrementer(cabinID, firstClasseats,
                bussClassSeats, econClassSeats);

        assertEquals(9, result);
        System.out.println("\nTest: cabinSeatDecrementerBussClass\nPassed!\nExpected: 9, Returned: 9");

    }

    @Test
    void cabinSeatDecrementerEconClass()
    {
        int cabinID = 33;
        int firstClasseats = 10;
        int bussClassSeats = 10;
        int econClassSeats = 10;
        int result = 0;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.cabinSeatDecrementer(cabinID, firstClasseats,
                bussClassSeats, econClassSeats);

        assertEquals(9, result);
        System.out.println("\nTest: cabinSeatDecrementerEconClass\nPassed!\nExpected: 9, Returned: 9");

    }

    @Test
    void cabinSeatDecrementerNoDecrementIfZero()
    {
        int cabinID = 11;
        int firstClasseats = 0;
        int bussClassSeats = 10;
        int econClassSeats = 10;
        int result = 0;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.cabinSeatDecrementer(cabinID, firstClasseats,
                bussClassSeats, econClassSeats);

        assertEquals(-1, result);
        System.out.println("\nTest: cabinSeatDecrementerNoDecrementIfZero\nPassed!\nExpected: 0, Returned: 0");

    }

    @Test
    void cabinSeatDecrementerNoDecrementIfWrongID()
    {
        int cabinID = 12;
        int firstClasseats = 0;
        int bussClassSeats = 10;
        int econClassSeats = 10;
        int result = 0;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.cabinSeatDecrementer(cabinID, firstClasseats,
                bussClassSeats, econClassSeats);

        assertEquals(-1, result);
        System.out.println("\nTest: cabinSeatDecrementerNoDecrementIfWrongID\nPassed!\nExpected: -1, Returned: -1");

    }

    @Test
    void cabinButtonHiderFirstSeatsAvail()
    {
        int firstClassSeats = 0;
        int bussClassSeats = 10;
        int econClassSeats = 10;
        String result;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.cabinButtonHider(firstClassSeats, bussClassSeats, econClassSeats);

        assertEquals(" No first Class seats available, ", result);
        System.out.println("\nTest: cabinButtonHiderFirstSeatsAvail\nPassed!" +
                "\nExpected: No first Class seats available, , , Returned: No first Class seats available, ");

    }

    @Test
    void cabinButtonHiderBussOREconSeatsAvail()
    {
        int firstClassSeats = 10;
        int bussClassSeats = 0;
        int econClassSeats = 0;
        String result;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.cabinButtonHider(firstClassSeats, bussClassSeats, econClassSeats);

        assertEquals(" No Business Class seats available, No Economy Class Seats available, ", result);
        System.out.println("\nTest: cabinButtonHiderBussOREconSeatsAvail\nPassed!" +
                "\nExpected: No Business Class seats available, No Economy Class Seats available, " +
                ", Returned: No Business Class seats available, No Economy Class Seats available, ");

    }

    @Test
    void cabinButtonHiderAllSeatsAvail()
    {
        int firstClassSeats = 10;
        int bussClassSeats = 10;
        int econClassSeats = 10;
        String result;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.cabinButtonHider(firstClassSeats, bussClassSeats, econClassSeats);

        assertEquals(" ", result);
        System.out.println("\nTest: cabinButtonHiderAllSeatsAvail\nPassed!" +
                "\nExpected: , Returned: ");

    }

    @Test
    void addResIfNoBlanksFirstName()
    {
        String firstName = " ";
        String lastName = "Doe";
        String phoneNum = "3239999999";
        String email = "johndoe@gmail.com";
        boolean result;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.addResIfNoBlanks(firstName, lastName, phoneNum, email);

        assertFalse(result);
        System.out.println("\nTest: addResIfNoBlanksFirstName\nPassed!" +
                "\nExpected: False, Returned: False");

    }

    @Test
    void addResIfNoBlanksLastName()
    {
        String firstName = "John";
        String lastName = " ";
        String phoneNum = "3239999999";
        String email = "johndoe@gmail.com";
        boolean result;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.addResIfNoBlanks(firstName, lastName, phoneNum, email);

        assertFalse(result);
        System.out.println("\nTest: addResIfNoBlanksLastName\nPassed!" +
                "\nExpected: False, Returned: False");

    }

    @Test
    void addResIfNoBlanksPhoneNum()
    {
        String firstName = "John";
        String lastName = "Doe";
        String phoneNum = " ";
        String email = "johndoe@gmail.com";
        boolean result;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.addResIfNoBlanks(firstName, lastName, phoneNum, email);

        assertFalse(result);
        System.out.println("\nTest: addResIfNoBlanksPhoneNum\nPassed!" +
                "\nExpected: False, Returned: False");

    }

    @Test
    void addResIfNoBlanksEmail()
    {
        String firstName = "John";
        String lastName = "Doe";
        String phoneNum = "3239999999";
        String email = " ";
        boolean result;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.addResIfNoBlanks(firstName, lastName, phoneNum, email);

        assertFalse(result);
        System.out.println("\nTest: addResIfNoBlanksEmail\nPassed!" +
                "\nExpected: False, Returned: False");

    }

    @Test
    void addResIfNoBlanks()
    {
        String firstName = "John";
        String lastName = "Doe";
        String phoneNum = "3239999999";
        String email = "johndoe@gmail.com";
        boolean result;

        var paxInfoController = new PassengerInfoController();

        result = paxInfoController.addResIfNoBlanks(firstName, lastName, phoneNum, email);

        assertTrue(result);
        System.out.println("\nTest: addResIfNoBlanks\nPassed!" +
                "\nExpected: True, Returned: True");


    }
}