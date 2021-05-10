package me.sobolewski.clinic.account;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class InputValidation {
    
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("\\A[a-z0-9!#$%&'*+/=?^_‘{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_‘{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\z");
        return pattern.matcher(email).matches();
    }
    
    public boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("([+]+[0-9]{1,3})?[0-9]{9}$");
        return pattern.matcher(phoneNumber).matches();
    }
    
}
