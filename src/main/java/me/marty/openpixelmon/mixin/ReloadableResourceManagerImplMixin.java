package me.marty.openpixelmon.mixin;

import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {

    @Shadow public abstract void addPack(ResourcePack pack);

    @Inject(method = "<init>", at = @At("TAIL"))
    private void pixelmon_register_resource_pack(ResourceType type, CallbackInfo ci) {
        this.addPack(new ResourcePack() {
            @Nullable
            @Override
            public InputStream openRoot(String fileName) throws IOException {
                return null;
            }

            @Override
            public InputStream open(ResourceType type, Identifier id) throws IOException {
                return null;
            }

            @Override
            public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
                return null;
            }

            @Override
            public boolean contains(ResourceType type, Identifier id) {
                return false;
            }

            @Override
            public Set<String> getNamespaces(ResourceType type) {
                return null;
            }

            @Nullable
            @Override
            public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public void close() {

            }
        });
    }

}
