/*
 * Copyright (C) 2014-2016 The Dirty Unicorns Project
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
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import androidx.preference.ListPreference;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto;
import com.bootleggers.dumpster.extra.Utils;

public class Misc extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

        private static final String TAG = "Misc";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.bootleg_dumpster_cat_misc);
        setRetainInstance(true);

        ContentResolver resolver = getActivity().getContentResolver();

        // Possible packages to start the custom activity
        String[] deviceExtrasIntent = {
            "com.android.settings.action.EXTRA_SETTINGS",
            "android.intent.action.MAIN",
            "android.intent.action.MAIN",
            "android.intent.action.MAIN"
        };
        String[] deviceExtrasPackages = {
            "com.dirtyunicorns.settings.device",
            "org.omnirom.device",
            "org.lineageos.settings.device",
            getResources().getString(R.string.device_extras_package_name)
        };
        String[] deviceExtrasActivities = {
            "com.dirtyunicorns.settings.device.TouchscreenGestureSettings",
            "org.omnirom.device.DeviceSettingsActivity",
            "org.lineageos.settings.device.DeviceSettingsActivity",
            getResources().getString(R.string.device_extras_activity_name)
        };

        // Check in all the prefs
        Preference appRelatedPref = findPreference("app_related_category");
        Preference deviceExtras = findPreference("device_extras_category");
        Preference systemPref = findPreference("system_category");

        // Cleanup if we have the switches disabled
        if (!getResources().getBoolean(R.bool.has_app_related_options)) getPreferenceScreen().removePreference(appRelatedPref);
        if (!getResources().getBoolean(R.bool.has_system_options)) getPreferenceScreen().removePreference(systemPref);
        boolean hasDeviceExtras = false;
        for (int i = 0; i < deviceExtrasPackages.length; i++) {
            if (hasDeviceExtras) return;

            String pkg = deviceExtrasPackages[i];
            if (pkg == null || pkg == "") return;
            if (Utils.isPackageInstalled(getActivity(), pkg)) {
                Intent devExtrasIntent = new Intent(deviceExtrasIntent[i]);
                devExtrasIntent.setClassName(pkg, deviceExtrasActivities[i]);
                deviceExtras.setIntent(devExtrasIntent);

                String extrasAppName = Utils.getPackageLabel(getActivity(), pkg);
                if (extrasAppName != null) {
                    deviceExtras.setTitle(extrasAppName);
                }
                String devModel = SystemProperties.get("ro.product.model", null);
                if (devModel != null) {
                    deviceExtras.setSummary(getResources().getString(
                            R.string.device_extras_summary, devModel));
                }
                hasDeviceExtras = true;
            } else {
                hasDeviceExtras = false;
            }
        }
        if (!hasDeviceExtras) {
            getPreferenceScreen().removePreference(deviceExtras);
        }
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

