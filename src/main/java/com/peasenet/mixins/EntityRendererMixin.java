/*
 * Copyright (c) 2022. Gavin Pease and contributors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.peasenet.mixins;

import com.peasenet.main.GavinsMod;
import com.peasenet.mods.Type;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Shadow
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Shadow
    @Final
    protected TextRenderer textRenderer;

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void renderHealth(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        double d = this.dispatcher.getSquaredDistanceToCamera(entity);
        if (entity instanceof LivingEntity && GavinsMod.isEnabled(Type.MOD_HPTAG)) {
            LivingEntity livingEntity = (LivingEntity) entity;
            double currHealth = livingEntity.getHealth();
            currHealth = Math.round(currHealth * 2) / 2.0;

            var text = "" + currHealth + " HP";
            if (!(d > 1024.0)) {
                boolean bl = !entity.isSneaky();
                float f = entity.getHeight() + 0.5F;

                matrices.push();
                matrices.translate(0.0, f, 0.0);
                matrices.multiply(this.dispatcher.getRotation());
                matrices.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
                int j = (int) (g * 255.0F) << 24;
                float h = (float) (-textRenderer.getWidth(text) / 2);
                int color = 0x00ff00;
                var percentHealth = (int) (currHealth / livingEntity.getMaxHealth());
                if (percentHealth < 0.75)
                    color = 0xffff00;
                if (percentHealth < 0.5)
                    color = 0xffa500;
                if (percentHealth < 0.25)
                    color = 0xff0000;
                textRenderer.draw(text, h, (float) 0, color, false, matrix4f, vertexConsumers, bl, j, light);
                if (bl) {
                    textRenderer.draw(text, h, (float) 0, color, false, matrix4f, vertexConsumers, false, 0, light);
                }

                matrices.pop();
            }
        }
    }
}
