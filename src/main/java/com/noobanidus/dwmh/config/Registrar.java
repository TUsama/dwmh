package com.noobanidus.dwmh.config;

import com.noobanidus.dwmh.items.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class Registrar {
    public static ItemWhistle whistle = null;
    public static ItemEnchantedCarrot carrot = null;

    public static void preInit() {
        whistle = new ItemWhistle();
        whistle.init();

        carrot = new ItemEnchantedCarrot();
        carrot.init();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(whistle);
        if (ItemEnchantedCarrot.enabled) {
            event.getRegistry().register(carrot);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(whistle, 0, new ModelResourceLocation("dwmh:whistle", "inventory"));
        if (ItemEnchantedCarrot.enabled) {
            ModelLoader.setCustomModelResourceLocation(carrot, 1, new ModelResourceLocation("dwmh:carrot", "type=normal"));
            ModelLoader.setCustomModelResourceLocation(carrot, 0, new ModelResourceLocation("dwmh:carrot", "type=broken"));
        }
    }
}
