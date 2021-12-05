package com.notayessir.util;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


/**
 * 图形验证码随机工具类 image captcha util
 */
public class RandomUtil {



    /**
     * color set
     */
    private static final Color[] COLORS = {Color.DARK_GRAY, Color.BLACK, Color.RED,
             Color.ORANGE, Color.GREEN, Color.MAGENTA,
             Color.BLUE,
    };

    /**
     * uppercase and lowercase letter
     */
    private static final String [] LETTER = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "a","b","c","d","e","f","g","h","j","k","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
    };

    /**
     * uppercase letter and number
     */
    private static final String [] LETTER_NUMBER = {
            "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
            "1","2","3","4","5","6","7","8","9"
    };






    /**
     * randomly return a letter
     * @return  a letter
     */
    private static String genLetter() {
        int min = 0;
        int max = LETTER.length - 1;
        return LETTER[min + (int) (Math.random() * (max - min + 1))];
    }

    /**
     * randomly return a letter or number
     * @return  a letter or number
     */
    public static String genLetterOrNumber() {
        int min = 0;
        int max = LETTER_NUMBER.length - 1;
        return LETTER_NUMBER[min + (int) (Math.random() * (max - min + 1))];
    }

    /**
     * randomly return a color
     *
     * @return color {@link RandomUtil#COLORS}
     */
    public static Color genColor() {
        int min = 0;
        int max = COLORS.length - 1;
        return COLORS[min + (int) (Math.random() * (max - min + 1))];
    }

    /**
     * randomly return a number which range in [0-max]
     *
     * @param max   max value
     * @return      a number which range in [0-max]
     */
    public static int genNum(int max) {
        int min = 0;
        return min + (int) (Math.random() * (max - min + 1));
    }


    /**
     * randomly return a number which range in [min-max]
     *
     * @param min   min value
     * @param max   max value
     * @return      a number which range in [min-max]
     */
    public static int genNum(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }


    /**
     * randomly return n letter
     *
     * @param num   number
     * @return      n letter
     */
    public static String genLetterStr(int num) {
        StringBuilder letterStr = new StringBuilder(num);
        for (int i = 0; i < num; i++) {
            letterStr.append(genLetter());
        }
        return letterStr.toString();
    }



    /**
     * randomly return N letter or number
     *
     * @param num   number
     * @return      N letter or number
     */
    public static String genLetterAndNumberStr(int num) {
        StringBuilder letterStr = new StringBuilder(num);
        for (int i = 0; i < num; i++) {
            letterStr.append(genLetterOrNumber());
        }
        return letterStr.toString();
    }


    /**
     * randomly return N value which range [0,num]
     * @param max       max value
     * @param count     count
     * @return          N value which range [0,num]
     */
    public static int [] genRandomIndex(int max, int count) {
        if (count >= max){
            throw new RuntimeException("'count' must be smaller than 'num'.");
        }
        int [] index = new int[count];
        ArrayList<Integer> indexArr = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            indexArr.add(i);
        }
        Collections.shuffle(indexArr);
        for (int i = 0; i < count; i++) {
            index[i] = indexArr.get(i);
        }
        return index;

    }




}
