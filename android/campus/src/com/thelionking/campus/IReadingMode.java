package com.thelionking.campus;

/**
 * @author the lion king
 */
public interface IReadingMode {
    //日间模式
    public static final int DAY_MODE = 0;
    //夜间模式
    public static final int NIGHT_MODE = 1;

    void onReadingModeChanged(int mode);
}
