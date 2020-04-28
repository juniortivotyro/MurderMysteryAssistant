package com.cdhoff.murdermystery.Modules.utils;

import com.cdhoff.murdermystery.Client;
import com.cdhoff.murdermystery.Modules.Mod;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientProxy {

    //public static KeyBinding[] keyBindings;

    public static HashMap<Mod, KeyBinding> keyBindings = new HashMap<Mod, KeyBinding>();

    static ArrayList<String> temp = new ArrayList<String>();
    static ArrayList<Integer> keys = new ArrayList<Integer>();

    public static void init() {

        //keyBindings = new KeyBinding[Client.getInstance().getModloader().getModlist().size()];

        for(Mod m : Client.getInstance().getModloader().getModlist().values()){
            keyBindings.put(m, new KeyBinding("key." + m.getNameForConfig()+".toggle",m.getKeybind(),"key.murdermystery.category"));
            ClientRegistry.registerKeyBinding(keyBindings.get(m));
        }

        /*keyBindings[0] = new KeyBinding("key.esp.toggle", Keyboard.KEY_NONE, "key.murdermystery.category");
        keyBindings[1] = new KeyBinding("key.murdererautosend.toggle", Keyboard.KEY_NONE, "key.murdermystery.category");
        keyBindings[2] = new KeyBinding("key.murdermystery.toggle", Keyboard.KEY_NONE, "key.murdermystery.category");
        keyBindings[3] = new KeyBinding("key.nametags.toggle", Keyboard.KEY_NONE, "key.murdermystery.category");
        keyBindings[4] = new KeyBinding("key.tracers.toggle", Keyboard.KEY_NONE, "key.murdermystery.category");
        keyBindings[5] = new KeyBinding("key.truesight.toggle", Keyboard.KEY_NONE, "key.murdermystery.category");
        keyBindings[6] = new KeyBinding("key.wallhack.toggle", Keyboard.KEY_NONE, "key.murdermystery.category");
         */



        /*for(Mod m : Client.getInstance().getModloader().getModlist().values()){
            temp.add(m.getNameForConfig());
            keys.add(m.getKeybind());

            System.out.print(m.getID() + " " + m.getKeybind());
        }

        keyBindings = new KeyBinding[temp.size()];

        for(int i=0; i< temp.size(); i++){
            keyBindings[i] = new KeyBinding("key." + temp.get(i) + ".toggle", keys.get(i), "key.murdermystery.category");
        }*/
    }

}
