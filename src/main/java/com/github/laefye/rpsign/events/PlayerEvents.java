package com.github.laefye.rpsign.events;

import com.github.laefye.rpsign.RPSign;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerEvents implements Listener {
    private final RPSign plugin;

    public PlayerEvents(RPSign plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void changeSign(SignChangeEvent event) {
        makeSign(event);
    }

    private void makeSign(SignChangeEvent event) {
        var lines = event.getLines().clone();
        var block = event.getBlock();
        plugin.getSignManager().put(block, null);
        if (!lines[0].equals("[ResourcePack]")) {
            return;
        }
        var service = plugin.getServiceManager().get(lines[1]);
        if (service == null) {
            setSign(event, plugin.getLangConfig().getString("sign.invalid-service"), "", "", "");
            return;
        }
        setSign(event, plugin.getLangConfig().getString("sign.loading"), "", "", "");
        service.getResourcePack(lines[2], plugin, pack -> {
            if (pack != null) {
                setSign(block,
                        plugin.getLangConfig().getString("sign.download"),
                        plugin.getLangConfig().getString("sign.download-name").formatted(pack.getName()),
                        "",
                        plugin.getLangConfig().getString("sign.download-click")
                        );
                plugin.getSignManager().put(block, pack);
            } else {
                setSign(block, plugin.getLangConfig().getString("sign.invalid-pack"), "", "", "");
            }
        });
    }

    public void setSign(Block block, String a, String b, String c, String d) {
        var sign = (Sign) block.getState();
        sign.setLine(0, a);
        sign.setLine(1, b);
        sign.setLine(2, c);
        sign.setLine(3, d);
        sign.update();
    }

    public void setSign(SignChangeEvent event, String a, String b, String c, String d) {
        event.setLine(0, a);
        event.setLine(1, b);
        event.setLine(2, c);
        event.setLine(3, d);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        var block = event.getClickedBlock();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || block == null || !block.getType().toString().contains("SIGN")) {
            return;
        }
        var pack = plugin.getSignManager().get(block);
        if (pack == null ) {
            return;
        }
        // Чтоб клиентский майнкрафт отдумался
        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                () -> event.getPlayer().setResourcePack(pack.getUrl(), pack.getHash()),
                20L
        );

    }
}
