package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Phone_check_util {

    public static String checkPhone(String phoneNumber) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phoneNumber.length() != 11) {
            return null;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phoneNumber);
            if(!m.matches())
                return null;
            return phoneNumber;
        }
    }
}
