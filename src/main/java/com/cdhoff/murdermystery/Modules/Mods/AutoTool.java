package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Main;
import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import com.cdhoff.murdermystery.Modules.utils.BlockUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class AutoTool extends Mod {
    public AutoTool() {
        super("AutoTool", "Automatically switches tools", ModType.AUTOTOOL, 0x00);
    }

    private int timer;
    private int prevSelectedSlot;

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {
//        if(Minecraft.getMinecraft().objectMouseOver != null && Minecraft.getMinecraft().objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY)
//        {
//            Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
//
//            if(entity instanceof EntityPlayer
//                    && ((EntityPlayer)entity).getHealth() > 0)
//                setSlot();
//        }

        // update timer
        if(timer > 0)
        {
            timer--;
            return;
        }

    }

    @Override
    public void onDraw(RenderGameOverlayEvent.Post event) {

    }

    @Override
    public void onWorldRender(RenderWorldLastEvent e) {

    }

    @Override
    public void onRenderLiving(RenderLivingEvent.Specials.Pre event) {

    }

    @Override
    public void onEnable() {
        prevSelectedSlot = -1;
    }

    @Override
    public void onDisable() {
//        resetSlot();
    }

    @Override
    public void onPlayerDamageBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        Main.logger.info("Click");
        equipBestTool(event.getPos());
    }

    public void equipBestTool(BlockPos pos)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if(player.capabilities.isCreativeMode)
            return;

        IBlockState state = BlockUtils.getState(pos);

        ItemStack heldItem = player.getHeldItemMainhand();
        float bestSpeed = getDestroySpeed(heldItem, state);
        int bestSlot = -1;

        int fallbackSlot = -1;
        InventoryPlayer inventory = player.inventory;

        for(int slot = 0; slot < 9; slot++)
        {
            if(slot == inventory.currentItem)
                continue;

            ItemStack stack = inventory.getStackInSlot(slot);

            if(fallbackSlot == -1 && !isDamageable(stack))
                fallbackSlot = slot;

            float speed = getDestroySpeed(stack, state);
            if(speed <= bestSpeed)
                continue;

            if(stack.getItem() instanceof ItemSword)
                continue;

            if(isTooDamaged(stack))
                continue;

            bestSpeed = speed;
            bestSlot = slot;
        }

        boolean useFallback =
                isDamageable(heldItem) && (isTooDamaged(heldItem)
                        && getDestroySpeed(heldItem, state) <= 1);

        if(bestSlot != -1)
        {
            inventory.currentItem = bestSlot;
            return;
        }

        if(!useFallback)
            return;

        if(fallbackSlot != -1)
        {
            inventory.currentItem = fallbackSlot;
            return;
        }

        if(isTooDamaged(heldItem))
            if(inventory.currentItem == 8)
                inventory.currentItem = 0;
            else
                inventory.currentItem++;
    }

    private float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        float speed = (stack == null || stack.isEmpty()) ? 1 : stack.getDestroySpeed(state);

        if(speed > 1)
        {
            int efficiency = EnchantmentHelper
                    .getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            if(efficiency > 0 && !(stack == null || stack.isEmpty()))
                speed += efficiency * efficiency + 1;
        }

        return speed;
    }

    private boolean isDamageable(ItemStack stack)
    {
        return !(stack == null || stack.isEmpty()) && stack.getItem().isDamageable();
    }

    private boolean isTooDamaged(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getItemDamage() <= 4;
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
            float value = getValue(item);

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

        // start timer
        timer = 100;
    }

    private float getValue(Item item)
    {
                if(item instanceof ItemSword)
                    return ((ItemSword)item).getAttackDamage();

        return Integer.MIN_VALUE;
    }

//    private void resetSlot()
//    {
//            Minecraft.getMinecraft().player.inventory.currentItem = oldSlot;
//            return;
//    }


}
