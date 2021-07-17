package com.pseudonova.saverod.abstracts;

import com.pseudonova.saverod.interfaces.ICommand;

public abstract class AbstractCommand implements ICommand {

    private final String name;
    private final String permission;
    private final String description;

    protected AbstractCommand(String name, String permission, String description) {
        this.name = name;
        this.permission = String.format("saverod.%s", permission);
        this.description = description;
    }

    public String getName(){
        return this.name;
    }

    public String getPermission(){
        return this.permission;
    }

    public String getDescription(){
        return this.description;
    }
}
