package at.beampur.beamport.commands;

import at.beampur.beamport.master.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Tpa implements CommandExecutor {

    public Main plugin;
    public Tpa(Main plugin) { this.plugin = plugin; }


    private HashMap<Player, Player> tpaRequest = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 1) {

                //equalsIgnoreCase checkt ob bei args[0] accept steht (ignoreCase = scheiss auf groß,kein schreibung)
                if(args[0].equalsIgnoreCase("deny")) {

                    Player targetPlayer = tpaRequest.get(player);//FICK DICH
                    tpaRequest.remove(player);

                    player.sendMessage("Die Anfrage wurde erfolgreich abgelehnt.");

                    targetPlayer.sendMessage("Die Anfrage wurde abgelehnt.");
                    return false;
                }
                //equalsIgnoreCase checkt ob bei args[0] accept steht (ignoreCase = scheiss auf groß,kein schreibung)
                if(args[0].equalsIgnoreCase("accept")) {

                    if(tpaRequest.containsValue(sender)) {

                        Player tpaTarget = tpaRequest.get(player);

                        tpaTarget.teleport(player.getLocation());
                        player.sendMessage("Du wurdest Teleportiert.");
                        tpaRequest.remove(tpaTarget);
                        return false;

                    } else {
                        player.sendMessage("Du hast aktuell keine Anfragen.");
                        return false;
                    }
                }

                String targetName = args[0];
                Player targetPlayer = Bukkit.getPlayer(targetName);

                if (targetPlayer != null) {

                    String tpaPerm = "beamport.tp.tpa";

                    if (targetPlayer.hasPermission(tpaPerm)) {

                        if (targetPlayer.isOnline()) {

                            // Spieler /tpa LightningDesign


                            if(tpaRequest.containsKey(player)) {

                                player.sendMessage("Du hast bereits eine Anfrage gesendet.");
                                return false;

                            }

                            if(tpaRequest.containsValue(targetPlayer)) {

                                player.sendMessage("Dieser Spieler hat bereits eine Anfrage von jemand anderem");
                                return false;

                            }

                            tpaRequest.put(player, targetPlayer);

                            targetPlayer.sendMessage("Der Spieler " + player.getName() + " hat dir eine Anfrage " +
                                    "geschickt ob er/sie sich zu dir Teleportieren darf, antworte " +
                                    "mit /tpa accept oder /tpa deny");

                            player.sendMessage("Du hast die Anfrage erfolgreich an den " +
                                    "Spieler " + targetPlayer.getName() + " gesendet");
                            //               K          V
                            //     5.      Beampur | LightningDesign


                            new BukkitRunnable() {

                                int timer = 5; // 20 -> Sekunden
                                Player targetFinal = targetPlayer;

                                @Override
                                public void run() {

                                    if(!tpaRequest.containsValue(targetFinal)) {
                                        cancel();

                                    }

                                    timer --; // -- bedeutet -> zieht immer 1 ab und ++ -> fügt immer 1 dazu

                                    player.sendMessage("Queue wir in " + ChatColor.RED + timer + ChatColor.WHITE + " Sekunden geschlossen.");

                                    if(timer <= 0) {
                                        tpaRequest.remove(player);
                                        player.sendMessage(ChatColor.RED + "Die Anfrage wurde abbgebrochen.");
                                        targetPlayer.sendMessage(ChatColor.DARK_RED + "Die Anfrage wurde abbgebrochen.");
                                        cancel();
                                    }
                                }
                            }.runTaskTimerAsynchronously(plugin, 0, 20);

/* oder
                            new BukkitRunnable() {
                                @Override
                                public void run() {

                                    tpaRequest.remove(player);

                                }
                            }.runTaskLaterAsynchronously(plugin, 20*5);

                            // 20 Ticks = 1 Sekunde
*/
                            return false;

                        }
                    }
                } else {
                    player.sendMessage("Dieser Spieler existiert nicht");
                    return false;
                }
            }
        }
        return false;
    }
}