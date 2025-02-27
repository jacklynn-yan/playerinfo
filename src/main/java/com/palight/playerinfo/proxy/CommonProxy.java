package com.palight.playerinfo.proxy;

import com.palight.playerinfo.PlayerInfo;
import com.palight.playerinfo.commands.CalcCommand;
import com.palight.playerinfo.gui.GuiHandler;
import com.palight.playerinfo.listeners.*;
import com.palight.playerinfo.macro.MacroConfig;
import com.palight.playerinfo.modules.Module;
import com.palight.playerinfo.options.ModConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.lwjgl.opengl.Display;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonProxy {

    public static Map<String, KeyBinding> keybinds = new HashMap<>();

    public void init(FMLInitializationEvent event) {

        // Registering Listeners
        MinecraftForge.EVENT_BUS.register(new HitListener());
        MinecraftForge.EVENT_BUS.register(new KeyListener());
        MinecraftForge.EVENT_BUS.register(new RenderListener());
        MinecraftForge.EVENT_BUS.register(new FovListener());
        MinecraftForge.EVENT_BUS.register(new MainScreenHandler());
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        MinecraftForge.EVENT_BUS.register(new MacroEventHandler());

        // Registering Commands
        ClientCommandHandler.instance.registerCommand(new CalcCommand());

        // Registering Keybinds
        // key codes at https://computercraft.info/wiki/images/thumb/8/81/CC-Keyboard-Charcodes.png/963px-CC-Keyboard-Charcodes.png
        keybinds.put("key.zoom", new KeyBinding("key.zoom", 21, "Player Info"));
        keybinds.put("key.main", new KeyBinding("key.main", 35, "Player Info"));
        keybinds.put("key.perspective", new KeyBinding("key.perspective", 29, "Player Info"));

        keybinds.values().forEach(ClientRegistry::registerKeyBinding);

        // Register Gui Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(PlayerInfo.instance, new GuiHandler());

        // init config
        try {
            ModConfiguration.initConfig();
            MacroConfig.initConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MacroConfig.syncFromFile();

        for (Module module : PlayerInfo.getModules().values()) {
            module.init();
        }

        PlayerInfo.DATA_FOLDER = Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/playerinfo/";

        Display.setTitle("playerinfo v" + PlayerInfo.VERSION + " (" + PlayerInfo.commitHash.substring(0, 7) + "/" + PlayerInfo.defaultBranchName + ")");

        if (ModConfiguration.widgetStates.length == 0) {
            PlayerInfo.saveWidgetPositions();
        }
        PlayerInfo.setModuleStates();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
