package de.cristelknight.doapi.networking;

import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.networking.packet.ItemStackSyncS2CPacket;
import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;

public class DoApiMessages {

    public static final ResourceLocation ITEM_SYNC = new DoApiRL("item_sync");

    public static void registerS2CPackets(){
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ITEM_SYNC, new ItemStackSyncS2CPacket());
    }

}
