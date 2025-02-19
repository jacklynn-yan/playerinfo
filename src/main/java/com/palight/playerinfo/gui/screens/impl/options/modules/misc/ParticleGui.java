package com.palight.playerinfo.gui.screens.impl.options.modules.misc;

import com.palight.playerinfo.gui.screens.CustomGuiScreenScrollable;
import com.palight.playerinfo.gui.widgets.GuiCustomWidget;
import com.palight.playerinfo.gui.widgets.impl.GuiButton;
import com.palight.playerinfo.gui.widgets.impl.GuiDropdown;
import com.palight.playerinfo.options.ModConfiguration;
import com.palight.playerinfo.util.MCUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticleGui extends CustomGuiScreenScrollable {

    private int buttonX;
    private int buttonY;

    private GuiDropdown particlePicker;
    private GuiButton setParticleButton;

    public ParticleGui() {
        super(I18n.format("screen.particle"));
    }

    @Override
    public void initGui() {
        super.initGui();

        buttonX = guiX + 32;
        buttonY = guiY + 32;

        List<String> particleNames = new ArrayList<>();

        Arrays.asList(MCUtil.ParticleTypes.values()).forEach(type -> { particleNames.add(type.name.substring(0, 1).toUpperCase() + type.name.substring(1));});
        particleNames.add("Random");

        particlePicker = new GuiDropdown(0, buttonX, buttonY, particleNames.toArray(new String[0]));
        setParticleButton = new GuiButton(1, buttonX + 64, buttonY, 64, 20, "Set Particle");

        this.guiElements.addAll(Arrays.asList(
                this.particlePicker,
                this.setParticleButton
        ));
    }

    @Override
    protected void widgetClicked(GuiCustomWidget widget) {
        super.widgetClicked(widget);
        if (widget.id == setParticleButton.id) {
            System.out.println("SETTING CUSTOM CRIT PARTICLE TO " + particlePicker.getSelectedItem().toUpperCase());
            ModConfiguration.writeConfig(ModConfiguration.CATEGORY_PARTICLE, "selectedParticle", particlePicker.getSelectedItem().toLowerCase());
            ModConfiguration.syncFromGUI();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int btn) throws IOException {
        super.mouseClicked(mouseX, mouseY, btn);
        particlePicker.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY, btn);
    }
}
