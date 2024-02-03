package cn.ksmcbrigade.AC;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class Manager {
    public static void runAG(Player player){
        //Use Mixin
    }

    public static void runRR(Player player){
        //Use Mixin
    }

    public static void runCM(Player player){
        //Use Event
    }

    public static void runFC(Player player){
        //Use Event
    }

    public static void runWN(Player player){
        Minecraft MC = Minecraft.getInstance();
        Options options = MC.options;
        if(options.keyDown.isDown()){
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundChatPacket(I18n.get("chat.ac.wc").replace("{key}",options.keyDown.getKey().getDisplayName().getString())));
        }
        if(options.keyUp.isDown()){
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundChatPacket(I18n.get("chat.ac.wc").replace("{key}",options.keyUp.getKey().getDisplayName().getString())));
        }
        if(options.keyLeft.isDown()){
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundChatPacket(I18n.get("chat.ac.wc").replace("{key}",options.keyLeft.getKey().getDisplayName().getString())));
        }
        if(options.keyRight.isDown()){
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundChatPacket(I18n.get("chat.ac.wc").replace("{key}",options.keyRight.getKey().getDisplayName().getString())));
        }
        if(options.keyJump.isDown()){
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundChatPacket(I18n.get("chat.ac.wc2").replace("{key}",options.keyJump.getKey().getDisplayName().getString())));
        }
    }

    public static void runCN(Player player){
        Minecraft MC = Minecraft.getInstance();
        Options options = MC.options;
        if(options.keyAttack.isDown()){
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundChatPacket(I18n.get("chat.ac.cc").replace("{l}",options.keyAttack.getKey().getDisplayName().getString())));
        }
        if(options.keyUse.isDown()){
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundChatPacket(I18n.get("chat.ac.cc").replace("{l}",options.keyUse.getKey().getDisplayName().getString())));
        }
    }
}
