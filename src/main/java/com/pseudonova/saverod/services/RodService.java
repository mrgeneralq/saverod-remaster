package com.pseudonova.saverod.services;

import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.repositories.RodRepository;


public class RodService implements IRodService
{
    private final RodRepository rodRepository;


    public RodService(RodRepository rodRepository) {
        this.rodRepository = rodRepository;
    }

    @Override
    public boolean rodExists(String name){
        return this.rodRepository.containsKey(name);
    }



    @Override
    public Rod getRodByName(String name){


        return rodRepository.getValue(name);
    }

    @Override
    public void createRod(Rod rod) {
        this.rodRepository.addOrUpdate(rod);
    }


    /*@Override
    public RodInstance getInstance(ItemStack item) {
        if(!item.hasItemMeta())
            return null;

        if(!item.getItemMeta().hasLore())
            return null;

        List<String> lore = item.getItemMeta().getLore();

        if(lore.isEmpty())
            return null;

        String instanceLine = lore.stream()
                .map(ChatColor:: stripColor)
                .filter(line -> line.startsWith(RodInstance.ROD_IDENTIFIER))
                .findFirst()
                .orElse(null);

        if(instanceLine == null)
            return null;

        String instanceID = instanceLine.replace(RodInstance.ROD_IDENTIFIER, "");

        return rodInstanceService.getRodInstance(instanceID);
    }

     */
}
