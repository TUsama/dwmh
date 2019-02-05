package com.noobanidus.dwmh.proxy.steeds;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

public class DummySteedProxy implements ISteedProxy {
    public boolean isTeleportable(Entity entity, EntityPlayer player) {
        return false;
    }

    public boolean isListable(Entity entity, EntityPlayer player) {
        return false;
    }

    // Carrot
    public boolean isTameable(Entity entity, EntityPlayer player) {
        return false;
    }

    public int tame(Entity entity, EntityPlayer player) {
        return 0;
    }

    public boolean isAgeable(Entity entity, EntityPlayer player) {
        return false;
    }

    public int age(Entity entity, EntityPlayer player) {
        return 0;
    }

    public boolean isHealable(Entity entity, EntityPlayer player) {
        return false;
    }

    public int heal(Entity entity, EntityPlayer player) {
        return 0;
    }

    // Not currently implemented
    public boolean isBreedable(Entity entity, EntityPlayer player) {
        return false;
    }

    public int breed(Entity entity, EntityPlayer player) {
        return 0;
    }

    public boolean isLoaded() {
        return false;
    }

    public boolean isMyMod(Entity entity) {
        return false;
    }

    public ITextComponent getResponseKey(Entity entity, EntityPlayer player) {
        return null;
    }

    public String proxyName() {
        return "dummy";
    }
}
