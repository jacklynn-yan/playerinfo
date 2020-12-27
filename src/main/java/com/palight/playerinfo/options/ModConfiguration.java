package com.palight.playerinfo.options;

import com.palight.playerinfo.PlayerInfo;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.BooleanEntry;
import net.minecraftforge.fml.client.config.GuiConfigEntries.StringEntry;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ModConfiguration {
    private static Configuration config;
    private static File file;

    public static final String CATEGORY_GENERAL = Configuration.CATEGORY_GENERAL;

    public static final String CATEGORY_SERVERS = "servers";
    public static final String CATEGORY_HYPIXEL = CATEGORY_SERVERS + ".hypixel";

    public static final String CATEGORY_INTEGRATIONS = "integrations";
    public static final String CATEGORY_LIFX = CATEGORY_INTEGRATIONS + ".lifx";

    public static final String CATEGORY_GUI = "gui";

    public static final String CATEGORY_MODS = "mods";

    public static final String CATEGORY_WIDGETS = "widgets";

    public static final String CATEGORY_MAIN_MENU = "mainmenu";

    public static final String CATEGORY_DISCORD = "discord";

    public static final String CATEGORY_TWEAKS = "tweaks";
    public static final String CATEGORY_PARTICLE = CATEGORY_TWEAKS + ".particle";
    public static final String CATEGORY_DISPLAY = CATEGORY_TWEAKS + ".display";
    public static final String CATEGORY_PERSPECTIVE = CATEGORY_TWEAKS + ".perspective";

    private static class DefaultValues {

        private static final String selectedServer = "Hypixel";
        private static final String bedwarsMode = "bedwars_eight_one";
        private static final String alertSound = "none";
        private static final boolean friendAlertsEnabled = false;
        private static final String hypixelApiKey = "";

        private static final String lifxSelector = "all";
        private static final String lifxToken = "";
        private static final boolean lifxTeamMode = false;

        private static final boolean blurModEnabled = true;
        private static final boolean pumpkinModEnabled = false;
        private static final boolean lifxModEnabled = false;
        private static final boolean scoreboardModEnabled = false;
        private static final boolean noteBlockModEnabled = false;
        private static final boolean coordsModEnabled = false;
        private static final boolean mainMenuModEnabled = true;
        private static final boolean toggleSprintModEnabled = false;
        private static final boolean toggleSneakModEnabled = false;
        private static final boolean bedwarsResourcesModEnabled = false;
        private static final boolean cpsModEnabled = false;
        private static final boolean reachDisplayModEnabled = false;
        private static final boolean displayModEnabled = false;
        private static final boolean hypixelEventsModEnabled = false;
        private static final boolean pingModEnabled = false;
        private static final boolean fpsModEnabled = false;
        private static final boolean particleModEnabled = false;
        private static final boolean armorModEnabled = false;
        private static final boolean perspectiveModEnabled = false;

        private static final boolean toggleSprintWidgetEnabled = true;
        private static final String[] widgetStates = new String[0];

        private static final boolean mainMenuChroma = false;

        private static final boolean discordRPCEnabled = true;

        private static final String selectedParticle = "crit";

        private static final boolean disableWaterOverlay = false;

        private static final boolean mustHoldPerspectiveKey = true;

        private static final boolean scoreboardEnabled = true;
        private static final boolean scoreboardNumbersEnabled = true;
        private static final int scoreboardHeaderColor = 1610612736;
        private static final int scoreboardBodyColor = 1342177280;
    }


    public static String selectedServer = DefaultValues.selectedServer;
    public static String bedwarsMode = DefaultValues.bedwarsMode;
    public static String alertSound = DefaultValues.alertSound;
    public static boolean friendAlertsEnabled = DefaultValues.friendAlertsEnabled;
    public static String hypixelApiKey = DefaultValues.hypixelApiKey;

    public static String lifxSelector = DefaultValues.lifxSelector;
    public static String lifxToken = DefaultValues.lifxToken;
    public static boolean lifxTeamMode = DefaultValues.lifxTeamMode;

    public static boolean blurModEnabled = DefaultValues.blurModEnabled;
    public static boolean pumpkinModEnabled = DefaultValues.pumpkinModEnabled;
    public static boolean lifxModEnabled = DefaultValues.lifxModEnabled;
    public static boolean scoreboardModEnabled = DefaultValues.scoreboardModEnabled;
    public static boolean noteBlockModEnabled = DefaultValues.noteBlockModEnabled;
    public static boolean coordsModEnabled = DefaultValues.coordsModEnabled;
    public static boolean mainMenuModEnabled = DefaultValues.mainMenuModEnabled;
    public static boolean toggleSprintModEnabled = DefaultValues.toggleSprintModEnabled;
    public static boolean toggleSneakModEnabled = DefaultValues.toggleSneakModEnabled;
    public static boolean bedwarsResourcesModEnabled = DefaultValues.bedwarsResourcesModEnabled;
    public static boolean cpsModEnabled = DefaultValues.cpsModEnabled;
    public static boolean reachDisplayModEnabled = DefaultValues.reachDisplayModEnabled;
    public static boolean displayModEnabled = DefaultValues.displayModEnabled;
    public static boolean hypixelEventsModEnabled = DefaultValues.hypixelEventsModEnabled;
    public static boolean pingModEnabled = DefaultValues.pingModEnabled;
    public static boolean fpsModEnabled = DefaultValues.fpsModEnabled;
    public static boolean particleModEnabled = DefaultValues.particleModEnabled;
    public static boolean armorModEnabled = DefaultValues.armorModEnabled;
    public static boolean perspectiveModEnabled = DefaultValues.perspectiveModEnabled;

    public static boolean toggleSprintWidgetEnabled = DefaultValues.toggleSprintWidgetEnabled;
    public static String[] widgetStates = DefaultValues.widgetStates;

    public static boolean mainMenuChroma = DefaultValues.mainMenuChroma;

    public static boolean discordRPCEnabled = DefaultValues.discordRPCEnabled;

    public static String selectedParticle = DefaultValues.selectedParticle;

    public static boolean disableWaterOverlay = DefaultValues.disableWaterOverlay;

    public static boolean mustHoldPerspectiveKey = DefaultValues.mustHoldPerspectiveKey;

    public static boolean scoreboardEnabled = DefaultValues.scoreboardEnabled;
    public static boolean scoreboardNumbersEnabled = DefaultValues.scoreboardNumbersEnabled;
    public static int scoreboardHeaderColor = DefaultValues.scoreboardHeaderColor;
    public static int scoreboardBodyColor = DefaultValues.scoreboardBodyColor;

    public static Configuration getConfig() {
        return config;
    }

    public static void initConfig() throws IOException {
        file = new File(Loader.instance().getConfigDir(), PlayerInfo.MODID + ".cfg");

        if (!file.exists()) {
            file.createNewFile();
        }

        config = new Configuration(file);
        config.load();

        syncFromFile();
    }

    public static void syncFromFile() {
        syncConfig(true, true);
    }

    public static void syncFromGUI() {
        syncConfig(false, true);
    }

    private static void syncConfig(boolean loadConfigFromFile, boolean readFieldsFromConfig) {

        if (loadConfigFromFile) config.load();

        List<String> propOrderGeneral = new ArrayList<>();
        config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrderGeneral);

        Property scoreboardEnabled = config.get(CATEGORY_GUI, "scoreboardEnabled", DefaultValues.scoreboardEnabled, "Toggle scoreboard");
        Property scoreboardNumbersEnabled = config.get(CATEGORY_GUI, "scoreboardNumbersEnabled", DefaultValues.scoreboardNumbersEnabled, "Toggle the red numbers on the scoreboard");
        Property scoreboardHeaderColor = config.get(CATEGORY_GUI, "scoreboardHeaderColor", DefaultValues.scoreboardHeaderColor, "Scoreboard header color");
        Property scoreboardBodyColor = config.get(CATEGORY_GUI, "scoreboardBodyColor", DefaultValues.scoreboardBodyColor, "Scoreboard body color");

        List<String> propOrderGui = new ArrayList<>();
        propOrderGui.addAll(Arrays.asList(
                scoreboardEnabled.getName(),
                scoreboardNumbersEnabled.getName(),
                scoreboardHeaderColor.getName(),
                scoreboardBodyColor.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_GUI, propOrderGui);

        Property selectedServer = config.get(CATEGORY_SERVERS, "selectedServer", DefaultValues.selectedServer, "Selected server for server util");

        List<String> propOrderServers = new ArrayList<>();
        propOrderServers.addAll(Arrays.asList(
                selectedServer.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_SERVERS, propOrderServers);

        Property bedwarsMode = config.get(CATEGORY_HYPIXEL, "bedwarsMode", DefaultValues.bedwarsMode, "Bedwars mode for quick play");
        Property alertSound = config.get(CATEGORY_HYPIXEL, "alertSound", DefaultValues.alertSound, "Sound to be played for alerts");
        Property friendAlertsEnabled = config.get(CATEGORY_HYPIXEL, "friendAlertsEnabled", DefaultValues.friendAlertsEnabled, "Enable friend join/leave alerts");
        Property hypixelApiKey = config.get(CATEGORY_HYPIXEL, "hypixelApiKey", DefaultValues.hypixelApiKey, "Hypixel API key");

        List<String> propOrderHypixel = new ArrayList<>();
        propOrderHypixel.addAll(Arrays.asList(
                bedwarsMode.getName(),
                alertSound.getName(),
                friendAlertsEnabled.getName(),
                hypixelApiKey.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_HYPIXEL, propOrderHypixel);

        Property lifxSelector = config.get(CATEGORY_LIFX, "lifxSelector", DefaultValues.lifxSelector, "LIFX light selector");
        Property lifxToken = config.get(CATEGORY_LIFX, "lifxToken", DefaultValues.lifxToken, "LIFX token for LIFX integration");
        Property lifxTeamMode = config.get(CATEGORY_LIFX, "lifxTeamMode", DefaultValues.lifxTeamMode, "Whether or not the light should change based on your helmet color");

        List<String> propOrderLifx = new ArrayList<>();
        propOrderLifx.addAll(Arrays.asList(
                lifxSelector.getName(),
                lifxToken.getName(),
                lifxTeamMode.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_LIFX, propOrderLifx);

        Property blurModEnabled = config.get(CATEGORY_MODS, "blurModEnabled", DefaultValues.blurModEnabled, "Enable background blurring in gui");
        Property pumpkinModEnabled = config.get(CATEGORY_MODS, "pumpkinModEnabled", DefaultValues.pumpkinModEnabled, "Disable pumpkin overlay");
        Property lifxModEnabled = config.get(CATEGORY_MODS, "lifxModEnabled", DefaultValues.lifxModEnabled, "Enable LIFX mod");
        Property scoreboardModEnabled = config.get(CATEGORY_MODS, "scoreboardModEnabled", DefaultValues.scoreboardModEnabled, "Enable scoreboard mod");
        Property noteBlockModEnabled = config.get(CATEGORY_MODS, "noteBlockModEnabled", DefaultValues.noteBlockModEnabled, "Show note block notes");
        Property coordsModEnabled = config.get(CATEGORY_MODS, "coordsModEnabled", DefaultValues.coordsModEnabled, "Show your coordinates on screen");
        Property mainMenuModEnabled = config.get(CATEGORY_MODS, "mainMenuModEnabled", DefaultValues.mainMenuModEnabled, "Enable the custom main menu");
        Property toggleSprintModEnabled = config.get(CATEGORY_MODS, "toggleSprintModEnabled", DefaultValues.toggleSprintModEnabled, "Enable toggle sprint");
        Property toggleSneakModEnabled = config.get(CATEGORY_MODS, "toggleSneakModEnabled", DefaultValues.toggleSneakModEnabled, "Enable toggle sneak");
        Property bedwarsResourcesModEnabled = config.get(CATEGORY_MODS, "bedwarsResourcesModEnabled", DefaultValues.bedwarsResourcesModEnabled, "Enable bedwars resources indicator");
        Property cpsModEnabled = config.get(CATEGORY_MODS, "cpsModEnabled", DefaultValues.cpsModEnabled, "Enable CPS mod");
        Property reachDisplayModEnabled = config.get(CATEGORY_MODS, "reachDisplayModEnabled", DefaultValues.reachDisplayModEnabled, "Enable CPS mod");
        Property displayModEnabled = config.get(CATEGORY_MODS, "displayModEnabled", DefaultValues.displayModEnabled, "Enable display tweaks mod");
        Property hypixelEventsModEnabled = config.get(CATEGORY_MODS, "hypixelEventsModEnabled", DefaultValues.hypixelEventsModEnabled);
        Property pingModEnabled = config.get(CATEGORY_MODS, "pingModEnabled", DefaultValues.pingModEnabled);
        Property fpsModEnabled = config.get(CATEGORY_MODS, "fpsModEnabled", DefaultValues.fpsModEnabled);
        Property particleModEnabled = config.get(CATEGORY_MODS, "particleModEnabled", DefaultValues.particleModEnabled);
        Property armorModEnabled = config.get(CATEGORY_MODS, "armorModEnabled", DefaultValues.armorModEnabled);
        Property perspectiveModEnabled = config.get(CATEGORY_MODS, "perspectiveModEnabled", DefaultValues.perspectiveModEnabled);

        List<String> propOrderMods = new ArrayList<>();
        propOrderMods.addAll(Arrays.asList(
                blurModEnabled.getName(),
                pumpkinModEnabled.getName(),
                scoreboardModEnabled.getName(),
                lifxModEnabled.getName(),
                noteBlockModEnabled.getName(),
                coordsModEnabled.getName(),
                mainMenuModEnabled.getName(),
                toggleSprintModEnabled.getName(),
                toggleSneakModEnabled.getName(),
                bedwarsResourcesModEnabled.getName(),
                cpsModEnabled.getName(),
                reachDisplayModEnabled.getName(),
                displayModEnabled.getName(),
                hypixelEventsModEnabled.getName(),
                pingModEnabled.getName(),
                fpsModEnabled.getName(),
                particleModEnabled.getName(),
                armorModEnabled.getName(),
                perspectiveModEnabled.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_MODS, propOrderMods);

        Property toggleSprintWidgetEnabled = config.get(CATEGORY_WIDGETS, "toggleSprintWidgetEnabled", DefaultValues.toggleSprintWidgetEnabled, "Enable toggle sprint widget");
        Property widgetStates = config.get(CATEGORY_WIDGETS, "widgetStates", DefaultValues.widgetStates, "Saved widget states");

        List<String> propOrderWidgets = new ArrayList<>();
        propOrderWidgets.addAll(Arrays.asList(
                toggleSprintWidgetEnabled.getName(),
                widgetStates.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_WIDGETS, propOrderWidgets);

        Property mainMenuChroma = config.get(CATEGORY_MAIN_MENU, "mainMenuChroma", DefaultValues.mainMenuChroma, "Main menu chroma effect");

        List<String> propOrderMainMenu  = new ArrayList<>();
        propOrderMainMenu.addAll(Arrays.asList(
                mainMenuChroma.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_MAIN_MENU, propOrderMainMenu);

        Property discordRPCEnabled = config.get(CATEGORY_DISCORD, "discordRPCEnabled", DefaultValues.discordRPCEnabled, "Enable Discord Rich Presence");

        List<String> propOrderDiscord = new ArrayList<>();
        propOrderDiscord.addAll(Arrays.asList(
                discordRPCEnabled.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_DISCORD, propOrderDiscord);

        Property selectedParticle = config.get(CATEGORY_PARTICLE, "selectedParticle", DefaultValues.selectedParticle, "Selected crit particle");

        List<String> propOrderParticle = new ArrayList<>();
        propOrderParticle.addAll(Arrays.asList(
                selectedParticle.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_PARTICLE, propOrderParticle);

        Property disableWaterOverlay = config.get(CATEGORY_DISPLAY, "disableWaterOverlay", DefaultValues.disableWaterOverlay, "Disable water overlay");

        List<String> propOrderDisplay = new ArrayList<>();
        propOrderDisplay.addAll(Arrays.asList(
                disableWaterOverlay.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_DISPLAY, propOrderDisplay);

        Property mustHoldPerspectiveKey = config.get(CATEGORY_PERSPECTIVE, "mustHoldPerspectiveKey", DefaultValues.mustHoldPerspectiveKey, "Perspective reverts when the perspective key is no longer pressed.");

        List<String> propOrderPerspective = new ArrayList<>();
        propOrderPerspective.addAll(Arrays.asList(
                mustHoldPerspectiveKey.getName()
        ));
        config.setCategoryPropertyOrder(CATEGORY_PERSPECTIVE, propOrderPerspective);

        try {
            noteBlockModEnabled.setConfigEntryClass(BooleanEntry.class);

            selectedServer.setConfigEntryClass(StringEntry.class);

            bedwarsMode.setConfigEntryClass(StringEntry.class);
            alertSound.setConfigEntryClass(StringEntry.class);
            friendAlertsEnabled.setConfigEntryClass(BooleanEntry.class);
            hypixelApiKey.setConfigEntryClass(StringEntry.class);

            lifxSelector.setConfigEntryClass(StringEntry.class);
            lifxToken.setConfigEntryClass(StringEntry.class);
            lifxTeamMode.setConfigEntryClass(BooleanEntry.class);

            blurModEnabled.setConfigEntryClass(BooleanEntry.class);
            pumpkinModEnabled.setConfigEntryClass(BooleanEntry.class);
            lifxModEnabled.setConfigEntryClass(BooleanEntry.class);
            scoreboardModEnabled.setConfigEntryClass(BooleanEntry.class);
            coordsModEnabled.setConfigEntryClass(BooleanEntry.class);
            mainMenuModEnabled.setConfigEntryClass(BooleanEntry.class);
            toggleSprintModEnabled.setConfigEntryClass(BooleanEntry.class);
            toggleSneakModEnabled.setConfigEntryClass(BooleanEntry.class);
            bedwarsResourcesModEnabled.setConfigEntryClass(BooleanEntry.class);
            cpsModEnabled.setConfigEntryClass(BooleanEntry.class);
            reachDisplayModEnabled.setConfigEntryClass(BooleanEntry.class);
            displayModEnabled.setConfigEntryClass(BooleanEntry.class);
            hypixelEventsModEnabled.setConfigEntryClass(BooleanEntry.class);
            pingModEnabled.setConfigEntryClass(BooleanEntry.class);
            fpsModEnabled.setConfigEntryClass(BooleanEntry.class);
            particleModEnabled.setConfigEntryClass(BooleanEntry.class);
            armorModEnabled.setConfigEntryClass(BooleanEntry.class);
            perspectiveModEnabled.setConfigEntryClass(BooleanEntry.class);

            toggleSprintWidgetEnabled.setConfigEntryClass(BooleanEntry.class);
            widgetStates.setConfigEntryClass(GuiConfigEntries.ArrayEntry.class);

            mainMenuChroma.setConfigEntryClass(BooleanEntry.class);

            discordRPCEnabled.setConfigEntryClass(BooleanEntry.class);

            selectedParticle.setConfigEntryClass(StringEntry.class);

            disableWaterOverlay.setConfigEntryClass(BooleanEntry.class);

            mustHoldPerspectiveKey.setConfigEntryClass(BooleanEntry.class);

            scoreboardEnabled.setConfigEntryClass(BooleanEntry.class);
            scoreboardNumbersEnabled.setConfigEntryClass(BooleanEntry.class);
            scoreboardHeaderColor.setConfigEntryClass(GuiConfigEntries.IntegerEntry.class);
            scoreboardBodyColor.setConfigEntryClass(GuiConfigEntries.IntegerEntry.class);
        } catch(NoClassDefFoundError e) {
            e.printStackTrace();
        }


        if (readFieldsFromConfig) {
            ModConfiguration.noteBlockModEnabled = noteBlockModEnabled.getBoolean();

            ModConfiguration.selectedServer = selectedServer.getString();

            ModConfiguration.bedwarsMode = bedwarsMode.getString();
            ModConfiguration.alertSound = alertSound.getString();
            ModConfiguration.friendAlertsEnabled = friendAlertsEnabled.getBoolean();
            ModConfiguration.hypixelApiKey = hypixelApiKey.getString();

            ModConfiguration.lifxSelector = lifxSelector.getString();
            ModConfiguration.lifxToken = lifxToken.getString();
            ModConfiguration.lifxTeamMode = lifxTeamMode.getBoolean();

            ModConfiguration.blurModEnabled = blurModEnabled.getBoolean();
            ModConfiguration.pumpkinModEnabled = pumpkinModEnabled.getBoolean();
            ModConfiguration.lifxModEnabled = lifxModEnabled.getBoolean();
            ModConfiguration.scoreboardModEnabled = scoreboardModEnabled.getBoolean();
            ModConfiguration.coordsModEnabled = coordsModEnabled.getBoolean();
            ModConfiguration.mainMenuModEnabled = mainMenuModEnabled.getBoolean();
            ModConfiguration.toggleSprintModEnabled = toggleSprintModEnabled.getBoolean();
            ModConfiguration.toggleSneakModEnabled = toggleSneakModEnabled.getBoolean();
            ModConfiguration.bedwarsResourcesModEnabled = bedwarsResourcesModEnabled.getBoolean();
            ModConfiguration.cpsModEnabled = cpsModEnabled.getBoolean();
            ModConfiguration.reachDisplayModEnabled = reachDisplayModEnabled.getBoolean();
            ModConfiguration.displayModEnabled = displayModEnabled.getBoolean();
            ModConfiguration.hypixelEventsModEnabled = hypixelEventsModEnabled.getBoolean();
            ModConfiguration.pingModEnabled = pingModEnabled.getBoolean();
            ModConfiguration.fpsModEnabled = fpsModEnabled.getBoolean();
            ModConfiguration.particleModEnabled = particleModEnabled.getBoolean();
            ModConfiguration.armorModEnabled = armorModEnabled.getBoolean();
            ModConfiguration.perspectiveModEnabled = perspectiveModEnabled.getBoolean();

            ModConfiguration.toggleSprintWidgetEnabled = toggleSprintWidgetEnabled.getBoolean();
            ModConfiguration.widgetStates = widgetStates.getStringList();

            ModConfiguration.mainMenuChroma = mainMenuChroma.getBoolean();

            ModConfiguration.discordRPCEnabled = discordRPCEnabled.getBoolean();

            ModConfiguration.selectedParticle = selectedParticle.getString();

            ModConfiguration.disableWaterOverlay = disableWaterOverlay.getBoolean();

            ModConfiguration.mustHoldPerspectiveKey = mustHoldPerspectiveKey.getBoolean();

            ModConfiguration.scoreboardEnabled = scoreboardEnabled.getBoolean();
            ModConfiguration.scoreboardNumbersEnabled = scoreboardNumbersEnabled.getBoolean();
            ModConfiguration.scoreboardHeaderColor = scoreboardHeaderColor.getInt();
            ModConfiguration.scoreboardBodyColor = scoreboardBodyColor.getInt();
        }



        selectedServer.set(ModConfiguration.selectedServer);

        bedwarsMode.set(ModConfiguration.bedwarsMode);
        alertSound.set(ModConfiguration.alertSound);
        friendAlertsEnabled.set(ModConfiguration.friendAlertsEnabled);
        hypixelApiKey.set(ModConfiguration.hypixelApiKey);

        lifxSelector.set(ModConfiguration.lifxSelector);
        lifxToken.set(ModConfiguration.lifxToken);
        lifxTeamMode.set(ModConfiguration.lifxTeamMode);

        blurModEnabled.set(ModConfiguration.blurModEnabled);
        pumpkinModEnabled.set(ModConfiguration.pumpkinModEnabled);
        lifxModEnabled.set(ModConfiguration.lifxModEnabled);
        noteBlockModEnabled.set(ModConfiguration.noteBlockModEnabled);
        scoreboardModEnabled.set(ModConfiguration.scoreboardModEnabled);
        coordsModEnabled.set(ModConfiguration.coordsModEnabled);
        mainMenuModEnabled.set(ModConfiguration.mainMenuModEnabled);
        toggleSprintModEnabled.set(ModConfiguration.toggleSprintModEnabled);
        toggleSneakModEnabled.set(ModConfiguration.toggleSneakModEnabled);
        bedwarsResourcesModEnabled.set(ModConfiguration.bedwarsResourcesModEnabled);
        cpsModEnabled.set(ModConfiguration.cpsModEnabled);
        reachDisplayModEnabled.set(ModConfiguration.reachDisplayModEnabled);
        displayModEnabled.set(ModConfiguration.displayModEnabled);
        hypixelEventsModEnabled.set(ModConfiguration.hypixelEventsModEnabled);
        pingModEnabled.set(ModConfiguration.pingModEnabled);
        fpsModEnabled.set(ModConfiguration.fpsModEnabled);
        particleModEnabled.set(ModConfiguration.particleModEnabled);
        armorModEnabled.set(ModConfiguration.armorModEnabled);
        perspectiveModEnabled.set(ModConfiguration.perspectiveModEnabled);

        toggleSprintWidgetEnabled.set(ModConfiguration.toggleSprintWidgetEnabled);
        widgetStates.set(ModConfiguration.widgetStates);

        mainMenuChroma.set(ModConfiguration.mainMenuChroma);

        discordRPCEnabled.set(ModConfiguration.discordRPCEnabled);

        selectedParticle.set(ModConfiguration.selectedParticle);

        disableWaterOverlay.set(ModConfiguration.disableWaterOverlay);

        mustHoldPerspectiveKey.set(ModConfiguration.mustHoldPerspectiveKey);

        scoreboardEnabled.set(ModConfiguration.scoreboardEnabled);
        scoreboardNumbersEnabled.set(ModConfiguration.scoreboardNumbersEnabled);
        scoreboardHeaderColor.set(ModConfiguration.scoreboardHeaderColor);
        scoreboardBodyColor.set(ModConfiguration.scoreboardBodyColor);

        if (config.hasChanged()) config.save();
    }

    public static int getInt(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0).getInt();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0;
    }

    public static double getDouble(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, 0D).getDouble();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0D;
    }

    public static float getFloat(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return (float)config.get(category, key, 0D).getDouble();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return 0f;
    }

    public static String getString(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return config.get(category, key, "").getString();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return "";
    }

    public static short getShort(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return (short)config.get(category, key, (short)0).getInt();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return (short)0;
    }

    public static byte getByte(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return (byte)config.get(category, key, (byte)0).getInt();
            }
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return (byte)0;
    }

    public static boolean getBoolean(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (config.getCategory(category).containsKey(key))
                return config.get(category, key, false).getBoolean();
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return false;
    }

    public static String[] getStringList(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (config.getCategory(category).containsKey(key))
                return config.get(category, key, new String[0]).getStringList();
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return new String[0];
    }

    public static void writeConfig(String category, String key, String value) {
        config = new Configuration(file);
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, int value) {
        config = new Configuration(file);
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, boolean value) {
        config = new Configuration(file);
        try {
            config.load();
            boolean set = config.get(category, key, value).getBoolean();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, double value) {
        config = new Configuration(file);
        try {
            config.load();
            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, short value) {
        config = new Configuration(file);
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(Integer.valueOf(value));
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, byte value) {
        config = new Configuration(file);
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(Integer.valueOf(value));
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, float value) {
        config = new Configuration(file);
        try {
            config.load();
            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(Double.valueOf(value));
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static void writeConfig(String category, String key, String[] value) {
        config = new Configuration(file);
        try {
            config.load();
            String[] set = config.get(category, key, value).getStringList();
            config.getCategory(category).get(key).set(value);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
    }

    public static boolean hasCategory(String category) {
        config = new Configuration(file);
        try {
            config.load();
            return config.hasCategory(category);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return false;
    }

    public static boolean hasKey(String category, String key) {
        config = new Configuration(file);
        try {
            config.load();
            if (!config.hasCategory(category))
                return false;
            return config.getCategory(category).containsKey(key);
        } catch (Exception e) {
            System.out.println("Cannot load configuration file!");
        } finally {
            config.save();
        }
        return false;
    }
}
