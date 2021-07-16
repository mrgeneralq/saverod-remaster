package com.pseudonova.saverod.statics;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class NamespaceKeyContainer {

    private static NamespaceKeyContainer instance;
    private Plugin plugin;
    private NamespacedKey rodInstanceKey;

    private NamespaceKeyContainer(){}

    public static NamespaceKeyContainer getContainer(){
        if(instance == null)
            instance = new NamespaceKeyContainer();
        return instance;
    }

    public void initialize(Plugin plugin){
        this.plugin = plugin;
        this.setKeys();
    }

    public NamespacedKey getRodInstanceKey(){
        return this.rodInstanceKey;
    }


    private void setKeys(){
        this.rodInstanceKey = new NamespacedKey(this.plugin, "rod-instance");
    }

}
