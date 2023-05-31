package com.example.employeetaskmanagementsystem.utility;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Service
public class VerificationCodeService {
    private Map<String, Integer> verificationCodes = new HashMap<>();

    public int generateVerificationCode(String email) {
        int code = new Random().nextInt(900000) + 100000; // Generate a random 6-digit code
        verificationCodes.put(email, code); // Store the code in the verificationCodes map
        return code;
    }

    public boolean verifyCode(String email, int code) {
        Integer storedCode = verificationCodes.get(email); // Get the stored code for the email
        return storedCode != null && storedCode == code; // Compare the stored code with the provided code
    }
}
