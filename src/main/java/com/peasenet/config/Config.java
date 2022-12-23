package com.peasenet.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.peasenet.main.Settings;

import java.lang.reflect.Type;

public abstract class Config<E> {

    public <E> E getConfig() {
        Gson gson = new Gson();
        Type type = TypeToken.of(getClass()).getType();
        E data;
        if (Settings.settings.containsKey(key)) {
            data = gson.fromJson(Settings.settings.get(key).toString(), type);
            if (data == null) {
                loadDefaultConfig();
                getInstance();
            }
        } else {
            loadDefaultConfig();
            data = (E) getInstance();
        }
        return data;
    }

    static String key;

    public void setKey(String key) {
        key = key;
    }

    public abstract void setInstance(E data);

    public abstract void loadDefaultConfig();

    public abstract E getInstance();

    public void saveConfig() {
        var cfg = getInstance();
        Settings.settings.put(key, cfg);
        Settings.save();
    }

    public abstract void readFromSettings();
//    public void readFromSettings() {
//        setInstance((E)Settings);
//    }
}
