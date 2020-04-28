package com.cdhoff.murdermystery.Modules.utils;

import com.cdhoff.murdermystery.Client;
import com.cdhoff.murdermystery.Modules.Mod;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.HashMap;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber
public class EventHandler {

    public EventHandler(){        System.out.print("EventHandler");
    }

    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled=true)
    public static void onEvent(InputEvent.KeyInputEvent event)
    {
        //KeyBinding[] keyBindings = ClientProxy.keyBindings;

        /*if(keyBindings[0].isPressed()){
            Client.getInstance().getModloader().getModlist().get(ModType.ESP).toggle();
        }
        if(keyBindings[1].isPressed()){
            Client.getInstance().getModloader().getModlist().get(ModType.MURDERERAUTOSEND).toggle();
        }
        if(keyBindings[2].isPressed()){
            Client.getInstance().getModloader().getModlist().get(ModType.MURDERMYSTERY).toggle();
        }
        if(keyBindings[3].isPressed()){
            Client.getInstance().getModloader().getModlist().get(ModType.NAMETAGS).toggle();
        }
        if(keyBindings[4].isPressed()){
            Client.getInstance().getModloader().getModlist().get(ModType.TRACERS).toggle();
        }
        if(keyBindings[5].isPressed()){
            Client.getInstance().getModloader().getModlist().get(ModType.TRUESIGHT).toggle();
        }
        if(keyBindings[6].isPressed()){
            Client.getInstance().getModloader().getModlist().get(ModType.WALLHACK).toggle();
        }*/

        HashMap<Mod, KeyBinding> keyBindings = ClientProxy.keyBindings;

        for(Mod m : Client.getInstance().getModloader().getModlist().values()){
            if(keyBindings.get(m).isPressed()){
                m.toggle();
            }
        }


        /*for(int i=0; i<Client.getInstance().getModloader().getModlist().size(); i++){
            if(keyBindings[i].isPressed()){
                Client.getInstance().getModloader().getModlist().get(i).toggle();
            }
        }*/

    }

}
