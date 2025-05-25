package com.ai.Tool-Leveling.client;

import com.ai.Tool-Leveling.Tool-LevelingMod;
import com.ai.Tool-Leveling.Tool-LevelingScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Tool-LevelingScreen extends HandledScreen<Tool-LevelingScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Tool-LevelingMod.MODID, "textures/gui/power_enchanter_gui.png");
    private EnchantmentListWidget enchantList;
    private ButtonWidget applyButton;

    public Tool-LevelingScreen(Tool-LevelingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 200;
        this.backgroundHeight = 180;
        this.titleX = 8;
        this.titleY = 6;
        this.playerInventoryTitleX = 8;
        this.playerInventoryTitleY = 94;
    }

    @Override
    protected void init() {
        super.init();
        int left = (width - backgroundWidth) / 2;
        int top = (height - backgroundHeight) / 2;

        // Scrollable list of all enchantments
        enchantList = new EnchantmentListWidget(client, 120, 80, top + 20, top + 100, left + 10, 20);
        for (Enchantment ench : Registry.ENCHANTMENT) {
            enchantList.addEntry(new EnchantmentListWidget.EnchantmentEntry(ench));
        }
        enchantList.setRenderBackground(false);
        this.addSelectableChild(enchantList);

        // XP cost display area: static, recalculated when selection changes
        // apply button
        applyButton = new ButtonWidget(left + 140, top + 60, 50, 20, Text.of("Enchant"), btn -> {
            EnchantmentListWidget.EnchantmentEntry entry = enchantList.getSelectedOrNull();
            if (entry != null) {
                int level = entry.getLevel();
                // send packet
                handler.sendEnchantPacket(entry.enchantment, level);
            }
        });
        this.addDrawableChild(applyButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        // XP cost text
        int left = (width - backgroundWidth) / 2;
        int top = (height - backgroundHeight) / 2;
        EnchantmentListWidget.EnchantmentEntry sel = enchantList.getSelectedOrNull();
        if (sel != null) {
            int cost = handler.getCostFor(sel.enchantment, sel.getLevel());
            String costText = "XP Cost: " + cost;
            client.textRenderer.draw(matrices, costText, left + 140, top + 40, 0xFFFFFF);
        }
        enchantList.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        renderBackgroundTexture(matrices);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        this.client.getTextureManager().bindTexture(TEXTURE);
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    private void renderBackgroundTexture(MatrixStack matrices) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
    }

    // Scrollable list implementation
    public static class EnchantmentListWidget extends ElementListWidget<EnchantmentListWidget.EnchantmentEntry> {
        public EnchantmentListWidget(net.minecraft.client.MinecraftClient client, int width, int height, int top, int bottom, int left, int entryHeight) {
            super(client, width, height, top, bottom, entryHeight);
            this.left = left;
            this.width = width;
            this.bottom = bottom;
        }

        @Override
        protected int getScrollbarPositionX() {
            return left + width - 6;
        }

        @Override
        public int getRowWidth() {
            return width - 12;
        }

        public static class EnchantmentEntry extends ElementListWidget.Entry<EnchantmentEntry> {
            private final Enchantment enchantment;
            private int level = 1;

            public EnchantmentEntry(Enchantment enchantment) {
                this.enchantment = enchantment;
            }

            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                String txt = enchantment.getName(1).getString() + " [" + level + "]";
                client.textRenderer.draw(matrices, txt, x + 2, y + 2, 0xFFFFFF);
            }

            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    level = Math.min(5, level + 1);
                    return true;
                } else if (button == 1) {
                    level = Math.max(1, level - 1);
                    return true;
                }
                return false;
            }

            public Text getNarration() {
                return Text.of(enchantment.getName(1).getString());
            }

            public Enchantment getEnchantment() {
                return enchantment;
            }

            public int getLevel() {
                return level;
            }
        }
    }
}