package com.servidoresspace.parceiros;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.VaultEco;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;

import static org.bukkit.Bukkit.getServer;

public class ScoreAPI implements Listener {
    public void showScore(Player p) {
        MPlayer mp = MPlayer.get(p);
        Faction fac = mp.getFaction();
        String terras = BoardColl.get().getFactionAt(PS.valueOf(p.getLocation())).getName();
        Faction fact = BoardColl.get().getFactionAt(PS.valueOf(p.getLocation()));
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("sidebar", "dummy");
        obj.setDisplayName("§3§lACE MC");
        obj.getScore("§1").setScore(15);

        if (fact.getRelationTo(fac) == Rel.ALLY){
            obj.getScore("§e[ §3" + terras + " §e]").setScore(14);
        }else if (fact.getRelationTo(fac) == Rel.NEUTRAL){
            obj.getScore("§e[ §f" + terras + " §e]").setScore(14);
        }else if (fact.getRelationTo(fac) == Rel.ENEMY){
            obj.getScore("§e[ §c" + terras + " §e]").setScore(14);
        }else {
            obj.getScore("§e[ §a" + terras + " §e]").setScore(14);
        }
        obj.getScore("§9").setScore(13);
        obj.getScore("§7Poder: §e"+mp.getPowerRounded()+"/"+mp.getPowerMax()).setScore(12);
        obj.getScore("§2").setScore(11);
        if(mp.getFaction().getId().equalsIgnoreCase("none")) {
            obj.getScore("§b").setScore(10);
            obj.getScore(" §7Sem facção").setScore(10);

        }else{
            obj.getScore("§7Facção: §a" + fac.getName()).setScore(10);
            obj.getScore(" §7Poder: §9" + fac.getPowerRounded()).setScore(9);
            obj.getScore(" §7Onlines: §9" + fac.getOnlinePlayers().size() +"§7/§9"+fac.getMembersCount()).setScore(8);
            obj.getScore(" §7Terrenos: §9" + fac.getLandCount()).setScore(7);
        }

        obj.getScore("§3").setScore(7);
        this.setupEconomy();
        DecimalFormat formatador = new DecimalFormat("#,##0");
        double qtcoins = economy.getBalance(p.getName());
        String qtcoinsf = formatador.format(qtcoins);
        obj.getScore("§7Coins: §a"+qtcoinsf).setScore(6);
        obj.getScore("§7Cash: §cPrévia").setScore(5);
        obj.getScore("§8").setScore(4);
        obj.getScore("§5www.servidoresspace.com").setScore(3);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(sb);
    }

    public void startTimer() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("AceScore"), new BukkitRunnable() {
            @Override
            public void run() {
                for(Player on : Bukkit.getOnlinePlayers()) {
                    showScore(on);
                }
            }
        },0,10*20);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        showScore(p);
    }


    public static Economy economy = null;
    private boolean setupEconomy()
    {

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

}
