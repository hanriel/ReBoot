package com.hanriel.reboot_auth.internal;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.hanriel.reboot_auth.api.IconMenu;
import com.hanriel.reboot_auth.util.Validate;

/**
 * This class links an IconMenu with an Inventory, via InventoryHolder.
 */
public class MenuInventoryHolder implements InventoryHolder {

	private IconMenu iconMenu;
	
	public MenuInventoryHolder(IconMenu iconMenu) {
		this.iconMenu = iconMenu;
	}
	
	@Override
	public Inventory getInventory() {
		// This inventory will not do anything.
		// I'm 90% sure that it doesn't break any other plugin,
		// because the only way you can get here is using InventoryClickEvent,
		// that is cancelled by ChestCommands, or using InventoryOpenEvent.
		return Bukkit.createInventory(null, iconMenu.getSize());
	}
	
	public IconMenu getIconMenu() {
		return iconMenu;
	}
	
	public void setIconMenu(IconMenu iconMenu) {
		Validate.notNull(iconMenu, "IconMenu cannot be null");
		this.iconMenu = iconMenu;
	}

}
