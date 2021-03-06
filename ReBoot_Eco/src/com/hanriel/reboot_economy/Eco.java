package com.hanriel.reboot_economy;

import com.hanriel.reboot_core.command.CommandFramework;
import com.hanriel.reboot_core.Core;
import com.hanriel.reboot_core.util.Utils;
import com.hanriel.reboot_economy.command.*;
import com.hanriel.reboot_economy.config.*;
import com.hanriel.reboot_economy.config.YAML.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Eco extends JavaPlugin {

    public static final String CHAT_PREFIX = ChatColor.AQUA + "[" + ChatColor.GREEN + "Eco" + ChatColor.AQUA + "] " + ChatColor.GREEN;

    private static Eco instance;
    private static Lang lang;

    @Override
    public void onEnable() {
        if (instance != null) {
            getLogger().warning("Please do not use /reload or plugin reloaders. Do \"/eco reload\" instead.");
            return;
        }

        instance = this;
        lang = new Lang(new PluginConfig(this, "lang.yml"));

        CommandFramework.register(this, new EcoCommandHandler("eco"));
        CommandFramework.register(this, new PayCommandHandler("pay"));

        //Eco.getInstance().getServer().getPluginManager().registerEvents(new BreakBlockListener(), instance);

        load();
    }

    public void load() {

        try {
            lang.load();
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().warning("I/O error while using the language file. Default values will be used.");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            getLogger().warning("The lang.yml was not a valid YAML, please look at the error above. Default values will be used.");
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().warning("Unhandled error while reading the values for the configuration! Please inform the developer.");
        }

        Bukkit.getServicesManager().register(Eco.class, this, this, ServicePriority.Normal);

        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(this, "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        }
    }

    public static void addBalance(Player player, long amount) {
        setBalance(player, getBalance(player) + amount);
    }

    public static void setBalance(Player player, long amount) {
        player.setMetadata("c", new FixedMetadataValue(Core.getInstance(), amount));
    }

//    public static long[] getBalance(Player p) {
//        long[] i = new long[3];
//        i[0] = p.getMetadata("c").get(0).asLong();
//        i[2] = i[0] / 10000;
//        i[1] = i[0] / 100 % 100;
//        i[0] %= 100;
//        return i;
//    }

    public static long getBalance(Player player) {
        return player.getMetadata("c").get(0).asLong();
    }

//    public static long[] normBalance(long[] a) {
//        long[] i = new long[3];
//        i[0] = a[0] + toCopper("s", a[1]) + toCopper("g", a[2]);
//        i[2] = i[0] / 10000;
//        i[1] = i[0] / 100 % 100;
//        i[0] %= 100;
//        return i;
//    }

    public static long[] normBalance(long balance) {
        long[] i = new long[3];
        i[0] = balance;
        i[2] = i[0] / 10000;
        i[1] = i[0] / 100 % 100;
        i[0] %= 100;
        return i;
    }

    public static long toCopper(String cur, long amount) {
        if (cur.charAt(0) == 's') return amount * 100;
        if (cur.charAt(0) == 'g') return amount * 10000;
        return amount;
    }

    public static boolean hasBalance(Player player, String cur, long amount) {
        long i = player.getMetadata("c").get(0).asLong();
        long am = toCopper(cur, amount);
        return am <= i;
    }

//    public static String formatBalance(long[] amount){
//        return "&6" + amount[0] + " C &7" + amount[1] + " S &e" + amount[2] + " G";
//    }

    public static String formatBalance(long balance){
        long[] amount = normBalance(balance);
        return "&6" + amount[0] + " C &7" + amount[1] + " S &e" + amount[2] + " G";
    }

//
//    public static Lang getLang() {
//        return lang;
//    }
//

    public static Eco getInstance() {
        return instance;
    }

}
