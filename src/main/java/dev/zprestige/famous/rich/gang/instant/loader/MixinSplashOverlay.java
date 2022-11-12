package dev.zprestige.famous.rich.gang.instant.loader;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(SplashOverlay.class)
public class MixinSplashOverlay {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SplashOverlay;fill(Lnet/minecraft/client/util/math/MatrixStack;IIIII)V"), index = 5)
    private int render(int par2) {
        if (mc.world != null) {
            return new Color(0, 0, 0, 0).getRGB();
        }
        return par2;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V"), index = 3)
    private float setShaderColor(float alpha) {
        if (mc.world != null) {
            return 0.0f;
        }
        return alpha;
    }

    @Inject(method = "pausesGame", at = @At("HEAD"), cancellable = true)
    private void pauseGame(CallbackInfoReturnable<Boolean> cir) {
        if (mc.world != null) {
            cir.setReturnValue(false);
        }
    }
}
