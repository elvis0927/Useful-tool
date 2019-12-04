package util;

public class ID_check_util {
    /**
     * 18位二代身份证号码的正则表达式
     */
    public static final String REGEX_ID_NO_18 = "^"
            + "\\d{6}" // 6位地区码
            + "(18|19|([23]\\d))\\d{2}" // 年YYYY
            + "((0[1-9])|(10|11|12))" // 月MM
            + "(([0-2][1-9])|10|20|30|31)" // 日DD
            + "\\d{3}" // 3位顺序码
            + "[0-9Xx]" // 校验码
            + "$";

    /**
     * 15位一代身份证号码的正则表达式
     */
    public static final String REGEX_ID_NO_15 = "^"
            + "\\d{6}" // 6位地区码
            + "\\d{2}" // 年YYYY
            + "((0[1-9])|(10|11|12))" // 月MM
            + "(([0-2][1-9])|10|20|30|31)" // 日DD
            + "\\d{3}"// 3位顺序码
            + "$";

    /**
     * 1、校验的入口，先判断长度
     */
    public static String checkIDNo(String IDNumber){
        if(IDNumber.length() == 18){
            if(checkIDNo18(IDNumber))
                return IDNumber;
            else
                return null;
        }else if(IDNumber.length() == 15)
            return updateIDNo15to18(IDNumber);
        else
            return null;
    }

    /**
     * 2、匹配正则表达式
     */
    private static boolean regexMatch(String inputString, String regex) {
        return inputString.matches(regex);
    }

    /**
     * 3、计算校验码
     */
    private static String computeCheckNumber(String IDNo18) {
        // 加权因子
        int[] W = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        char[] masterNumberArray = IDNo18.substring(0,17).toCharArray();
        int sum = 0;
        for (int i = 0; i < W.length; i++) {
            sum += Integer.parseInt(String.valueOf(masterNumberArray[i])) * W[i];
        }
        // 根据同余定理得到的校验码数组
        String[] checkNumberArray = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
        //得到校验码在数组中的下标index
        int index = sum % 11;
        // 得到校验码
        String checkNumber = checkNumberArray[index];
        // 返回校验码
        return checkNumber;
    }

    /**
     * 校验身份证号,适用于18位的二代身份证号码
     */
    private static Boolean checkIDNo18(String IDNo18) {
        // 匹配身份证号码的正则表达式
        if (!regexMatch(IDNo18, REGEX_ID_NO_18)) {
            return false;
        }
        //判断校验码与最后一位是否一致
        String lastNumber = IDNo18.substring(17).toUpperCase();
        if(!computeCheckNumber(IDNo18).equals(lastNumber))
            return false;
        return true;
    }

    /**
     * 15位一代身份证号码升级18位二代身份证号码,增加年份的前2位和最后1位校验码
     */
    private static String updateIDNo15to18(String IDNo15) {
        // 匹配身份证号码的正则表达式
        if (!regexMatch(IDNo15, REGEX_ID_NO_15)) {
            return null;
        }
        // 得到本体码，因一代身份证皆为19XX年生人，年份中增加19，组成4位
        String masterNumber = IDNo15.substring(0, 6) + "19" + IDNo15.substring(6);
        // 计算校验码
        String checkNumber = computeCheckNumber(masterNumber);
        // 返回本体码+校验码=完整的身份证号码
        return masterNumber + checkNumber;
    }
}
