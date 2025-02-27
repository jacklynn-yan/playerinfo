package com.palight.playerinfo.rendering.items;

import com.palight.playerinfo.mixin.render.IMixinItemRenderer;
import com.palight.playerinfo.options.ModConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;

public class CustomItemRenderer {
    private ItemRenderer parent;
    private Minecraft mc;

    public CustomItemRenderer(ItemRenderer parent) {
        this.parent = parent;
        mc = Minecraft.getMinecraft();
    }

    public void transformFirstPersonItem(float equipProgress, float swingProgress) {
        if (ModConfiguration.oldAnimationsModEnabled && ModConfiguration.bowAnimationEnabled && mc != null && mc.thePlayer != null &&
                mc.thePlayer.getItemInUse() != null && mc.thePlayer.getItemInUse().getItem() != null &&
                Item.getIdFromItem(mc.thePlayer.getItemInUse().getItem()) == 261) {
            GlStateManager.translate(0.0f, 0.05f, 0.04f);
        }

        if (ModConfiguration.oldAnimationsModEnabled && ModConfiguration.rodAnimationEnabled && mc != null && mc.thePlayer != null && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() !=
                null && Item.getIdFromItem(mc.thePlayer.getCurrentEquippedItem().getItem()) == 346) {
            GlStateManager.translate(0.08f, -0.027f, -0.33f);
            GlStateManager.scale(0.93f, 1.0f, 1.0f);
        }

        if (ModConfiguration.oldAnimationsModEnabled && ModConfiguration.blockHitAnimationEnabled && mc != null && mc.thePlayer != null && mc.thePlayer.isSwingInProgress && mc.thePlayer.getCurrentEquippedItem() !=
                null && !mc.thePlayer.isEating() && !mc.thePlayer.isBlocking()) {
            GlStateManager.scale(0.85f, 0.85f, 0.85f);
            GlStateManager.translate(-0.078f, 0.003f, 0.05f);
        }


        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    public void renderItemInFirstPerson(float partialTicks, float prevEquippedProgress, float equippedProgress, ItemStack itemToRender) {
        float f = 1.0F - (prevEquippedProgress + (equippedProgress - prevEquippedProgress) * partialTicks);
        EntityPlayerSP entityPlayerSP = mc.thePlayer;
        float f1 = entityPlayerSP.getSwingProgress(partialTicks);
        float f2 = entityPlayerSP.prevRotationPitch + (entityPlayerSP.rotationPitch - entityPlayerSP.prevRotationPitch) * partialTicks;
        float f3 = entityPlayerSP.prevRotationYaw + (entityPlayerSP.rotationYaw - entityPlayerSP.prevRotationYaw) * partialTicks;
        ((IMixinItemRenderer) parent).callRotateArroundXAndY(f2, f3);
        ((IMixinItemRenderer) parent).callSetLightMapFromPlayer(entityPlayerSP);
        ((IMixinItemRenderer) parent).callRotateWithPlayerRotations(entityPlayerSP, partialTicks);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();

        if (itemToRender != null) {
            if (itemToRender.getItem() == Items.filled_map) {
                ((IMixinItemRenderer) parent).callRenderItemMap(entityPlayerSP, f2, f, f1);
            } else if ((itemToRender.getItem() instanceof ItemSword) && !mc.thePlayer.isBlocking() && ModConfiguration.oldAnimationsModEnabled && ModConfiguration.swordAnimationEnabled) {
                transformFirstPersonItem(f, f1);
            } else if (entityPlayerSP.getItemInUseCount() > 0) {
                EnumAction enumaction = itemToRender.getItemUseAction();

                switch (enumaction) {
                    case NONE:
                        transformFirstPersonItem(f, 0.0F);
                        break;
                    case EAT:
                    case DRINK:
                        ((IMixinItemRenderer) parent).callPerformDrinking(entityPlayerSP, partialTicks);
                        if (ModConfiguration.oldAnimationsModEnabled && ModConfiguration.eatingAnimationEnabled) {
                            transformFirstPersonItem(f, f1);
                        } else {
                            transformFirstPersonItem(f, 0.0F);
                        }
                        break;
                    case BLOCK:
                        if (ModConfiguration.oldAnimationsModEnabled && ModConfiguration.blockHitAnimationEnabled) {
                            transformFirstPersonItem(f, f1);
                            ((IMixinItemRenderer) parent).callDoBlockTransformations();
                            GlStateManager.scale(0.83f, 0.88f, 0.85f);
                            GlStateManager.translate(-0.3f, 0.1f, 0.0f);
                        } else {
                            transformFirstPersonItem(f, 0f);
                            ((IMixinItemRenderer) parent).callDoBlockTransformations();
                        }
                        break;

                    case BOW:
                        if (ModConfiguration.oldAnimationsModEnabled && ModConfiguration.bowAnimationEnabled) {
                            transformFirstPersonItem(f, f1);
                            ((IMixinItemRenderer) parent).callDoBowTransformations(partialTicks, entityPlayerSP);
                            GlStateManager.translate(0.0F, 0.1F, -0.15F);
                        } else {
                            transformFirstPersonItem(f, 0.0F);
                            ((IMixinItemRenderer) parent).callDoBowTransformations(partialTicks, entityPlayerSP);
                        }
                }
            } else {
                ((IMixinItemRenderer) parent).callDoItemUsedTransformations(f1);
                transformFirstPersonItem(f, f1);
            }

            parent.renderItem(entityPlayerSP, itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
        } else if (!entityPlayerSP.isInvisible()) {
            ((IMixinItemRenderer) parent).callRenderPlayerArm(entityPlayerSP, f, f1);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }
}
