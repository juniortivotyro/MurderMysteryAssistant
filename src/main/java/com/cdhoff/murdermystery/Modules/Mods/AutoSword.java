package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;

public class AutoSword extends Mod {
    public AutoSword() {
        super("AutoSword", "", ModType.AUTOSWORD, Keyboard.KEY_NONE);
    }

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {
        if(Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY)
        {
            Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;

            if(entity instanceof EntityPlayer
                    && ((EntityPlayer)entity).getHealth() > 0)
                setSlot();
        }
    }

    public void setSlot()
    {
        // find best weapon
        float bestValue = Integer.MIN_VALUE;
        int bestSlot = -1;
        for(int i = 0; i < 9; i++)
        {
            // skip empty slots
            if(Minecraft.getMinecraft().player.inventory.getStackInSlot(i).isEmpty())
                continue;

            Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();

            // get damage
            float value = 0;
            if(item instanceof ItemSword) {
                value = ((ItemSword) item).getAttackDamage();
            }

            // compare with previous best weapon
            if(value > bestValue)
            {
                bestValue = value;
                bestSlot = i;
            }
        }

        // check if any weapon was found
        if(bestSlot == -1)
            return;

        // save old slot
//        if(oldSlot == 0)
//            oldSlot = Minecraft.getMinecraft().player.inventory.currentItem;

        // set slot
        Minecraft.getMinecraft().player.inventory.currentItem = bestSlot;
    }

    @Override
    public void onDraw(RenderGameOverlayEvent.Post event) {

    }

    @Override
    public void onWorldRender(RenderWorldLastEvent e) {

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
