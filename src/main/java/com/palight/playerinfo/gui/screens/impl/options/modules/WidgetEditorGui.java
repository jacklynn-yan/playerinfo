package com.palight.playerinfo.gui.screens.impl.options.modules;

import com.palight.playerinfo.PlayerInfo;
import com.palight.playerinfo.gui.ingame.widgets.GuiIngameWidget;
import com.palight.playerinfo.gui.screens.CustomGuiScreen;
import com.palight.playerinfo.modules.Module;
import com.palight.playerinfo.util.NumberUtil;
import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WidgetEditorGui extends CustomGuiScreen {

    private List<GuiIngameWidget> widgets = new ArrayList<GuiIngameWidget>();

    private GuiIngameWidget editingWidget;

    private int moduleXDiff; // x difference between widget and mouse
    private int moduleYDiff; // y difference between widget and mouse

    public WidgetEditorGui() {
        super(I18n.format("screen.widgetEditor"));
    }

    @Override
    public void initGui() {
        for (Module module : PlayerInfo.getModules().values()) {
            GuiIngameWidget widget = module.getWidget();
            if (widget != null) {
                widgets.add(widget);
            }
            module.startEditingWidgets();
        }
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        for (Module module : PlayerInfo.getModules().values()) {
            widgets.remove(module.getWidget());
            module.stopEditingWidgets();
        }
        PlayerInfo.saveWidgetPositions();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int btn) throws IOException {
        editingWidget = getClickedWidget(mouseX, mouseY);
        if (editingWidget == null) return;
        if (!editingWidget.movable) return;
        if (btn == 0) {
            moduleXDiff = editingWidget.getPosition().getX() - mouseX;
            moduleYDiff = editingWidget.getPosition().getY() - mouseY;
        } else if (btn == 1) {
            editingWidget.toggleChroma();
        }
        super.mouseClicked(mouseX, mouseY, btn);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int btn, long p_mouseClickMove_4_) {
        if (editingWidget == null) return;
        if (!editingWidget.movable) return;
        if (btn == 0) {
            editingWidget.getPosition().setX(mouseX + moduleXDiff);
            editingWidget.getPosition().setY(mouseY + moduleYDiff);
        }
        super.mouseClickMove(mouseX, mouseY, btn, p_mouseClickMove_4_);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int btn) {
        editingWidget = null;
        super.mouseReleased(mouseX, mouseY, btn);
    }

    private GuiIngameWidget getClickedWidget(int x, int y) {
        for (GuiIngameWidget widget : widgets) {
            if (widget == null) continue;
            if (NumberUtil.pointIsBetween(x, y, widget.getPosition().getX(), widget.getPosition().getY(), widget.getPosition().getX() + widget.width, widget.getPosition().getY() + widget.height)) {
                return widget;
            }
        }

        return null;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
