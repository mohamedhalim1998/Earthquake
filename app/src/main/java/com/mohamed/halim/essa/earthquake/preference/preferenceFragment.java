package com.mohamed.halim.essa.earthquake.preference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamed.halim.essa.earthquake.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class preferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener
        , Preference.OnPreferenceChangeListener {

    public preferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.earthquake_prefrences);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();
        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference p = preferenceScreen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * set the summary of preference
     * and set change listener if edit text preference
     *
     * @param p     : to set the summary of
     * @param value : of the the preference
     */
    private void setPreferenceSummary(Preference p, String value) {
        if (p instanceof EditTextPreference) {
            p.setOnPreferenceChangeListener(this);
            p.setSummary(value);
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        String value = sharedPreferences.getString(key, "0.0");
        setPreferenceSummary(preference, value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        try {
            float val = Float.parseFloat((String) newValue);
            if (val < 0 || val > 10) {
                Toast.makeText(getContext(), "Magitude must be between 0 and 10", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Value can't be set to amount", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
