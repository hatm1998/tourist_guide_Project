package com.example.touristguide.Utilis;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

import io.opencensus.resource.Resource;

public class LoacalHelper {

    private static final String Selected_Language = "Local.Helper.Selected.Language";

    public static Context onAttach(Context context)
    {
        String lang = getPeristedData(context, Locale.getDefault().getLanguage());
        return setLocal(context,lang);
    }

    public static Context onAttach(Context context , String DefLanguage)
    {
        String lang = getPeristedData(context, DefLanguage);
        return setLocal(context,lang);
    }

    public static Context setLocal(Context context, String lang) {
        persits(context,lang);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return updateResuorce(context,lang);
        }

        return updateResuorceLegecy(context,lang);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResuorce(Context context, String lang) {
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);

    }

    @SuppressWarnings("deprecation")
    private static Context updateResuorceLegecy(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());

        return context;


    }

    private static void persits(Context context , String Lang)
    {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Selected_Language,Lang);
    }

    private static String getPeristedData(Context context, String language) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Selected_Language, language);

    }
}
