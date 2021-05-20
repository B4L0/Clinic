package me.sobolewski.clinic.account;

import lombok.experimental.UtilityClass;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.Random;

@UtilityClass
public class AuthData {
    
    private static final Random RANDOM = new Random();
    
    private static String generate(int length) {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        int lcCount = RANDOM.nextInt(length - length / 2) + 1;
        lowerCaseRule.setNumberOfCharacters(lcCount);
        
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        int ucCount = RANDOM.nextInt(length - length / 3 - lcCount) + 1;
        upperCaseRule.setNumberOfCharacters(ucCount);
        
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        int dCount = length - lcCount - ucCount;
        digitRule.setNumberOfCharacters(dCount);
        
        return gen.generatePassword(length, lowerCaseRule, upperCaseRule, digitRule);
    }
    
    @UtilityClass
    public static class Login {
        
        private static final int LENGTH = 8;
        
        public static String generate() {
            return AuthData.generate(LENGTH);
        }
        
    }
    
    @UtilityClass
    public static class Password {
        
        private static final int LENGTH = 12;
        
        public static String generate() {
            return AuthData.generate(LENGTH);
        }
        
    }
    
    
}
