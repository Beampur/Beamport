package at.beampur.beamport.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;


public class Commands implements CommandExecutor {

    /*

    /tp spielername
      0      1
    args.lenght == 0
             0         1       2       3
    args[0] targetName

     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {



        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(args.length == 1) {

                String targetName = args[0];
                Player targetPlayer = Bukkit.getPlayer(targetName);

                if(targetPlayer != null) {

                    //  Permission beamport.*

                    String tpPerm = "beamport.tp.target";

                    if(player.hasPermission(tpPerm)) {

                        if (targetPlayer.isOnline()) {

                            Location targetLocation = targetPlayer.getLocation();

                            player.teleport(targetLocation);
                            player.sendMessage("Du hast dich erflogreich zu " + targetPlayer.getName() +
                                    " teleportiert.");

                        } else {

                            player.sendMessage("Der Spieler "+targetPlayer.getName()+" ist nicht Online.");

                        }

                    } else {

                        player.sendMessage("Du hast für diesen Befehle keine Berechtigung.");

                    }
                    return false;

                } else {
                    player.sendMessage("Der eingegebene Spieler wurde nicht gefunden!");
                }

            } else if (args.length == 2) {

                String targetNameA = args[0];
                String targetNameB = args[1];

                Player targetPlayerA = Bukkit.getPlayer(targetNameA);
                Player targetPlayerB = Bukkit.getPlayer(targetNameB);

                    if(targetPlayerA != null && targetPlayerB != null) {

                        String tpOtherPerm = "beamport.tp.other";

                        if(player.hasPermission(tpOtherPerm)){

                            if(targetPlayerA.isOnline() && targetPlayerB.isOnline()) {

                                Location targetLocationB = targetPlayerB.getLocation();
                                targetPlayerA.teleport(targetLocationB);

                                player.sendMessage("Du hast erfolgreich den Spieler " + targetPlayerA.getName()+
                                        " zu " + targetPlayerB.getName() + " teleportiert.");

                                targetPlayerA.sendMessage("Du wurdes soeben zu " + targetPlayerB.getName() +
                                        " teleportiert.");

                                return false;


                            }

                        } else {

                            player.sendMessage("Du hast keine Berechtigung fpr diesen Befehl.");
                            return false;

                        }

                    } else {

                        player.sendMessage("Einer oder beide Spieler wurden nicht gefunden.");
                        return false;
                    }



            }

        } else {

            Bukkit.getLogger().log(Level.SEVERE, "Du bist gar kein Spieler");

        }



        return false;
    }
}
