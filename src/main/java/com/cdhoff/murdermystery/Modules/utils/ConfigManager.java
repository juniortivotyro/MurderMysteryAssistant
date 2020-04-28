package com.cdhoff.murdermystery.Modules.utils;

import com.cdhoff.murdermystery.Main;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Main.MODID)
@Config.LangKey("murdermystery.config.title")
public class ConfigManager {

    @Config.LangKey("config.murdermystery.category_murdermystery")
    public static murderMystery MurderMystery = new murderMystery();

    @Config.LangKey("config.murdermystery.category_tracers")
    public static tracers Tracers = new tracers();

    @Config.LangKey("config.murdermystery.category_esp")
    public static esp ESP = new esp();

    public static class murderMystery{
        @Config.LangKey("config.murdermystery.detectableitems")
        @Config.Comment("Detectable Items")
        public String[] detectableItems = new String[]{"sword"};

        @Config.LangKey("config.murdermystery.murdererupdatefrequency")
        @Config.Comment("Murderer Update Frequency (1 - 20x/sec, 20 = 1x/sec)")
        public int murdererUpdateFrequency = 1;

        @Config.LangKey("config.murdermystery.murderertracerthickness")
        @Config.Comment("Murderer Tracer Thickness")
        public float murdererTracerThickness = 3.0f;
    }

    public static class tracers{
        @Config.LangKey("config.murdermystery.traceropacity")
        @Config.Comment("Opacity of Tracers")
        public float tracerOpacity = 1.0f;

        @Config.LangKey("config.murdermystery.tracerthickness")
        @Config.Comment("Tracer Thickness")
        public float tracerThickness = 1.0f;
    }

    public static class esp{
        @Config.LangKey("config.murdermystery.espopacity")
        @Config.Comment("Opacity of ESP")
        public float espOpacity = 1.0f;
    }



    @Mod.EventBusSubscriber(modid = Main.MODID)
    private static class EventHandler{
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event){
            if(event.getModID().equals(Main.MODID)){
                net.minecraftforge.common.config.ConfigManager.sync(Main.MODID, Config.Type.INSTANCE);
            }
        }
    }


}
