package cn.ksmcbrigade.AC;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.Commands;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Mod("ac")
@Mod.EventBusSubscriber
public class AutoChat {

    public static Logger LOGGER = LogManager.getLogger();
    public static final Path path = Paths.get("config/ac-config.json");
    public static String ggMessage = "gg";
    public static String Suffix = "~";

    public AutoChat() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("AutoChat loading...");
        init();
        LOGGER.info("AutoChat loaded.");
    }

    public static void init() throws IOException {
        if(!new File("config/vm/mods").exists()){
            new File("config/vm/mods").mkdirs();
        }
        if(!new File("config/vm/mods/AutoGG.json").exists()){
            JsonObject object = new JsonObject();
            object.addProperty("name","feature.ac.ag");
            object.addProperty("id","ac");
            object.addProperty("main","cn.ksmcbrigade.AC.Manager");
            object.addProperty("function","NONE");
            object.addProperty("function_2","runAG");
            object.addProperty("gui_main","NONE");
            object.addProperty("gui_function","NONE");
            Files.write(Paths.get("config/vm/mods/AutoGG.json"),object.toString().getBytes());
        }

        if(!new File("config/vm/mods/WalkNotice.json").exists()){
            JsonObject object = new JsonObject();
            object.addProperty("name","feature.ac.wc");
            object.addProperty("id","ac");
            object.addProperty("main","cn.ksmcbrigade.AC.Manager");
            object.addProperty("function","NONE");
            object.addProperty("function_2","runWN");
            object.addProperty("gui_main","NONE");
            object.addProperty("gui_function","NONE");
            Files.write(Paths.get("config/vm/mods/WalkNotice.json"),object.toString().getBytes());
        }

        if(!new File("config/vm/mods/ClickNotice.json").exists()){
            JsonObject object = new JsonObject();
            object.addProperty("name","feature.ac.cc");
            object.addProperty("id","ac");
            object.addProperty("main","cn.ksmcbrigade.AC.Manager");
            object.addProperty("function","NONE");
            object.addProperty("function_2","runCN");
            object.addProperty("gui_main","NONE");
            object.addProperty("gui_function","NONE");
            Files.write(Paths.get("config/vm/mods/ClickNotice.json"),object.toString().getBytes());
        }

        if(!new File("config/vm/mods/RERead.json").exists()){
            JsonObject object = new JsonObject();
            object.addProperty("name","feature.ac.rr");
            object.addProperty("id","ac");
            object.addProperty("main","cn.ksmcbrigade.AC.Manager");
            object.addProperty("function","NONE");
            object.addProperty("function_2","runRR");
            object.addProperty("gui_main","NONE");
            object.addProperty("gui_function","NONE");
            Files.write(Paths.get("config/vm/mods/RERead.json"),object.toString().getBytes());
        }

        if(!new File("config/vm/mods/ChatSuffix.json").exists()){
            JsonObject object = new JsonObject();
            object.addProperty("name","feature.ac.cm");
            object.addProperty("id","ac");
            object.addProperty("main","cn.ksmcbrigade.AC.Manager");
            object.addProperty("function","NONE");
            object.addProperty("function_2","runCM");
            object.addProperty("gui_main","NONE");
            object.addProperty("gui_function","NONE");
            Files.write(Paths.get("config/vm/mods/ChatSuffix.json"),object.toString().getBytes());
        }
        if(!new File("config/vm/mods/FancyChat.json").exists()){
            JsonObject object = new JsonObject();
            object.addProperty("name","feature.ac.fc");
            object.addProperty("id","ac");
            object.addProperty("main","cn.ksmcbrigade.AC.Manager");
            object.addProperty("function","NONE");
            object.addProperty("function_2","runFC");
            object.addProperty("gui_main","NONE");
            object.addProperty("gui_function","NONE");
            Files.write(Paths.get("config/vm/mods/FancyChat.json"),object.toString().getBytes());
        }

        if(!new File("config/ac-config.json").exists()){
            JsonObject object = new JsonObject();
            object.addProperty("ggMessage","gg");
            object.addProperty("suffix","~");
            Files.write(path,object.toString().getBytes());
        }
        JsonObject jsonObject = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
        ggMessage = jsonObject.get("ggMessage").getAsString();
        Suffix = jsonObject.get("suffix").getAsString();
    }

    public static Object is(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("cn.ksmcbrigade.VM.Utils");
        Class<?>[] parameterTypes = new Class[]{String.class};
        Method method = clazz.getDeclaredMethod("isEnabledMod", parameterTypes);
        method.setAccessible(true);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        return method.invoke(instance, name);
    }

    @SubscribeEvent
    public static void RegisterCommands(RegisterClientCommandsEvent event){
        event.getDispatcher().register(Commands.literal("sendMessage").then(Commands.argument("message", StringArgumentType.string()).then(Commands.argument("number", IntegerArgumentType.integer(0)).executes(context -> {
            String message = StringArgumentType.getString(context,"message");
            int wh = IntegerArgumentType.getInteger(context,"number");
            for(int i=0;i<wh;i++){
                Objects.requireNonNull(Minecraft.getInstance().getConnection()).getConnection().send(new ServerboundChatPacket(message));
            }
            return 0;
        }))));

        event.getDispatcher().register(Commands.literal("setGG").then(Commands.argument("message",StringArgumentType.string()).executes(context -> {
            ggMessage=StringArgumentType.getString(context,"message");
            try {
                save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return 0;
        })));

        event.getDispatcher().register(Commands.literal("setSuffix").then(Commands.argument("message",StringArgumentType.string()).executes(context -> {
            Suffix=StringArgumentType.getString(context,"message");
            try {
                save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return 0;
        })));
    }

    @SubscribeEvent
    public static void OnClientChat(ClientChatEvent event) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object o = is(I18n.get("feature.ac.cm"));
        Object o2 = is(I18n.get("feature.ac.rr"));
        Object o3 = is(I18n.get("feature.ac.fc"));
        if((o instanceof Boolean) && (boolean) o){
            event.setMessage(event.getMessage()+Suffix);
        }
        if((o2 instanceof Boolean) && (boolean) o2){
            Objects.requireNonNull(Minecraft.getInstance().getConnection()).getConnection().send(new ServerboundChatPacket(event.getMessage()));
        }
        if((o3 instanceof Boolean) && (boolean) o3){
            event.setMessage(Convert(event.getMessage()));
        }
    }

    public static String Convert(String input)
    {
        StringBuilder output = new StringBuilder();
        for(char c : input.toCharArray())
            output.append(convertChar(c));
        return output.toString();
    }

    public static String convertChar(char c)
    {
        if(c < 0x21 || c > 0x80)
            return "" + c;
        if("(){}[]|".contains(Character.toString(c)))
            return "" + c;
        return new String(Character.toChars(c + 0xfee0));
    }

    public static void save() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("ggMessage",ggMessage);
        json.addProperty("suffix",Suffix);
        Files.write(path,json.toString().getBytes());
    }
}
