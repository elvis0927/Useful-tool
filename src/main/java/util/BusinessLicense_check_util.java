package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessLicense_check_util {

    public static String checkBusinessLicense(String businessLicense) {
        String regex = "/(^(?:(?![IOZSV])[\\dA-Z]){2}\\d{6}(?:(?![IOZSV])[\\dA-Z]){10}$)|(^\\d{15}$)/";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(businessLicense);
        if (!m.matches())
            return null;
        return businessLicense;
    }
}
