package com.pseudonova.saverod.services;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IConfigService;

public class ConfigService implements IConfigService {

    private SaveRod plugin;

    public ConfigService(SaveRod plugin) {
        this.plugin = plugin;
    }

    @Override
    public void reloadConfig() {
        this.plugin.reloadConfig();
    }
}
