/*
 * Copyright (C) 2016 AospExtended ROM Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bootleggers.dumpster.categories;

import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import androidx.preference.ListPreference;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class Buttons extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "Buttons";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.bootleg_dumpster_cat_buttons);
        setRetainInstance(true);

        ContentResolver resolver = getActivity().getContentResolver();

        // Check in all the prefs
        Preference volumePref = findPreference("volume_category");
        Preference powerMenuPref = findPreference("powermenu_category");
        Preference navbarPref = findPreference("navbar_category");
        Preference hwKeysPref = findPreference("hwkeys_category");
        Preference fingerprintPref = findPreference("fingerprint_category");

        // Cleanup if we have the switches disabled
        if (!getResources().getBoolean(R.bool.has_volume_options)) getPreferenceScreen().removePreference(volumePref);
        if (!getResources().getBoolean(R.bool.has_powermenu_options)) getPreferenceScreen().removePreference(powerMenuPref);
        if (!getResources().getBoolean(R.bool.has_navbar_options)) getPreferenceScreen().removePreference(navbarPref);
        if (!getResources().getBoolean(R.bool.has_hwkeys_options)) getPreferenceScreen().removePreference(hwKeysPref);
        if (!getResources().getBoolean(R.bool.has_fingerprint_options)) getPreferenceScreen().removePreference(fingerprintPref);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.BOOTLEG;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();
        return true;
    }
}
