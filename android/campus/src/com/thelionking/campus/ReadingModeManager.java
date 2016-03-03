package com.thelionking.campus;

import java.util.HashSet;
import java.util.Set;

/**
 * @author the lion king
 * @version 1.0
 * <h>Singleton</h>
 */
public final class ReadingModeManager implements IReadingMode{
    private Set<IReadingMode> callbacks;

    private ReadingModeManager(){
        callbacks = new HashSet<IReadingMode>();
    }

    public static ReadingModeManager getInstance() {
        return SingleHelper.INSTANCE;
    }

    @Override
    public void onReadingModeChanged(int mode) {
        for(IReadingMode callback : callbacks){
            if(callback != null){
                callback.onReadingModeChanged(mode);
            }
        }
    }

    private static class SingleHelper{
        static ReadingModeManager INSTANCE = new ReadingModeManager();
    }

    public void add(IReadingMode callback) {
        callbacks.add(callback);
    }

    public void remove(IReadingMode callback){
        callbacks.remove(callback);
    }

}
