package com.pseudonova.saverod.utils;

import org.bukkit.ChatColor;

public class StringUtils {

    public static String colorize(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }

}
