package com.cdhoff.murdermystery;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.Mods.MurderMystery;
import com.cdhoff.murdermystery.Modules.utils.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber
public class RenderManager extends Gui {

    private Minecraft mc;

    public RenderManager(Minecraft mc){
        super();
        this.mc = mc;
    }

    @SubscribeEvent
    public void renderScreen(RenderGameOverlayEvent.Post event) {
        if (event.isCancelable() || event.getType() != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        render(mc);
    }

    public void render(Minecraft minecraft) {
        drawClientOverlay(minecraft);
    }

    public static void drawClientOverlay(Minecraft minecraft) {

        if(!minecraft.gameSettings.showDebugInfo) {

            //Renders client name on top left
        minecraft.fontRenderer.drawStringWithShadow(Main.NAME, 2, 2, Color.WHITE.getRGB());

        //Renders each Mod that is enabled on top right
        int i = 0;

        ScaledResolution sr = new ScaledResolution(minecraft);

            for (Mod m : Client.getInstance().getModloader().getModlist().values()) {
                if (m.isState()) {
                    int offset = (minecraft.fontRenderer.FONT_HEIGHT * i);

                    minecraft.fontRenderer.drawStringWithShadow(m.getName(),
                            sr.getScaledWidth() - minecraft.fontRenderer.getStringWidth(m.getName()) - 2,
                            2 + offset, Color.WHITE.getRGB());
                    i += 1;
                }
            }
        }

    }

    public static void drawMurderMystery(Minecraft minecraft){
        ScaledResolution sr = new ScaledResolution(minecraft);
        if(!minecraft.gameSettings.showDebugInfo) {

            minecraft.fontRenderer.drawStringWithShadow("Murderer: " + MurderMystery.formatString(MurderMystery.murderDisplay), 2, minecraft.fontRenderer.FONT_HEIGHT + 2, Color.WHITE.getRGB());

            int offset = 0;

            for (String name : MurderMystery.murderers) {
                for (NetworkPlayerInfo data : minecraft.player.connection.getPlayerInfoMap()) {
                    if (data.getGameProfile().getName().equals(name)) {

                        Minecraft.getMinecraft().getTextureManager().bindTexture(data.getLocationSkin());
                        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(2 + offset, (minecraft.fontRenderer.FONT_HEIGHT * 2) + 2, 32, 32, 32, 32);
                        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(2 + offset, (minecraft.fontRenderer.FONT_HEIGHT * 2) + 2, 160, 32, 32, 32);
                        offset += 34;
                    }
                }
            }
        }


        //Gui.drawModalRectWithCustomSizedTexture(2, (minecraft.fontRenderer.FONT_HEIGHT * 2) + 2, 0, 0, 64, 64, GL_TEXTURE_WIDTH, GL_TEXTURE_HEIGHT);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);


    }

    public static void drawRect(final float x, final float y, final float x2, final float y2, final Color color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        blendFunc(GL11.GL_SRC_ALPHA, GL11. GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), 0.8f);
        GlStateManager.glBegin(GL11.GL_QUADS);
        glVertex2d(x2, y);
        glVertex2d(x, y);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);
        GlStateManager.glEnd();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        GlStateManager.glEnd();
        GlStateManager.disableTexture2D();
        GlStateManager.disableBlend();
        glDisable(GL_LINE_SMOOTH);
        GlStateManager.popMatrix();

    }

    public static void drawEntityBox(final Entity entity, Color color, final boolean outline, RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        final net.minecraft.client.renderer.entity.RenderManager renderManager = mc.getRenderManager();

        disableLighting();
        disableDepth();
        disableTexture2D();
        enableBlend();
        blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        double renderPosX = mc.getRenderManager().viewerPosX;
        double renderPosY = mc.getRenderManager().viewerPosY;
        double renderPosZ = mc.getRenderManager().viewerPosZ;
        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.getPartialTicks()
                - renderPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.getPartialTicks()
                - renderPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.getPartialTicks()
                - renderPosZ;

        final AxisAlignedBB entityBox = entity.getEntityBoundingBox();
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                entityBox.minX - entity.posX + x - 0.05D,
                entityBox.minY - entity.posY + y,
                entityBox.minZ - entity.posZ + z - 0.05D,
                entityBox.maxX - entity.posX + x + 0.05D,
                entityBox.maxY - entity.posY + y + 0.15D,
                entityBox.maxZ - entity.posZ + z + 0.05D
        );

        /*if (outline) {
            GlStateManager.glLineWidth(1F);
            glEnable(GL_LINE_SMOOTH);
//            color(color.getRed(), color.getGreen(), color.getBlue(), 0.95f);
            RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        }*/

        color(color.getRed(), color.getGreen(), color.getBlue(), ConfigManager.ESP.espOpacity);
        drawFilledBox(axisAlignedBB);
        disableBlend();
        enableTexture2D();
        enableDepth();
        enableLighting();
    }

    public static void drawFilledBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        tessellator.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
    }


    public static void drawOutlinedBox(AxisAlignedBB bb)
    {
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
    }

    public static void drawSolidBox(AxisAlignedBB bb)
    {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
    }

    public static void applyCameraRotationOnly()
    {
        Entity player = Minecraft.getMinecraft().getRenderViewEntity();
        GL11.glRotated(MathHelper.wrapDegrees(player.getPitchYaw().x+180), 1, 0, 0);
        GL11.glRotated(MathHelper.wrapDegrees(player.getPitchYaw().y+180), 0, 1,
                0);
    }

    public static Vec3d getCameraPos(RenderWorldLastEvent e)
    {
        return Minecraft.getMinecraft().player.getPositionEyes(e.getPartialTicks());
    }

}
