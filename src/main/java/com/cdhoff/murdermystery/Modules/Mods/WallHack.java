package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class WallHack extends Mod {
    public WallHack() {
        super("WallHack", "Show players through walls", ModType.WALLHACK, Keyboard.KEY_HOME);
    }

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {


    }

    @Override
    public void onDraw(RenderGameOverlayEvent.Post event) {

    }

    @Override
    public void onWorldRender(RenderWorldLastEvent e) {

        GlStateManager.pushMatrix();
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        for(Entity entity : Minecraft.getMinecraft().world.loadedEntityList){
            if(entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().player) {
                Minecraft.getMinecraft().getRenderManager().renderEntityStatic(entity, e.getPartialTicks(), false);
            }
            if(entity instanceof EntityItem) {
                Minecraft.getMinecraft().getRenderManager().renderEntityStatic(entity, e.getPartialTicks(), false);
            }
        }
        GlStateManager.popMatrix();
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
