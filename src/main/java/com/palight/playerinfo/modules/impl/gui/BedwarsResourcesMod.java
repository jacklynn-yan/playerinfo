package com.palight.playerinfo.modules.impl.gui;

import com.palight.playerinfo.gui.ingame.widgets.impl.ResourceWidget;
import com.palight.playerinfo.modules.Module;
import com.palight.playerinfo.options.ModConfiguration;

public class BedwarsResourcesMod extends Module {
    public BedwarsResourcesMod() {
        super("resources", "Bedwars Resources", "Shows a resources count for bedwars.", ModuleType.GUI, null, new ResourceWidget());
    }

    @Override
    public void init() {
        this.setEnabled(ModConfiguration.bedwarsResourcesModEnabled);
    }

    @Override
    public void setEnabled(boolean enabled) {
        ModConfiguration.writeConfig(ModConfiguration.CATEGORY_MODS, "bedwarsResourcesModEnabled", enabled);
        ModConfiguration.syncFromGUI();
        super.setEnabled(enabled);
    }
}
