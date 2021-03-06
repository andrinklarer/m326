package com.google;

import java.awt.*;
import java.text.DecimalFormat;

public class DefaultValues {
    public static final Color COLOR_TEXT_MAIN = new Color(0, 0, 0);
    public static final Color COLOR_TEXT_PLACEHOLDER = new Color(75, 75, 75);
    public static final Color COLOR_TEXT_ERROR = new Color(180, 0, 0);
    public static final Color COLOR_TEXT_POSITIVE = new Color(0, 180, 0);

    public static final Color COLOR_BACKGROUND_LIGHT = new Color(0xBFD7ED);
    public static final Color COLOR_BACKGROUND_MAIN = new Color(0x60A3D9);
    public static final Color COLOR_BACKGROUND_DARK = new Color(0x0074B7);

    public static final char CHAR_PASSWORD_ECHO_CHAR = '*';

    public static final String USER_DATA_FILE_PATH ="users.txt";
    public static final String STOCK_DATA_FILE_PATH ="stocks.txt";

    public static final Font FONT_MAIN = new Font("Dialog", Font.PLAIN,12);
    public static final Font FONT_BOLD =new Font("Dialog", Font.BOLD,12);
    public static final Font FONT_TITLE = new Font("Dialog", Font.BOLD,36);

    public static final int MIN_SCORE = 0;
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
}
