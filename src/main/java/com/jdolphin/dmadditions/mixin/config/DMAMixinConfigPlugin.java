package com.jdolphin.dmadditions.mixin.config;

import com.jdolphin.dmadditions.config.DMACommonConfig;
import net.minecraftforge.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DMAMixinConfigPlugin implements IMixinConfigPlugin {

	@Override
	public void onLoad(String mixinPackage) {}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		List<String> immp = new ArrayList<>();
		immp.add("com.jdolphin.dmadditions.mixin.comp.immp.BotiMixin");
		immp.add("com.jdolphin.dmadditions.mixin.comp.immp.SotoMixin");

		List<String> ntm = new ArrayList<>();
		ntm.add("com.jdolphin.dmadditions.mixin.comp.ntm.TardisActionListMixin");

		if (immp.contains(mixinClassName)) {
			return LoadingModList.get().getModFileById("immersive_portals") != null && DMACommonConfig.isBotiEnabled();
		}
		if (ntm.contains(mixinClassName)) {
			return LoadingModList.get().getModFileById("tardis") != null;
		} return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}
