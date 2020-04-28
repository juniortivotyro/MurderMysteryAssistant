package com.cdhoff.murdermystery.Modules;

import com.cdhoff.murdermystery.Client;
import com.cdhoff.murdermystery.Modules.Mods.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.TreeMap;

public class ModLoader {

    private TreeMap<ModType, Mod> modlist = new TreeMap<>();

    public void initialize() {
        modlist.put(ModType.ESP, new ESP());
        modlist.put(ModType.FULLBRIGHT, new Fullbright());
        modlist.put(ModType.MURDERMYSTERY, new MurderMystery());
        modlist.put(ModType.MURDERERAUTOSEND, new MurdererAutoSend());
        modlist.put(ModType.NAMETAGS, new NameTags());
        modlist.put(ModType.TRACERS, new Tracers());
        modlist.put(ModType.TRUESIGHT, new TrueSight());
        modlist.put(ModType.WALLHACK, new WallHack());
        modlist.put(ModType.AUTOTOOL, new AutoTool());
        modlist.put(ModType.AUTOSWORD, new AutoSword());
    }

    public TreeMap<ModType, Mod> getModlist() {
        return modlist;
    }

    public Mod getMod(ModType mod) {
        return modlist.get(mod);
    }

    public Mod getMod(String mod) {
        for (Mod m : modlist.values()) {
            if (m.getName().toLowerCase().equals(mod.toLowerCase())) {
                return m;
            }
        }
        return null;
    }

    /*@SubscribeEvent(priority = EventPriority.LOWEST)
    public void handleToggleEvent(InputEvent.KeyInputEvent event) {

        //EventKeyAction keypress = (EventKeyAction) event;

        Logger.getLogger(Main.NAME).info(String.valueOf(Keyboard.getEventKey()) + " has been pressed.");

        for (Mod m : modlist.values()) {
            Logger.getLogger(Main.NAME).info(m.getName() + " keybind is " + m.getKeybind());


            if (Keyboard.getEventKey() == m.getKeybind()) {
                Logger.getLogger(Main.NAME).info(m.getName() + " keybind has been pressed");

                m.toggle();
            }
        }
    }*/

    @SubscribeEvent
    public void handleUpdateEvent(LivingEvent.LivingUpdateEvent event) {

        for(Mod m : Client.getInstance().getModloader().getModlist().values()) {
            if (m.isState()) {
                m.onEvent(event);
            }
        }

    }

    @SubscribeEvent
    public void handleDrawEvent(RenderGameOverlayEvent.Post event) {
        for(Mod m : Client.getInstance().getModloader().getModlist().values()) {
            if (m.isState()) {
                m.onDraw(event);
            }
        }
    }

    @SubscribeEvent
    public void handleWorldRenderEvent(RenderWorldLastEvent event) {
        for(Mod m : Client.getInstance().getModloader().getModlist().values()) {
            if (m.isState()) {
                m.onWorldRender(event);
            }
        }
    }

    @SubscribeEvent
    public void handleRenderLivingSpecialPreEvent(RenderLivingEvent.Specials.Pre event){
        for(Mod m : Client.getInstance().getModloader().getModlist().values()) {
            if (m.isState()) {
                m.onRenderLiving(event);
            }
        }
    }

    @SubscribeEvent
    public void handleBlockBreakEvent(PlayerInteractEvent.LeftClickBlock event){
        for(Mod m:Client.getInstance().getModloader().getModlist().values()) {
            if (m.isState()){
                m.onPlayerDamageBlock(event);
            }
        }
    }

}
