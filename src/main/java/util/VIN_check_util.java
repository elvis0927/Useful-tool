package util;

import java.util.HashMap;
import java.util.Map;

public class VIN_check_util {
    //字符权重表
    private static final Map<Character, Integer> CHAR_WEIGHTS = new HashMap<>();
    //位置权重表
    private static final int[] POS_WEIGHTS = new int[]{8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2};

    static {
        for (int i = 0; i < 10; i++) {
            CHAR_WEIGHTS.put(String.valueOf(i).charAt(0), i);
        }
        CHAR_WEIGHTS.put('A', 1);
        CHAR_WEIGHTS.put('B', 2);
        CHAR_WEIGHTS.put('C', 3);
        CHAR_WEIGHTS.put('D', 4);
        CHAR_WEIGHTS.put('E', 5);
        CHAR_WEIGHTS.put('F', 6);
        CHAR_WEIGHTS.put('G', 7);
        CHAR_WEIGHTS.put('H', 8);
        CHAR_WEIGHTS.put('J', 1);
        CHAR_WEIGHTS.put('K', 2);
        CHAR_WEIGHTS.put('L', 3);
        CHAR_WEIGHTS.put('M', 4);
        CHAR_WEIGHTS.put('N', 5);
        CHAR_WEIGHTS.put('P', 7);
        CHAR_WEIGHTS.put('R', 9);
        CHAR_WEIGHTS.put('S', 2);
        CHAR_WEIGHTS.put('T', 3);
        CHAR_WEIGHTS.put('U', 4);
        CHAR_WEIGHTS.put('V', 5);
        CHAR_WEIGHTS.put('W', 6);
        CHAR_WEIGHTS.put('X', 7);
        CHAR_WEIGHTS.put('Y', 8);
        CHAR_WEIGHTS.put('Z', 9);
    }


    public static String checkVIN(String vin) {
        boolean flag = true;
        if (null == vin || vin.equals("")) {
            flag = false;
        } else if (vin.trim().length() == 17) {
            vin = vin.toUpperCase();
            int sum = 0;
            int checkSum = 0;
            for (int i = 0; i < vin.length(); i++) {
                char code = vin.charAt(i);
                Integer cw = CHAR_WEIGHTS.get(code);
                if (cw == null) {
                    flag = false;
                }
                int pw = POS_WEIGHTS[i];
                sum += cw * pw;
                if (i == 8) {
                    // 获取校验位的值
                    if (code == 'X') {
                        checkSum = 10;
                    } else if (code >= '0' && code <= '9') {
                        checkSum = Integer.valueOf(String.valueOf(code));
                    } else {
                        flag = false;
                    }
                }
            }
            flag = checkSum == sum % 11;
        }
        if (flag==true)
            return vin;
        else
            return null;
    }
}
