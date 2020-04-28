package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

public class NameTags extends Mod {
    public NameTags() {
        super("NameTags", "Shows cool nametags", ModType.NAMETAGS, Keyboard.KEY_NEXT);
    }

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {
            RenderPlayer.NAME_TAG_RANGE = Integer.MAX_VALUE;
    }

    @Override
    public void onDraw(RenderGameOverlayEvent.Post event) {

    }

    Minecraft mc = Minecraft.getMinecraft();
    @Override
    public void onWorldRender(RenderWorldLastEvent e) {
        /*for(EntityPlayer player : Minecraft.getMinecraft().world.playerEntities) {
            RenderManager.drawNamePlate(Minecraft.getMinecraft().fontRenderer, player.getName(), (float) player.posX, (float) player.posY, (float) player.posZ, (int) player.height + 1, Minecraft.getMinecraft().getRenderManager().playerViewX, Minecraft.getMinecraft().getRenderManager().playerViewY, tpv);
        }*/
        for(EntityPlayer player : Minecraft.getMinecraft().world.playerEntities) {


            if (mc.player != player) {

                double renderPosX = mc.getRenderManager().viewerPosX;
                double renderPosY = mc.getRenderManager().viewerPosY;
                double renderPosZ = mc.getRenderManager().viewerPosZ;

                RenderManager renderManager = mc.getRenderManager();

                pushMatrix();
                glTranslated( // Translate to player position with render pos and interpolate it
                        player.lastTickPosX + (player.posX - player.lastTickPosX) * e.getPartialTicks() - renderPosX,
                        player.lastTickPosY + (player.posY - player.lastTickPosY) * e.getPartialTicks() -  renderPosY + player.eyeHeight + 0.55,
                        player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * e.getPartialTicks() - renderPosZ
                );

                rotate(-mc.getRenderManager().playerViewY, 0f, 1f, 0f);
                rotate(mc.getRenderManager().playerViewX, 1f, 0f, 0f);

                float distance = mc.player.getDistance(player) / 4F;
                String text;

                if(player.getHealth() < 6) {
                    text = player.getDisplayName().getFormattedText() + " " + TextFormatting.RED + Math.round(player.getHealth()) + "HP";
                }else if(player.getHealth() < 15) {
                    text = player.getDisplayName().getFormattedText() + " " + TextFormatting.YELLOW + Math.round(player.getHealth()) + "HP";
                }else{
                    text = player.getDisplayName().getFormattedText() + " " + TextFormatting.GREEN + Math.round(player.getHealth()) + "HP";
                }

                if(distance < 2F) distance = 2F;

                float scale = distance/100F * 1F;

                glScalef(-scale, -scale, scale);

                disableLighting();
                disableDepth();
                enableTexture2D();
                enableBlend();
                blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


                int width = mc.fontRenderer.getStringWidth(text) / 2;

                //com.cdhoff.murdermystery.RenderManager.drawRect(-width - 2f, width + 4f, mc.fontRenderer.FONT_HEIGHT, 2f, 0x56565680);
                com.cdhoff.murdermystery.RenderManager.drawRect(-width - 2F, -2F, width+4F, mc.fontRenderer.FONT_HEIGHT + 2f, Color.BLACK);
                mc.getRenderManager().getFontRenderer().drawString(text, 1F + -width, 1f, 0xFFFFFF, true);
                GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
                RenderHelper.enableStandardItemLighting();
                disableBlend();
                enableTexture2D();
                enableDepth();
                enableLighting();
                popMatrix();
            }
        }
    }

    @Override
    public void onPlayerDamageBlock(PlayerInteractEvent.LeftClickBlock event) {

    }

    @Override
    public void onRenderLiving(RenderLivingEvent.Specials.Pre event){
        if(event.getEntity() instanceof EntityPlayer){
            event.setCanceled(true);
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
