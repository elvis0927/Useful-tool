package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LicensePlateNumber_check_util {

    public static String checkLicensePlateNumber(String licensePlateNumber) {
        String regex = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(licensePlateNumber);
        if (!m.matches())
            return null;
        return licensePlateNumber;
    }
}
