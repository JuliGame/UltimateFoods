package net.juligame.ultimatefoods.utils;
public class Base64 {

    public static boolean IsBase64(String str) {
        try {
            java.util.Base64.getDecoder().decode(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
