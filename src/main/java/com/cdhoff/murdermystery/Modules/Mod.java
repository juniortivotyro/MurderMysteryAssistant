package com.cdhoff.murdermystery.Modules;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public abstract class Mod {


    private ModType type;
    private String name, description;
    private int keybind;
    private boolean state;
    private String string;

    /**
     * Set keybind to -1 for no keybind
     *
     * @param name
     * @param description
     * @param type
     * @param keybind
     */
    public Mod(String name, String description, ModType type, int keybind) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.keybind = keybind;
        //this.keybind = Client.getSetting().getInt(this.getID() + "_bind", keybind);
        //this.keybind = Main.config.getInt(this.getID() + "_bind", Configuration.CATEGORY_GENERAL, keybind, -1, Integer.MAX_VALUE, "Keybind for Mod");
    }

    /**
     * Saves state+keybind to config
     */
    private void save() {
        /*Client.getSetting().saveInt(this.getID() + "_bind", keybind);
        Client.getSetting().saveBool(this.getID() + "_state", state);
        Client.getSetting().saveString(this.getID() + "_string", string);

         */


    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public ModType getType() {
        return type;
    }

    public void setType(ModType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getString() { return string;}

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
        save();
    }

    /**
     * Returns this mods unique ID
     */
    public String getID() {
        return this.name.toLowerCase().replace(" ", "_") + this.type.name().toLowerCase().replace(" ", "_");
    }
    public String getNameForConfig() {
        return this.name.toLowerCase().replace(" ", "_");
    }

    public void toggle() {
        this.state = !state;
        //save();
        //IChat.sendClientMessage(this.getName() + " is now " + (state ? "enabled" : "disabled"));
        if (state) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    /*
     * Handlers
     */

    public abstract void onEvent(LivingEvent.LivingUpdateEvent event);

    public abstract void onDraw(RenderGameOverlayEvent.Post event);

    public abstract void onWorldRender(RenderWorldLastEvent e);

    public abstract void onPlayerDamageBlock(PlayerInteractEvent.LeftClickBlock event);

    public abstract void onRenderLiving(RenderLivingEvent.Specials.Pre event);

    public abstract void onEnable();

    public abstract void onDisable();



}
