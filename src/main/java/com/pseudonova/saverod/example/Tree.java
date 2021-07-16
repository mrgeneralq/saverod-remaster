package com.pseudonova.saverod.example;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private int apples;

    private List<AppleFallllistener> listeners = new ArrayList<>();

    public Tree(int apples){
        this.apples = apples;
    }

    public void registerFallListener(AppleFallllistener listener){
        this.listeners.add(listener);
    }

    public void dropApple(){
        this.apples--;

        for (AppleFallllistener listener : this.listeners)
            listener.onFall(this);
    }
}
