package com.example.javathehutair;

public class Checkout {

    private boolean verifiedCard = false;
    private  long lowerLimit = 1000000000000000L;
    private long upperLimit = 9999999999999999L;
    public boolean creditCheck(String firstName, String middleName, String lastName, int creditNumber, int cvc){
        if(firstName.chars().allMatch(Character::isLetter)
                && middleName.chars().allMatch(Character::isLetter)
                && lastName.chars().allMatch(Character::isLetter)
                && creditNumber >= lowerLimit
                && creditNumber <= upperLimit
                && cvc >= 100
                && cvc <= 999
        ){
            verifiedCard = true;
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
