package dev.juligame.classes.editors;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import dev.juligame.JuliMenus;
import dev.juligame.classes.enums.primitiveEnum;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Function;

public class signEditor {
    public signEditor(final Player player, final primitiveEnum type, final String placeholder, final Function<String, Void> method) {
        player.closeInventory();

        (new BukkitRunnable() {
            public void run() {
                try {
                    final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();


                    PacketContainer blockUpdate = new PacketContainer(PacketType.Play.Server.BLOCK_CHANGE);
                    blockUpdate.getModifier().writeDefaults();

                    BlockPosition signPosition = new BlockPosition(player.getLocation().toVector());
                    WrappedBlockData wrappedBlockData = WrappedBlockData.createData(Material.OAK_SIGN);

                    blockUpdate.getBlockPositionModifier().write(0, signPosition);
                    blockUpdate.getBlockData().write(0, wrappedBlockData);
                    protocolManager.sendServerPacket(player, blockUpdate);


                    player.sendBlockChange(player.getLocation(), Material.OAK_SIGN.createBlockData());
                    player.sendSignChange(player.getLocation(), new String[]{placeholder.replace("§", "&"), "", "", type.name()});


                    PacketContainer openSignPacket = new PacketContainer(PacketType.Play.Server.OPEN_SIGN_EDITOR);
                    openSignPacket.getModifier().writeDefaults();
                    openSignPacket.getBlockPositionModifier().write(0, signPosition);
                    protocolManager.sendServerPacket(player, openSignPacket);


                    wrappedBlockData = WrappedBlockData.createData(Material.AIR);
                    blockUpdate.getBlockData().write(0, wrappedBlockData);
                    protocolManager.sendServerPacket(player, blockUpdate);


                    PacketAdapter packetAdapter = new PacketAdapter((Plugin) JuliMenus.plugin, new PacketType[]{PacketType.Play.Client.UPDATE_SIGN}) {
                        public void onPacketReceiving(PacketEvent event) {
                            final Player ePlayer = event.getPlayer();
                            if (!ePlayer.getName().equals(player.getName()))
                                return;
                            PacketContainer packet = event.getPacket();
                            String[] lines = (String[]) packet.getStringArrays().read(0);

                            protocolManager.removePacketListener((PacketListener) this);

                            if (!type.isValid(lines[0])) {
                                if (!lines[0].isEmpty()) {
                                    player.sendMessage("§cInvalid value! Required: " + type.name() + " given " + primitiveEnum.getType(lines[0]));
                                    (new BukkitRunnable() {
                                        public void run() {
                                            new signEditor(ePlayer, type, placeholder, method);
                                        }
                                    }).runTaskLater(JuliMenus.plugin, 0L);
                                }
                            } else {
                                player.sendMessage("§aValue updated!");
                                method.apply(lines[0].replace("&", "§"));
                            }
                        }
                    };
                    protocolManager.addPacketListener((PacketListener) packetAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).runTaskLater(JuliMenus.plugin, 1L);
    }
}