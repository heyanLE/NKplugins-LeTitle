package cn.Le.Title;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;

/**
 * The listener-class of LeTitle
 * Created by eke_l on 2016/4/2 0002.
 */
public class TitleListener implements Listener {

    private static TitleMain main;

    public void setMain(TitleMain main1){
        main = main1;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event){
        if(main.titleConfig.titleJoinMessage){
            event.setJoinMessage(main.getLanguage("join",event.getPlayer().getName(),new TitleAdmin(event.getPlayer().getName()).getTitle()));
        }
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPlayerChat(PlayerChatEvent event){
        if(main.titleConfig.titleChat){
            event.setCancelled();
            main.getServer().broadcastMessage("["+new TitleAdmin(event.getPlayer().getName()).getTitle()+"§f]"+"<"+event.getPlayer().getName()+">"+event.getMessage());
        }
    }

}
