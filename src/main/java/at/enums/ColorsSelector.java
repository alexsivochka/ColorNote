package at.enums;

import java.util.HashMap;
import java.util.Map;

public enum ColorsSelector {

    RED("btn1","#E54345"),
    ORANGE("btn2","#FFA200"),
    YELLOW("btn3","#FFE000"),
    GREEN("btn4","#6FC230"),
    BLUE("btn5","#0070CF"),
    PURPLE("btn6","#7B35BD"),
    BLACK("btn7","#000000"),
    GRAY("btn8","#888888"),
    WHITE("btn9","#E8E8E8");


    private static final Map<String, ColorsSelector> BY_ID = new HashMap<>();
    private static final Map<String, ColorsSelector> BY_VALUE = new HashMap<>();

    private final String key;
    private final String hexValue;

    static {
        for (ColorsSelector service : values()) {
            BY_ID.put(service.key, service);
            BY_VALUE.put(service.hexValue, service);
        }
    }

    ColorsSelector(String key, String hexValue){
        this.key = key;
        this.hexValue = hexValue;
    }

    public String getKey() {
        return key;
    }
    public String getHexValue() {
        return hexValue;
    }

    public static String getValueById(String code) {
        return BY_ID.get(code).getHexValue();
    }
    public static String getIdByValue(String hexValue) {
        return BY_VALUE.get(hexValue).getKey();
    }

}
