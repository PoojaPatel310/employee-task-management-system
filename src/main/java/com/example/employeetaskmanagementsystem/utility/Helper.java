package com.example.employeetaskmanagementsystem.utility;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Random;

public class Helper {

    public static boolean checkUserRole(){
        String role="";
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        if (session.getAttribute("urole") != null){
            role = session.getAttribute("urole").toString();
        }
        System.out.println(role.equalsIgnoreCase("USER"));
        return role.equalsIgnoreCase("USER");
    }



    public static boolean checkAdminRole(){
        String role="";
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        if (session.getAttribute("urole") != null){
            role = session.getAttribute("urole").toString();
        }
        return role.equalsIgnoreCase("ADMIN");
    }

    public static String generateRandomNumber(){

        Random random = new Random();
        int randomNumber = random_int(999, 100);
        char[] randomChars = "qwertasdfgxcvbnmMNBVCyuiopXZLKJHGFDSAPOIUhjklzYTREWQ".toCharArray();
        StringBuilder sb = new StringBuilder();
        sb.append(randomChars[random.nextInt(randomChars.length)]);
        sb.append(randomChars[random.nextInt(randomChars.length)]);
        sb.append(randomChars[random.nextInt(randomChars.length)]);
        String randomString = sb.toString();
        String result = String.format("%d%s", randomNumber, randomString);
        return result;
    }

    public static int random_int(int max,int min)
    {
        return (int) (Math.random()*(max-min))+min;

    }


}
