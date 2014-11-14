/*
 * Copyright 2014 Gunnar Kappei.
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
package org.moosbusch.lumPi.beans.impl;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.apache.commons.lang3.ClassUtils;

/**
 *
 * @author Gunnar Kappei
 */
public class Options {

    private final Preferences preferences;
    private final boolean autoputDefaults = true;
    private final byte[] defaultByteArray = new byte[0];
    private final boolean defaultBoolean = Boolean.FALSE;
    private final float defaultFloat = 0.0f;
    private final int defaultInteger = 0;
    private final double defaultDouble = 0.0d;
    private final long defaultLong = 0l;
    private final String defaultString = "";

    public Options(Preferences preferences) {
        this.preferences = Objects.requireNonNull(preferences);
    }

    private void putDefaultValue(String key, Object value) {
        if (value != null) {
            try {
                if (!preferences.nodeExists(key)) {
                    Class<?> valueClass = ClassUtils.primitiveToWrapper(value.getClass());

                    if (valueClass == String.class) {
                        preferences.put(key, (String) value);
                    } else if (valueClass == Integer.class) {
                        preferences.putInt(key, (int) value);
                    } else if (valueClass == Float.class) {
                        preferences.putFloat(key, (float) value);
                    } else if (valueClass == Double.class) {
                        preferences.putDouble(key, (double) value);
                    } else if (valueClass == Long.class) {
                        preferences.putLong(key, (long) value);
                    } else if (valueClass == Boolean.class) {
                        preferences.putBoolean(key, (boolean) value);
                    } else if (valueClass == Byte[].class) {
                        preferences.putByteArray(key, (byte[]) value);
                    }
                }
            } catch (BackingStoreException ex) {
                Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void put(String key, String value) {
        preferences.put(key, Objects.requireNonNull(value));
    }

    public String get(String key, String def) {
        if (isAutoputDefaults()) {
            putDefaultValue(key, def);
        }

        return preferences.get(key, def);
    }

    public String get(String key) {
        return get(key, "");
    }

    public void remove(String key) {
        preferences.remove(key);
    }

    public int getInt(String key, int def) {
        if (isAutoputDefaults()) {
            putDefaultValue(key, def);
        }

        return preferences.getInt(key, def);
    }

    public int getInt(String key) {
        return getInt(key, getDefaultInteger());
    }

    public void putInt(String key, int value) {
        preferences.putInt(key, value);
    }

    public long getLong(String key, long def) {
        if (isAutoputDefaults()) {
            putDefaultValue(key, def);
        }

        return preferences.getLong(key, def);
    }

    public long getLong(String key) {
        return getLong(key, getDefaultLong());
    }

    public void putLong(String key, long value) {
        preferences.putLong(key, value);
    }

    public boolean getBoolean(String key, boolean def) {
        if (isAutoputDefaults()) {
            putDefaultValue(key, def);
        }

        return preferences.getBoolean(key, def);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, getDefaultBoolean());
    }

    public void putBoolean(String key, boolean value) {
        preferences.putBoolean(key, value);
    }

    public float getFloat(String key, float def) {
        if (isAutoputDefaults()) {
            putDefaultValue(key, def);
        }

        return preferences.getFloat(key, def);
    }

    public float getFloat(String key) {
        return getFloat(key, getDefaultFloat());
    }

    public void putFloat(String key, float value) {
        preferences.putFloat(key, value);
    }

    public double getDouble(String key, double def) {
        if (isAutoputDefaults()) {
            putDefaultValue(key, def);
        }

        return preferences.getDouble(key, def);
    }

    public double getDouble(String key) {
        return getDouble(key, getDefaultDouble());
    }

    public void putDouble(String key, double value) {
        preferences.putDouble(key, value);
    }

    public byte[] getByteArray(String key, byte[] def) {
        if (isAutoputDefaults()) {
            putDefaultValue(key, def);
        }

        return preferences.getByteArray(key, def);
    }

    public byte[] getByteArray(String key) {
        return getByteArray(key, getDefaultByteArray());
    }

    public void putByteArray(String key, byte[] value) {
        preferences.putByteArray(key, value);
    }

    public void sync() throws BackingStoreException {
        preferences.sync();
    }

    public boolean isAutoputDefaults() {
        return autoputDefaults;
    }

    public byte[] getDefaultByteArray() {
        return defaultByteArray.clone();
    }

    public boolean getDefaultBoolean() {
        return defaultBoolean;
    }

    public float getDefaultFloat() {
        return defaultFloat;
    }

    public int getDefaultInteger() {
        return defaultInteger;
    }

    public double getDefaultDouble() {
        return defaultDouble;
    }

    public long getDefaultLong() {
        return defaultLong;
    }

    public String getDefaultString() {
        return defaultString;
    }

}
