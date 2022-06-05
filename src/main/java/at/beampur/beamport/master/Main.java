package at.beampur.beamport.master;

import at.beampur.beamport.commands.Commands;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public static Main getInstance;

    /*
        Methode, die beim Plugin starten automatisch ausgeführt wird
     */

    public void onEnable() {

        getInstance = this;
        Bukkit.getLogger().log(Level.SEVERE, "Beamport aktivated!");

        //PluginManager pluginManager = Bukkit.getPluginManager();
        //pluginManager.

        Objects.requireNonNull(this.getCommand("tp")).setExecutor(new Commands());

    }

    /*
        Methode, die beim Plugin beenden automatisch ausgeführt wird
     */

    public void onDisable() {



    }

}
