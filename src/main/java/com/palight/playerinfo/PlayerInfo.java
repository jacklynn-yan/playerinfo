package com.palight.playerinfo;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.palight.playerinfo.gui.ingame.widgets.GuiIngameWidget;
import com.palight.playerinfo.gui.ingame.widgets.WidgetPosition;
import com.palight.playerinfo.modules.Module;
import com.palight.playerinfo.modules.impl.gui.*;
import com.palight.playerinfo.modules.impl.misc.*;
import com.palight.playerinfo.modules.impl.movement.ToggleSprintMod;
import com.palight.playerinfo.modules.impl.util.NoteBlockMod;
import com.palight.playerinfo.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mod(modid = PlayerInfo.MODID, version = PlayerInfo.VERSION)
public class PlayerInfo
{
    //TODO update version here and in build.gradle
    public static final String NAME = "playerinfo";
    public static final String MODID = "playerinfo";
    public static final String VERSION = "1.15.4";
    public static final String SERVER_PROXY_CLASS = "com.palight.playerinfo.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "com.palight.playerinfo.proxy.ClientProxy";
    public static String DATA_FOLDER;
    public static File configFile = new File(DATA_FOLDER + "config.json");

    public static Random random = new Random();

    public static Gson gson = new Gson();
    public static JsonParser parser = new JsonParser();

    @SidedProxy(serverSide = PlayerInfo.SERVER_PROXY_CLASS, clientSide = PlayerInfo.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.Instance("playerinfo")
    public static PlayerInfo instance;

    private static final Map<String, Module> modules = new HashMap<>();

    static {
        modules.put("scoreboard", new ScoreboardMod());
        modules.put("lifx", new LifxMod());
        modules.put("backgroundBlur", new BlurMod());
        modules.put("pumpkinOverlay", new PumpkinMod());
        modules.put("noteBlockHelper", new NoteBlockMod());
        modules.put("coords", new CoordsMod());
        modules.put("mainMenu", new CustomMainMenuMod());
        modules.put("toggleSprint", new ToggleSprintMod());
        modules.put("resources", new BedwarsResourcesMod());
        modules.put("cps", new CPSMod());
        modules.put("fps", new FpsMod());
        modules.put("reachDisplay", new ReachDisplayMod());
        modules.put("displayTweaks", new DisplayTweaksMod());
        modules.put("hypixelEvents", new HypixelEventsMod());
        modules.put("discordRPC", new DiscordRichPresenceMod());
        modules.put("ping", new PingMod());
        modules.put("particle", new ParticleMod());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("(PLAYERINFO) INITIALIZING MOD!");
        proxy.init(event);
        createDataFolder();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void onWorldJoin() {

    }

    public void createDataFolder() {
        File dataFolder = new File(DATA_FOLDER);

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    public static Map<String, Module> getModules() {
        return modules;
    }

    public static void saveWidgetPositions() {
        Map<String, WidgetPosition> modulePositions = new HashMap<>();
        for (Module module : getModules().values()) {
            GuiIngameWidget widget = module.getWidget();
            modulePositions.put(module.getId(), widget.getPosition());
        }
    }
}
