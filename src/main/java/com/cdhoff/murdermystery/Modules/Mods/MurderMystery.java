package com.cdhoff.murdermystery.Modules.Mods;

import com.cdhoff.murdermystery.Modules.Mod;
import com.cdhoff.murdermystery.Modules.ModType;
import com.cdhoff.murdermystery.Modules.utils.ConfigManager;
import com.cdhoff.murdermystery.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;

public class MurderMystery extends Mod {

    public MurderMystery() {
        super("MurderMystery", "Tells you who the murderer is", ModType.MURDERMYSTERY, Keyboard.KEY_DELETE);
    }

    public static ArrayList<String> murderers = new ArrayList<String>();
    //public String[] items = "sword,stick,axe,dead_bush,rod,feather,pie,pick,apple,name_tag,sponge,carrot,bone,double_plant".split(",");
    public static String murderDisplay = "";

    public Integer tickCount = 0;

    public static DecimalFormat df = new DecimalFormat("0.0");

    @Override
    public void onEvent(LivingEvent.LivingUpdateEvent event) {
        //For every loaded entity (Player, mob, etc.) in the world

        tickCount++;
        if(tickCount % ConfigManager.MurderMystery.murdererUpdateFrequency == 0) {

            murderDisplay = "";

            ArrayList<EntityPlayer> ents = new ArrayList<EntityPlayer>();

            try {
                for (EntityPlayer entity : Minecraft.getMinecraft().world.playerEntities) {

                    ents.add(entity);

                    //If the entity is a player
                    if (entity instanceof EntityPlayer) {
                        //Store the name of the player
                        String playerName = entity.getName();
                        //Store the item the player is holding
                        String heldItem = entity.getHeldItemMainhand().getItem().getRegistryName().toString();
                        //For every item in the list of items
                        for (String item : ConfigManager.MurderMystery.detectableItems) {
                            //If the item the player is holding matches any of the items in the config
                            if (heldItem.matches(".*" + item + ".*")) {


                                if (!murderers.contains(playerName)) {

                                    murderers.add(playerName);

                                    ISound sound = new PositionedSoundRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1, 1, (float)entity.posX, (float)entity.posY, (float)entity.posZ);

                                    //Minecraft.getMinecraft().getSoundHandler().playSound(sound);
                                    Minecraft.getMinecraft().world.playSound(Minecraft.getMinecraft().player, Minecraft.getMinecraft().player.getPosition().getX(), Minecraft.getMinecraft().player.getPosition().getY(), Minecraft.getMinecraft().player.getPosition().getZ(),SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1.0f, 1.0f);

                                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(playerName + " is the murderer!"));

                                    if (MurdererAutoSend.getAutoSendStatus())
                                        Minecraft.getMinecraft().player.sendChatMessage("/pc " + playerName + " is the murderer!");

                                }

                            }
                        }
                    }
                }
            }catch(NullPointerException e){
            }

            for (String player : murderers) {
                for (EntityPlayer ent : ents) {
                    if(ent.getName().equals(player)){
                        murderDisplay += player + " (" + df.format(ent.getDistance(Minecraft.getMinecraft().player)) + ") | ";
                    }
                }
            }

        }
    }


    public static String formatString(String s){
        return Optional.ofNullable(s).filter(str -> s.length() != 0).map(str -> str.substring(0, str.length() - 2)).orElse(s);
    }

    @Override
    public void onDraw(RenderGameOverlayEvent.Post event) {
        RenderManager.drawMurderMystery(Minecraft.getMinecraft());
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
        murderers.clear();
        murderDisplay = "";
    }

    @Override
    public void onDisable() {
        murderers.clear();
        murderDisplay = "";
    }
}
