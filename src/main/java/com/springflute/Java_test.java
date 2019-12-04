package com.springflute;

import util.ID_check_util;
import util.VIN_check_util;

public class Java_test {
    public static void main(String[] args) {
        String [] ss = {"LVSFCAMEX8F265694",
                "LNPFPGBC478663411",
                "",
                "WBAFR7103BC727722",
                "HJKLGFFA2NV145678",
                "JGHYGBC7891NB9354"};
        for (String s :ss) {
            System.out.println(VIN_check_util.checkVIN(s));
        }
    }
}
