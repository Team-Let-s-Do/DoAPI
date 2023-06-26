package de.cristelknight.doapi.forge.terraform.sign;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * An <b>internal</b> interface implemented on {@link BlockBehaviour.Properties} that is used to prevent sign blocks from overriding their block sound group to the default wood block sound group.
 */
public interface BlockSettingsLock {
	/**
	 * Locks the block sound group.
	 */
	public void lock();

	/**
	 * Locks the block sound group.
	 */
	public static BlockBehaviour.Properties lock(BlockBehaviour.Properties settings) {
		((BlockSettingsLock) settings).lock();
		return settings;
	}
}
