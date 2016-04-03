package cn.Le.Title;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.SimpleConfig;

/**
 * The config-class of LeTitle
 * Created by eke_l on 2016/4/2 0002.
 */
public class TitleConfig extends SimpleConfig {

    public TitleConfig(Plugin plugin){
        super(plugin);
    }

    @Path(value = "Default_Title")
    public String defaultTitle="XLE";

    @Path(value = "Title_Chat")
    public boolean titleChat=true;

    @Path(value = "Title_Join_Message")
    public boolean titleJoinMessage=true;

    @Path(value = "Language")
    public String language = "chs";
}
