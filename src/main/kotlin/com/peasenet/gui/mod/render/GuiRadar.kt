/*
 * MIT License
 *
 * Copyright (c) 2022-2024, Gavin C. Pease
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.peasenet.gui.mod.render

import com.peasenet.config.RadarConfig
import com.peasenet.gavui.Gui
import com.peasenet.gavui.GuiBuilder
import com.peasenet.gavui.math.PointF
import com.peasenet.gui.GuiElement
import com.peasenet.main.GavinsMod
import com.peasenet.main.Settings
import com.peasenet.settings.*
import net.minecraft.text.Text

/**
 *
 * @author gt3ch1
 * @version 06/18/2023
 */
class GuiRadar : GuiElement(Text.translatable("gavinsmod.mod.render.radar")) {

    private lateinit var playerEntityColor: ColorSetting
    private lateinit var hostileEntityColor: ColorSetting
    private lateinit var peacefulEntityColor: ColorSetting
    private lateinit var entityItemColor: ColorSetting
    private lateinit var backgroundColor: ColorSetting
    private lateinit var scaleSetting: ClickSetting
    private lateinit var pointSizeSetting: ClickSetting
    private lateinit var peacefulEntityToggle: ToggleSetting
    private lateinit var hostileEntityToggle: ToggleSetting
    private lateinit var itemToggle: ToggleSetting
    private lateinit var playerEntityToggle: ToggleSetting
    private lateinit var backgroundAlpha: SlideSetting
    private lateinit var pointAlpha: SlideSetting

    private lateinit var box: Gui

    private lateinit var colorTitle: Gui
    private lateinit var scaleTitle: Gui
    private lateinit var toggleTitle: Gui
    private lateinit var alphaTitle: Gui

    private var offsetX: Int = 0
    private var offsetY: Int = 0
    private var paddingX: Float = 0.0f
    private var paddingY: Float = 0.0f

    companion object {
        const val PADDING: Float = 5.0f
        var settings: ArrayList<Setting> = ArrayList()
        var visible: Boolean = false
    }

    override fun close() {
        super.close()
        visible = false
    }

    @Suppress("DuplicatedCode")
    override fun init() {
        visible = true
        this.parent = GavinsMod.guiSettings
        settings.clear()
        guis.clear()
        offsetX = client!!.window.scaledWidth / 2 - (this.width / 2)
        offsetY = client!!.window.scaledHeight / 2 - (this.height / 2)
        paddingX = PADDING + PADDING
        paddingY = offsetY + PADDING
        box = GuiBuilder()
            .setWidth(width)
            .setHeight(height)
            .setTopLeft(PADDING, offsetY.toFloat())
            .setHoverable(false)
            .setTransparency(0.9f)
            .build()
        val gapY = 12f
        var pos = PointF(paddingX, paddingY)

        colorTitle = GuiBuilder()
            .setHeight(12)
            .setTopLeft(pos)
            .setHoverable(false)
            .setTitle("gavinsmod.settings.radar.color")
            .setTransparency(0.0f)
            .setDrawBorder(false)
            .build()

        pos = pos.add(0f, gapY)

        playerEntityColor = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.player.color")
            .setColor(Settings.getConfig<RadarConfig>("radar").playerColor)
            .setTopLeft(pos)
            .buildColorSetting()
        playerEntityColor.setCallback { Settings.getConfig<RadarConfig>("radar").playerColor = playerEntityColor.color }

        pos = pos.add(playerEntityColor.gui.width + PADDING * 2, 0f)

        hostileEntityColor = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.mob.hostile.color")
            .setColor(Settings.getConfig<RadarConfig>("radar").hostileMobColor)
            .setTopLeft(pos)
            .buildColorSetting()
        hostileEntityColor.setCallback {
            Settings.getConfig<RadarConfig>("radar").hostileMobColor = hostileEntityColor.color
        }

        pos = PointF(paddingX, pos.y + gapY)

        peacefulEntityColor = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.mob.peaceful.color")
            .setColor(Settings.getConfig<RadarConfig>("radar").peacefulMobColor)
            .setTopLeft(pos)
            .buildColorSetting()
        peacefulEntityColor.setCallback {
            Settings.getConfig<RadarConfig>("radar").peacefulMobColor = peacefulEntityColor.color
        }

        pos = pos.add(peacefulEntityColor.gui.width + PADDING * 2, 0f)

        entityItemColor = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.item.color")
            .setColor(Settings.getConfig<RadarConfig>("radar").itemColor)
            .setTopLeft(pos)
            .buildColorSetting()
        entityItemColor.setCallback { Settings.getConfig<RadarConfig>("radar").itemColor = entityItemColor.color }

        pos = PointF(paddingX, pos.y + gapY)

        backgroundColor = SettingBuilder()
            .setTitle("gavinsmod.settings.background.color")
            .setColor(Settings.getConfig<RadarConfig>("radar").backgroundColor)
            .setTopLeft(pos)
            .buildColorSetting()
        backgroundColor.setCallback { Settings.getConfig<RadarConfig>("radar").backgroundColor = backgroundColor.color }

        pos = PointF(paddingX, pos.y + gapY)

        scaleTitle = GuiBuilder()
            .setHeight(12)
            .setTopLeft(pos)
            .setHoverable(false)
            .setTitle("gavinsmod.settings.radar.scale")
            .setTransparency(0.0f)
            .setDrawBorder(false)
            .build()

        pos = pos.add(0f, gapY)


        scaleSetting = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.scale")
            .setCallback(this::increaseScale)
            .setTopLeft(pos)
            .buildClickSetting()

        pos = pos.add(scaleSetting.gui.width + PADDING * 2, 0f)

        pointSizeSetting = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.pointsize")
            .setCallback(this::togglePointSize)
            .setTopLeft(pos)
            .buildClickSetting()

        pos = PointF(paddingX, pos.y + gapY)

        toggleTitle = GuiBuilder()
            .setHeight(12)
            .setTopLeft(pos)
            .setHoverable(false)
            .setTitle("gavinsmod.settings.radar.toggle")
            .setTransparency(0.0f)
            .setDrawBorder(false)
            .build()

        pos = pos.add(0f, gapY)

        peacefulEntityToggle = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.mob.peaceful")
            .setState(Settings.getConfig<RadarConfig>("radar").isShowPeacefulMob)
            .setTopLeft(pos)
            .buildToggleSetting()
        peacefulEntityToggle.setCallback {
            Settings.getConfig<RadarConfig>("radar").isShowPeacefulMob = peacefulEntityToggle.value
        }


        hostileEntityToggle = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.mob.hostile")
            .setState(Settings.getConfig<RadarConfig>("radar").isShowHostileMob)
            .setTopLeft(pos)
            .buildToggleSetting()
        hostileEntityToggle.setCallback {
            Settings.getConfig<RadarConfig>("radar").isShowHostileMob = hostileEntityToggle.value
        }

        itemToggle = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.item")
            .setState(Settings.getConfig<RadarConfig>("radar").isShowItem)
            .setTopLeft(pos)
            .buildToggleSetting()
        itemToggle.setCallback { Settings.getConfig<RadarConfig>("radar").isShowItem = itemToggle.value }

        playerEntityToggle = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.player")
            .setState(Settings.getConfig<RadarConfig>("radar").isShowPlayer)
            .setTopLeft(pos)
            .buildToggleSetting()
        playerEntityToggle.setCallback {
            Settings.getConfig<RadarConfig>("radar").isShowPlayer = playerEntityToggle.value
        }

        alphaTitle = GuiBuilder()
            .setHeight(12)
            .setTopLeft(pos)
            .setHoverable(false)
            .setTitle("gavinsmod.settings.radar.alpha")
            .setTransparency(0.0f)
            .setDrawBorder(false)
            .build()

        pos = pos.add(0f, gapY)


        backgroundAlpha = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.background.alpha")
            .setValue(Settings.getConfig<RadarConfig>("radar").backgroundAlpha)
            .setTopLeft(pos)
            .buildSlider()
        backgroundAlpha.setCallback { Settings.getConfig<RadarConfig>("radar").backgroundAlpha = backgroundAlpha.value }

        pos = pos.add(0f, gapY)

        pointAlpha = SettingBuilder()
            .setTitle("gavinsmod.settings.radar.point.alpha")
            .setValue(Settings.getConfig<RadarConfig>("radar").pointAlpha)
            .setTopLeft(pos)
            .buildSlider()
        pointAlpha.setCallback { Settings.getConfig<RadarConfig>("radar").pointAlpha = pointAlpha.value }
        // add all settings to the list
        guis.add(playerEntityColor.gui)
        guis.add(hostileEntityColor.gui)
        guis.add(peacefulEntityColor.gui)
        guis.add(entityItemColor.gui)
        guis.add(backgroundColor.gui)
        guis.add(scaleSetting.gui)
        guis.add(pointSizeSetting.gui)
        guis.add(peacefulEntityToggle.gui)
        guis.add(hostileEntityToggle.gui)
        guis.add(itemToggle.gui)
        guis.add(playerEntityToggle.gui)
        guis.add(backgroundAlpha.gui)
        guis.add(pointAlpha.gui)

        var maxWidth = 0
        for (gui in guis) {
            if (gui.title == null)
                continue
            if (client!!.textRenderer.getWidth(gui.title) > maxWidth) {
                maxWidth = client!!.textRenderer.getWidth(gui.title)
            }
        }

        guis.forEach {
            it.width = (maxWidth + 10).toFloat()
        }
        pos = PointF(paddingX, paddingY + gapY)
        playerEntityColor.gui.position = pos
        pos = pos.add(playerEntityColor.gui.width + PADDING, 0f)
        hostileEntityColor.gui.position = pos
        pos = PointF(paddingX, pos.y + gapY)
        peacefulEntityColor.gui.position = pos
        pos = pos.add(peacefulEntityColor.gui.width + PADDING, 0f)
        entityItemColor.gui.position = pos
        pos = PointF(paddingX, pos.y + gapY)
        backgroundColor.gui.position = pos
        pos = PointF(paddingX, pos.y + gapY)
        scaleTitle.position = (pos)
        pos = PointF(paddingX, pos.y + gapY)
        pointSizeSetting.gui.position = pos
        pos = pos.add(pointSizeSetting.gui.width + PADDING, 0f)
        scaleSetting.gui.position = pos
        pos = PointF(paddingX, pos.y + gapY)

        toggleTitle.position = pos
        pos = PointF(paddingX, pos.y + gapY)
        playerEntityToggle.gui.position = pos
        pos = pos.add(playerEntityToggle.gui.width + PADDING, 0f)
        hostileEntityToggle.gui.position = pos
        pos = PointF(paddingX, pos.y + gapY)
        peacefulEntityToggle.gui.position = pos
        pos = pos.add(peacefulEntityToggle.gui.width + PADDING, 0f)
        itemToggle.gui.position = pos
        pos = PointF(paddingX, pos.y + gapY)
        alphaTitle.position = pos
        pos = PointF(paddingX, pos.y + gapY)
        backgroundAlpha.gui.position = pos
        pos = pos.add(backgroundAlpha.gui.width + PADDING, 0f)
        pointAlpha.gui.position = pos

        guis.add(0, box)
        guis.add(colorTitle)
        guis.add(alphaTitle)
        guis.add(toggleTitle)
        guis.add(scaleTitle)
        guis.add(alphaTitle)
        updateScaleText(scaleSetting, Settings.getConfig<RadarConfig>("radar").scale)
        updateScaleText(pointSizeSetting, Settings.getConfig<RadarConfig>("radar").pointSize)
        super.init()
    }

    /**
     * Callback method for the scale setting.
     */
    private fun increaseScale() {
        Settings.getConfig<RadarConfig>("radar").scale += 1
        updateScaleText(scaleSetting, Settings.getConfig<RadarConfig>("radar").scale)
    }

    /**
     * Callback for the point size setting.
     */
    private fun togglePointSize() {
        Settings.getConfig<RadarConfig>("radar").updatePointSizeCallback()
        updateScaleText(pointSizeSetting, Settings.getConfig<RadarConfig>("radar").pointSize)
    }

    private fun updateScaleText(setting: ClickSetting, value: Int) {
        setting.gui.title =
            (Text.translatable(setting.gui.translationKey).append(Text.literal(" (%s)".format(value))))
    }

}