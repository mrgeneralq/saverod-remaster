package com.pseudonova.saverod.statics;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.repositories.RodRepository;
import com.pseudonova.saverod.services.RodService;

public class Bootstrapper {
    private static Bootstrapper instance;
    private SaveRod main;

    private IRodService rodService;
    private RodRepository rodRepository;

    private Bootstrapper() {}

    public static Bootstrapper getBootstrapper(){
        if(instance == null)
            instance = new Bootstrapper();
        return instance;
    }

    public void initialize(SaveRod main){
        this.main = main;

        this.rodRepository = new RodRepository(main);
        this.rodService = new RodService(main, rodRepository);

    }

    public IRodService getRodService() {
        return rodService;
    }
}
