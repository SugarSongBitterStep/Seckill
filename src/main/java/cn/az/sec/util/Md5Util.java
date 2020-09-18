package cn.az.sec.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String SALT = "9d5b364d";

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    public static String formPassToDbPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDb) {
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDbPass(formPass, saltDb);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDbPass("12345678", "9d5b364d"));
    }

}
