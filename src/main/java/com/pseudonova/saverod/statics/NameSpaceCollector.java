package com.pseudonova.saverod.statics;

import com.pseudonova.saverod.SaveRod;
import org.bukkit.NamespacedKey;

public class NameSpaceCollector {


    private static NameSpaceCollector instance;
    private SaveRod plugin;
    private NamespacedKey rodKey;

    private NameSpaceCollector(){

    }


    public static NameSpaceCollector getInstance(){
        if(instance == null)
            instance = new NameSpaceCollector();
        return instance;
    }

    public void initialize(SaveRod plugin){
        this.plugin = plugin;
        this.rodKey =  new NamespacedKey(this.plugin, "rod");
    }

    public NamespacedKey getRodKey(){
        return this.rodKey;
    }

}
