package de.cristelknight.doapi.forge.common.packs;

import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RepositorySourceMaker implements RepositorySource {
	private boolean client;
	public RepositorySourceMaker(boolean client) {
		this.client = client;
	}
	@Override
	public void loadPacks(@NotNull Consumer<Pack> consumer) {
		BuiltInPackRegistry.getPacks(consumer, client);
	}
}