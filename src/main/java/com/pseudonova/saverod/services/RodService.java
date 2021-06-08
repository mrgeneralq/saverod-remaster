package com.pseudonova.saverod.services;

import com.pseudonova.saverod.SaveRod;
import com.pseudonova.saverod.interfaces.IRodInstanceService;
import com.pseudonova.saverod.interfaces.IRodService;
import com.pseudonova.saverod.models.Ability;
import com.pseudonova.saverod.models.Rod;
import com.pseudonova.saverod.models.RodInstance;
import com.pseudonova.saverod.repositories.RodRepository;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RodService implements IRodService
{
    private final RodRepository rodRepository;
    private final IRodInstanceService rodInstanceService;
    private static final String ROD_IDENTIFIER = "rod-id: ";

    public RodService(IRodInstanceService rodInstanceService, RodRepository rodRepository) {
        this.rodRepository = rodRepository;
        this.rodInstanceService = rodInstanceService;
    }

    @Override
    public boolean isRod(ItemStack item) {
        return this.getInstance(item) != null;
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
    public void createRod(Rod rod){
        this.rodRepository.addOrUpdate(rod);
    }

    @Override
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
                .filter(line -> line.startsWith(ROD_IDENTIFIER))
                .findFirst()
                .orElse(null);

        if(instanceLine == null)
            return null;

        String instanceID = instanceLine.replace(ROD_IDENTIFIER, "");

        return rodInstanceService.getRodInstance(instanceID);
    }

    @Override
    public ItemStack getRodItem(RodInstance rodInstance) {

        Rod rod = this.getRodByName(rodInstance.getRodID());
        ItemStack itemStack = new ItemStack(rod.getMaterial());

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(rod.getDisplayName());

        List<String> lore = getLoreWithAbilities(rod);
        lore.add(ChatColor.AQUA + ROD_IDENTIFIER + ChatColor.GOLD + rodInstance.getInstanceID());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static List<String> getLoreWithAbilities(Rod rod){

        List<String> newLore = new ArrayList<>(rod.getLore());
        newLore.add("");

        newLore.add(ChatColor.GRAY + "Abilities(" + ChatColor.GREEN + rod.getAbilities().size() + ChatColor.GRAY + "):");

        newLore.addAll(rod.getAbilities().stream().map(Ability::getName).map(abilityName -> ChatColor.GREEN + abilityName).collect(Collectors.toList()));

        return newLore;
    }
}
