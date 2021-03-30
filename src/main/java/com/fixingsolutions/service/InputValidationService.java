package com.fixingsolutions.service;

public class InputValidationService {

    public static boolean isValidStringInput(String input){

        if(input == null || input.isEmpty()){
            return false;
        }

        return true;

    }

}
