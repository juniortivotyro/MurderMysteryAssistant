package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import com.cdhoff.murdermystery.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ESP extends Mod {

    public ESP() {
        super("ESP", "Player ESP",ModType.ESP, Keyboard.KEY_MINUS);
    }

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {

    }

    @Override
    public void onDraw(RenderGameOverlayEvent.Post event) {

    }

    @Override
    public void onWorldRender(RenderWorldLastEvent e) {
        for(EntityPlayer player : Minecraft.getMinecraft().world.playerEntities){
            if(player != Minecraft.getMinecraft().player) {
                if (MurderMystery.murderers.contains(player.getName())) {
                    RenderManager.drawEntityBox(player, Color.RED, true, e);
                } else if (player.getName().equals("Wacey_Fri") || player.getName().equals("Your_Daddy_Pepe") || player.getName().equals("PinkIsTheFlavour")) {

                    RenderManager.drawEntityBox(player, Color.GREEN, true, e);
                } else {
                    RenderManager.drawEntityBox(player, Color.BLUE, true, e);
                }
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

    }
}
