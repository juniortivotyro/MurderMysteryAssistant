package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import com.cdhoff.murdermystery.Modules.utils.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Tracers extends Mod {

    public Tracers() {
        super("Tracers", "Draw lines to players", ModType.TRACERS, Keyboard.KEY_INSERT);
    }

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {

    }

    @Override
    public void onWorldRender(RenderWorldLastEvent e) {
        Minecraft mc = Minecraft.getMinecraft();


        for (EntityPlayer player : mc.world.playerEntities) {
            if (mc.player != player) {


                float distance = mc.player.getDistance(player);
                double renderPosX = mc.getRenderManager().viewerPosX;
                double renderPosY = mc.getRenderManager().viewerPosY;
                double renderPosZ = mc.getRenderManager().viewerPosZ;
                double xPos = (player.lastTickPosX + (player.posX - player.lastTickPosX)) - renderPosX;
                double yPos = (player.lastTickPosY + (player.posY - player.lastTickPosY)) + player.height / 2.0f - renderPosY;
                double zPos = (player.lastTickPosZ + (player.posZ - player.lastTickPosZ)) - renderPosZ;

//                System.out.println("Player Name: " + player.getName() + " , renderPosX,Y,Z: " + renderPosX + ", " + "renderPosY, " + renderPosZ + " xPos, yPos, zPos: " + xPos + " " + yPos + " " + zPos);

                Vec3d eyes = new Vec3d(0, 0, 1).rotatePitch(-(float) Math.toRadians(mc.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(mc.player.rotationYaw));


                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.glLineWidth(ConfigManager.Tracers.tracerThickness);
                GlStateManager.disableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBuffer();
                glEnable(GL_LINE_SMOOTH);
                buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);

                if(MurderMystery.murderers.contains(player.getName())){
                    GlStateManager.glLineWidth(ConfigManager.MurderMystery.murdererTracerThickness);
                    buffer.pos(xPos, yPos, zPos).color(0.0f, 0.0f, 1.0f, ConfigManager.Tracers.tracerOpacity).endVertex();
                    buffer.pos(eyes.x, mc.player.getEyeHeight() + eyes.y, eyes.z).color(0.0F, 0.0f, 1.0f, ConfigManager.Tracers.tracerOpacity).endVertex();
                }else if(distance <= 3F){
                    buffer.pos(xPos, yPos, zPos).color(1.0f, 0.0f, 0.0f, ConfigManager.Tracers.tracerOpacity).endVertex();
                    buffer.pos(eyes.x, mc.player.getEyeHeight() + eyes.y, eyes.z).color(1.0f, 0.0f, 0.0f, ConfigManager.Tracers.tracerOpacity).endVertex();
                } else if(distance <= 20F){
                    buffer.pos(xPos, yPos, zPos).color(1.0f, (distance / 20F), 0.0f, ConfigManager.Tracers.tracerOpacity).endVertex();
                    buffer.pos(eyes.x, mc.player.getEyeHeight() + eyes.y, eyes.z).color(1.0f, (distance / 20F), 0.0f, ConfigManager.Tracers.tracerOpacity).endVertex();
                } else if(distance > 20F){
                    buffer.pos(xPos, yPos, zPos).color(0.0f, 1.0f, 0.0f, ConfigManager.Tracers.tracerOpacity).endVertex();
                    buffer.pos(eyes.x, mc.player.getEyeHeight() + eyes.y, eyes.z).color(0.0f, 1.0f, 0.0f, ConfigManager.Tracers.tracerOpacity).endVertex();
                }

                tessellator.draw();

                GlStateManager.disableBlend();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
                GlStateManager.enableDepth();
                GlStateManager.glLineWidth(1.0F);
                GlStateManager.popMatrix();
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
    public void onDraw(RenderGameOverlayEvent.Post event) {


    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
