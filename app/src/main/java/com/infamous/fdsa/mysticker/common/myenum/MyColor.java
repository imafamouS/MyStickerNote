package com.infamous.fdsa.mysticker.common.myenum;

/**
 * Created by apple on 5/22/17.
 */

public enum MyColor {
    COLOR_1("#E54345", "#F67580", "#FFE7E9", "#F8B5BB"),
    COLOR_2("#FFA200", "#FFA854", "#FFEBD7", "#E0B68D"),
    COLOR_3("#FFE000", "#F5DA66", "#FFF8BB", "#FAEF8C"),
    COLOR_4("#6FC230", "#96D468", "#E6F8DB", "#C4E9AB"),
    COLOR_5("#0070CF", "#84A5FF", "#E8E8FF", "#BECCFF"),
    COLOR_6("#7B35BD", "#B487DE", "#F0E0FF", "#D7BBF1"),
    COLOR_7("#888888", "#CCCCCC", "#EEEEEE", "#E0E0E0"),
    COLOR_8("#000000", "#333333", "#CCCCCC", "#8C8C8C"),
    COLOR_9("#FFFFFF", "#F0F0F0", "#FFFFFF", "#F9F9F9"),
    COLOR_COMPLETE("#000000", "#8B8B8B", "#000000", "#000000");


    String hex;
    String hex2;
    String hex3;
    String hex4;

    private MyColor(String hexColor, String hexColor2, String hexColor3, String hexColor4) {
        this.hex = hexColor;
        this.hex2 = hexColor2;
        this.hex3 = hexColor3;
        this.hex4 = hexColor4;
    }

    public static MyColor getColorByIndex(int index) {
        switch (index) {
            case 1:
                return COLOR_1;
            case 2:
                return COLOR_2;
            case 3:
                return COLOR_3;
            case 4:
                return COLOR_4;
            case 5:
                return COLOR_5;
            case 6:
                return COLOR_6;
            case 7:
                return COLOR_7;
            case 8:
                return COLOR_8;
            case 9:
                return COLOR_9;
            case 10:
                return COLOR_COMPLETE;
            default:
                return null;


        }
    }

    public static MyColor getColorByHex(String hex) {
        MyColor mycolor = null;
        for (MyColor i : MyColor.values()) {
            if (i.getColor().equals(hex)) {
                mycolor = i;
                break;
            }
        }
        return mycolor;
    }


    public String getColor() {
        return hex;
    }

    public String getColor2() {
        return hex2;
    }

    public String getColor3() {
        return hex3;
    }

    public String getColor4() {
        return hex4;
    }
}
