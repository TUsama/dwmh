package com.noobanidus.dwmh.proxy.steeds;

import com.lying.variousoddities.entity.mount.AbstractMount;
import com.lying.variousoddities.entity.mount.EntityChestPegasus;
import com.lying.variousoddities.entity.mount.EntityPegasus;
import com.noobanidus.dwmh.DWMH;
import com.noobanidus.dwmh.config.DWMHConfig;
import com.noobanidus.dwmh.wrappers.VaroddWrapper;
import com.teammetallurgy.atum.entity.animal.EntityCamel;
import com.teammetallurgy.atum.entity.animal.EntityDesertWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

@SuppressWarnings("unused")
public class VaroddProxy implements ISteedProxy {
    public boolean isTeleportable(Entity entity, EntityPlayer player) { // TODO
        if (!isListable(entity, player)) {
            return false;
        }

        VaroddWrapper wrapper = new VaroddWrapper(entity);

        return wrapper.isHorseSaddled() && globalTeleportCheck(wrapper, player);
    }

    public boolean isListable(Entity entity, EntityPlayer player) {
        if (!isMyMod(entity)) return false;

        VaroddWrapper wrapper = new VaroddWrapper(entity);

        if (wrapper.isChild() || !wrapper.isTame() || wrapper.dimension != player.dimension) {
            return false;
        }

        return wrapper.getOwnerUniqueId() != null && wrapper.getOwnerUniqueId().equals(player.getUniqueID());
    }

     public boolean globalTeleportCheck(VaroddWrapper wrapper, EntityPlayer player) {
        if (wrapper.getLeashed() || (wrapper.isBeingRidden() && wrapper.isRidingSameEntity(player))) {
            return false;
        }

        // And prevent you from summoning wrappers being ridden by other players
        if (wrapper.isBeingRidden() && !DWMHConfig.Ocarina.otherRiders) {
            return false;
        }

        // Compatibility for Horse Power device-attached wrappers
        if (wrapper.hasHome() && wrapper.world.getTileEntity(wrapper.getHomePosition()) != null) {
            return false;
        }

        return true;
    }

    // Carrot
    public boolean isTameable(Entity entity, EntityPlayer player) {
        if (!isMyMod(entity)) return false;

        VaroddWrapper wrapper = new VaroddWrapper(entity);

        if (wrapper.isGryphon()) return false;

        if (wrapper.isChild()) return false;

        return !wrapper.isTame();
    }

    public void tame(Entity entity, EntityPlayer player) { // TODO
        VaroddWrapper wrapper = new VaroddWrapper(entity);

        if (wrapper.isGryphon()) return;

        wrapper.setTamedBy(player);

        if (DWMHConfig.EnchantedCarrot.messages.taming) {
            doGenericMessage(entity, player, Generic.TAMING);
        }
    }

    public boolean isAgeable(Entity entity, EntityPlayer player) { // TODO
        if (!isMyMod(entity)) return false;

        return new VaroddWrapper(entity).ageable();
    }

    public void age(Entity entity, EntityPlayer player) { // TODO
        VaroddWrapper wrapper = new VaroddWrapper(entity);

        if (!wrapper.ageable()) return;

        wrapper.setGrowingAge(0);
        wrapper.world.setEntityState(entity, (byte) 7);

        if (DWMHConfig.EnchantedCarrot.messages.aging) {
            doGenericMessage(entity, player, Generic.AGING);
        }
    }

    public boolean isBreedable(Entity entity, EntityPlayer player) {
        if (!isMyMod(entity)) return false;

        VaroddWrapper wrapper = new VaroddWrapper(entity);

        if (wrapper.isGryphon()) return false;

        return !wrapper.isChild() && wrapper.getGrowingAge() == 0 && !wrapper.isInLove();
    }

    public void breed(Entity entity, EntityPlayer player) {
        if (!isMyMod(entity)) return;

        VaroddWrapper wrapper = new VaroddWrapper(entity);
        wrapper.setInLove(player);

        if (DWMHConfig.EnchantedCarrot.messages.breeding) {
            doGenericMessage(entity, player, Generic.BREEDING);
        }
    }

    public boolean isMyMod(Entity entity) {
        return entity instanceof AbstractMount || entity instanceof EntityPegasus || entity instanceof EntityChestPegasus;
    }

    public ITextComponent getResponseKey(Entity entity, EntityPlayer player) {
        if (!isMyMod(entity)) return null;

        VaroddWrapper wrapper = new VaroddWrapper(entity);

        ITextComponent temp;

        if (wrapper.hasHome() && wrapper.world.getTileEntity(wrapper.getHomePosition()) != null) {
            temp = new TextComponentTranslation("dwmh.strings.unsummonable.working");
            temp.getStyle().setColor(TextFormatting.DARK_RED);
        } else if (wrapper.getLeashed()) {
            temp = new TextComponentTranslation("dwmh.strings.unsummonable.leashed");
            temp.getStyle().setColor(TextFormatting.DARK_RED);
        } else if (!wrapper.isHorseSaddled()) {
            temp = new TextComponentTranslation("dwmh.strings.unsummonable.unsaddled");
            temp.getStyle().setColor(TextFormatting.DARK_RED);
        } else if (wrapper.isBeingRidden() && wrapper.isRidingSameEntity(player)) {
            temp = new TextComponentTranslation("dwmh.strings.unsummonable.ridden");
            temp.getStyle().setColor(TextFormatting.DARK_RED);
        } else if (wrapper.isBeingRidden() && !DWMHConfig.Ocarina.otherRiders) {
            temp = new TextComponentTranslation("dwmh.strings.unsummonable.ridden_other");
            temp.getStyle().setColor(TextFormatting.DARK_RED);
        } else if (wrapper.isBeingRidden() && DWMHConfig.Ocarina.otherRiders) {
            temp = new TextComponentTranslation("dwmh.strings.summonable.ridden_other");
            temp.getStyle().setColor(TextFormatting.DARK_AQUA);
        } else {
            temp = new TextComponentTranslation("dwmh.strings.summonable");
            temp.getStyle().setColor(TextFormatting.AQUA);
        }

        return temp;
    }

}