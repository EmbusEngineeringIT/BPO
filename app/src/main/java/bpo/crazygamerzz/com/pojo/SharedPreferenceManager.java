package bpo.crazygamerzz.com.pojo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by code2 on 12/2/18.
 */

public class SharedPreferenceManager
{
    public Context context;
    public static final String TIME_SPENT_ON_LEVEL = "time.spent.on.level";
    private static SharedPreferenceManager sInstance;
    /**
     * Used to make commits to the shared preferences file, don't use this directly, use the getter!!
     */
    private SharedPreferences mPreferencesManager;
    /**
     * Private Constructor
     */
    private SharedPreferenceManager() {
    }
    /**
     * Gets the instance(object) for this class
     *
     * @return The Instance of SharedManager singleton class
     */
    public static SharedPreferenceManager instance() {
        if (sInstance == null) {
            sInstance = new SharedPreferenceManager();
        }
        return sInstance;
    }
    public SharedPreferences getPreferencesManager() {
        if (mPreferencesManager == null) {
            mPreferencesManager = context.getSharedPreferences("saveState", Context.MODE_PRIVATE);
        }
        return mPreferencesManager;
    }
    /**
     * Save the time spent on a level
     *
     * @param milliseconds time spent on a level
     */
    public void persistTimeSpentOnLevel(long milliseconds) {
        getPreferencesManager().edit().putLong(TIME_SPENT_ON_LEVEL, milliseconds).commit();
    }
    /**
     * Returns the time spent on a level or 0 if the game is new
     *
     * @return milliseconds passed since the game started
     */
    public long getTimeSpentOnLevel() {
        return getPreferencesManager().getLong(TIME_SPENT_ON_LEVEL, 0);
    }
}

