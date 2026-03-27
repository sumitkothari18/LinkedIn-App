package com.example.LinkedIn.user_service.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {


    // hash password for the first time
    public static String hashPassword(String plainTextPassword)
    {
        return BCrypt.hashpw(plainTextPassword,BCrypt.gensalt());
    }

    public static Boolean checkPassword(String plainTextPassword, String hashedPassword)
    {
        return BCrypt.checkpw(plainTextPassword,hashedPassword);
    }
}
