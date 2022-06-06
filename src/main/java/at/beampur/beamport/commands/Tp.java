package at.beampur.beamport.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;


public class Tp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        //wird abgefragt ob der Sender ein Spieler ist
        if(sender instanceof Player) {

            Player player = (Player) sender;

            /*

            /HAUPTKOMMAND SUBKOMMAND1 <- args.length == 1
                            args[0]
            /HAUPTKOMMAND SUBKOMMAND1 SUBKOMMAND2 <- args.length == 2
                            args[0]     args[1]
            /HAUPTKOMMAND SUBKOMMAND1 SUBKOMMAND2 SUBKOMMAND3 <- args.length == 3
                            args[0]     args[1]     args[2]

            args.length == 1 <- Subkommand anzahl
            args.length == 2 <- hängen zwei weitere wörter an dem Haupt Befehl

             */

            if(args.length == 1) {

                String targetName = args[0];
                Player targetPlayer = Bukkit.getPlayer(targetName);

                if(targetPlayer != null) {

                    //  Permission wird erstellt
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


                                //bei msg´s nicht vergessen .getName (für den namen)
                                player.sendMessage("Du hast erfolgreich den Spieler " + targetPlayerA.getName()+
                                        " zu " + targetPlayerB.getName() + " teleportiert.");

                                targetPlayerA.sendMessage("Du wurdes soeben zu " + targetPlayerB.getName() +
                                        " teleportiert.");

                                return false;

                            }

                        } else {

                            player.sendMessage("Du hast keine Berechtigung für diesen Befehl.");
                            return false;

                        }

                    } else {

                        player.sendMessage("Einer oder beide Spieler wurden nicht gefunden.");
                        return false;
                    }



            }

        } else {

            //MSG an die Console Level.SVERE (in dem fall eine Error message)
            Bukkit.getLogger().log(Level.SEVERE, "Du bist gar kein Spieler");

        }



        return false;
    }
}
