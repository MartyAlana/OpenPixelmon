package me.marty.openpixelmon.mixin;

import com.google.common.collect.ImmutableMap;
import me.marty.openpixelmon.api.event.DynamicRegistryBuilderCallback;
import me.marty.openpixelmon.api.util.DynamicRegistryBuilder;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DynamicRegistryManager.class)
public class DynamicRegistryManagerMixin {

	@Inject(method = "method_30531", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void onInfosCreation(CallbackInfoReturnable<ImmutableMap<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>>> callbackInfoReturnable, ImmutableMap.Builder<RegistryKey<? extends Registry<?>>, DynamicRegistryManager.Info<?>> builder) {
		DynamicRegistryBuilderCallback.EVENT.invoker().register(new DynamicRegistryBuilder(builder));
	}
}
