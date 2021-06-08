package com.pseudonova.saverod.statics;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.repositories.RodInstanceRepository;
import com.pseudonova.saverod.repositories.RodRepository;
import com.pseudonova.saverod.services.RodInstanceService;
import com.pseudonova.saverod.services.RodService;

public class Bootstrapper {
    private static Bootstrapper instance;
    private SaveRod main;

    public IRodInstanceService getRodInstanceService() {
        return rodInstanceService;
    }

    private IRodInstanceService rodInstanceService;
    private RodInstanceRepository rodInstanceRepository;

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

        this.rodInstanceRepository = new RodInstanceRepository(main);
        this.rodInstanceService = new RodInstanceService(this.rodInstanceRepository);

        this.rodRepository = new RodRepository(main);
        this.rodService = new RodService(this.rodInstanceService, this.rodRepository);

    }

    public IRodService getRodService() {
        return rodService;
    }
}
