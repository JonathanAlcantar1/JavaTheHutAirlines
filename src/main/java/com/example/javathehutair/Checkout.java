package com.example.javathehutair;

import java.time.LocalDate;
import java.time.YearMonth;

public class Checkout {
    private boolean verifiedCard;
    public boolean creditCheck(String firstName, String middleName, String lastName, long creditNumber, long cvc, YearMonth expDate){
        setVerifiedCard(false);
        long lowerLimit = 1000000000000000L;
        long upperLimit = 9999999999999999L;
        if(firstName.chars().allMatch(Character::isLetter) && !firstName.isBlank()) {
            //System.out.println("firstname char match");
            if (middleName.chars().allMatch(Character::isLetter) || middleName.isBlank()) {
                //System.out.println("middlename char match");
                if (lastName.chars().allMatch(Character::isLetter) && !lastName.isBlank()) {
                    //System.out.println("lastname char match");
                    if(creditNumber >= lowerLimit && creditNumber <= upperLimit) {
                        //System.out.println("Creditnumber pass");
                        if (cvc >= 100 && cvc <= 999) {
                            //System.out.println("Cvc pass");
                            if(expDate.isAfter(YearMonth.now())) {
                                //System.out.println("Date pass");
                                setVerifiedCard(true);
                            }
                        }
                    }
                }
            }
        }

        return verifiedCard;
    }
    public void setVerifiedCard(boolean verifiedCard ){
        this.verifiedCard = verifiedCard;
    }
    public boolean getVerifiedCard(){
        return verifiedCard;
    }
}
