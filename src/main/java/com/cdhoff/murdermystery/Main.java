package com.cdhoff.murdermystery;

import com.cdhoff.murdermystery.Modules.ModLoader;
import com.cdhoff.murdermystery.Modules.utils.ClientProxy;
import com.cdhoff.murdermystery.Modules.utils.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{


    public static final String MODID = "murdermystery";
    public static final String NAME = "Murder Mystery Assistant";
    public static final String VERSION = "1.0";
    public static Logger logger = LogManager.getLogger();;
    public static Configuration config;

    public static List<String> detectableItems = new ArrayList<String>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new Client(this));
        MinecraftForge.EVENT_BUS.register(new RenderManager(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(new ModLoader());

        Client.getInstance().getModloader().initialize();

        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        MinecraftForge.EVENT_BUS.register(new ConfigManager());
    }

    @SubscribeEvent
    public void onUpdate(LivingUpdateEvent event){
        Client.getInstance().getModloader().handleUpdateEvent(event);
    }

    @SubscribeEvent
    public void onDraw(RenderGameOverlayEvent.Post event){
        Client.getInstance().getModloader().handleDrawEvent(event);
        RenderManager.drawClientOverlay(Minecraft.getMinecraft());
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event){
        Client.getInstance().getModloader().handleWorldRenderEvent(event);
    }

    /*@SubscribeEvent(priority = EventPriority.LOWEST)
    public void onKeyEvent(InputEvent.KeyInputEvent event){
        Client.getInstance().getModloader().handleToggleEvent(event);
    }*/

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ClientProxy.init();

    }
}
