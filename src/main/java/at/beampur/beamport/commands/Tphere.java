package at.beampur.beamport.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Tphere implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(args.length == 1) {

                String targetName = args[0];
                Player targetPlayer = Bukkit.getPlayer(targetName);

                if(targetPlayer != null) {

                    String tpherePerm = "beamport.tp.here";

                    if(targetPlayer.hasPermission(tpherePerm)) {

                        if(targetPlayer.isOnline()) {

                            Location targetLocation = targetPlayer.getLocation();

                            player.teleport(targetLocation);
                            player.sendMessage("Du hast erfolgreich den Spieler"
                                    + targetPlayer.getName() + " zu dir teleportiert.");

                            return false;

                        } else {

                            player.sendMessage("Der Spieler " + targetPlayer.getName() + " ist nicht online.");

                        }

                    } else {

                        player.sendMessage("Du hast keine Berechtigung, diesen Befehl auszuführen.");

                    }

                }

            }

        } else {

            Bukkit.getLogger().log(Level.SEVERE, "Du bist kein Spieler.");

        }



        return false;
    }
}
