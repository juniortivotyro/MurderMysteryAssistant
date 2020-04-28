package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class TrueSight extends Mod {

    public TrueSight() {
        super("TrueSight", "See Invisible Entities", ModType.TRUESIGHT, Keyboard.KEY_NONE);
    }

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {

    }

    @Override
    public void onDraw(RenderGameOverlayEvent.Post event) {

    }

    ArrayList<String> players = new ArrayList<String>();

    @Override
    public void onWorldRender(RenderWorldLastEvent e) {
        for(Entity entity : Minecraft.getMinecraft().world.loadedEntityList){
            if(entity.isInvisible() && entity.getDisplayName().toString() != null) {
                players.add(entity.getDisplayName().toString());
                entity.setInvisible(false);
            }
        }
    }

    @Override
    public void onPlayerDamageBlock(PlayerInteractEvent.LeftClickBlock event) {

    }

    @Override
    public void onRenderLiving(RenderLivingEvent.Specials.Pre event) {

    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        for(Entity entity : Minecraft.getMinecraft().world.loadedEntityList){
            if(players.contains(entity.getDisplayName().toString()))
                entity.setInvisible(true);
        }
        players.clear();
    }
}
