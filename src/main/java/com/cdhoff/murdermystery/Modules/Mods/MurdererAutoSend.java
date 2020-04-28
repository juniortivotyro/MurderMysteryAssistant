package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;

public class MurdererAutoSend extends Mod {

    private static boolean autoSendStatus;

    public MurdererAutoSend() {
        super("MurdererAutoSend", "Automatically send the names of the murderers in party chat.", ModType.MURDERMYSTERY, Keyboard.KEY_END);
    }

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {
        if(!autoSendStatus){
            autoSendStatus = true;
        }
    }

    public static boolean getAutoSendStatus(){
        return autoSendStatus;
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
        autoSendStatus = true;
    }

    @Override
    public void onDisable() {
        autoSendStatus = false;
    }
}
