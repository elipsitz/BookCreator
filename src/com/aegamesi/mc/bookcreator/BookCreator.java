package com.aegamesi.mc.bookcreator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BookCreator extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		// getCommand("command").setExecutor(new CommandExecutor(this));
	}

	public void onDisable() {
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		try {
			doMake(evt.getPlayer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doMake(Player player) throws IOException {
		int num = 1;
		ItemStack item = null;
		BookMeta bm = null;

		FileInputStream fstream = new FileInputStream(getDataFolder() + "/" + "Great Expectations-Charles Dickens.txt");
		BufferedInputStream buff = new BufferedInputStream(fstream);
		byte buffer[] = new byte[200];
		int page = 0;
		while (buff.read(buffer) != -1) {
			page++;
			if (page > 300 || bm == null) {
				if (page > 300 && bm != null) {
					page = 0;
					num++;
					item.setItemMeta(bm);
					player.getInventory().addItem(item);
				}
				
				item = new ItemStack(Material.WRITTEN_BOOK);
				bm = (BookMeta) item.getItemMeta();
				bm.setTitle("Great Expectations Vol. " + num);
				bm.setAuthor("Charles Dickens");
			}
			bm.addPage(new String(buffer));
		}

		item.setItemMeta(bm);
		player.getInventory().addItem(item);
	}
}