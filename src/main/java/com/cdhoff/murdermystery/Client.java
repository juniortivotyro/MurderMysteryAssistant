package com.cdhoff.murdermystery;

import com.cdhoff.murdermystery.Modules.ModLoader;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Client {
    private static Main main;
    private static Client instance;

    private ModLoader modloader = new ModLoader();

    public Client(Main main) {
        this.main = main;
        instance = this;
        System.out.print("Client");
    }




    public void initialize() {
        modloader.initialize();
    }

    public ModLoader getModloader() {
        return modloader;
    }

    public static Client getInstance() {
        return instance;
    }

}
