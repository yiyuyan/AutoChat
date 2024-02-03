package cn.ksmcbrigade.AC.mixin;

import cn.ksmcbrigade.AC.AutoChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static cn.ksmcbrigade.AC.AutoChat.is;

@Mixin(ChatComponent.class)
public class ChatMixin {
    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;IIZ)V",at = @At("TAIL"))
    public void sendMessage(Component p_93791_, int p_93792_, int p_93793_, boolean p_93794_, CallbackInfo ci) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object o = is(I18n.get("feature.ac.ag"));
        String message = p_93791_.getString();
        if((o instanceof Boolean) && (boolean) o){
            if(message.contains(I18n.get("chat.ac.gg"))){
                Objects.requireNonNull(Minecraft.getInstance().getConnection()).getConnection().send(new ServerboundChatPacket(AutoChat.ggMessage));
            }
        }
    }
}
