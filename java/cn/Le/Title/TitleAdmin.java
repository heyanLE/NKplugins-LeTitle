package cn.Le.Title;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eke_l on 2016/4/2 0002.
 * The admin-class of LeTitle
 */
public class TitleAdmin {
    private File file = new File("plugins/LeTitle/Players/").getAbsoluteFile();
    private Config config;
    private static TitleMain main;
    private String name;

    public TitleAdmin(TitleMain main1){
        main = main1;
    }

    public TitleAdmin(String name){
        if(! new File(file + "/"+name+".json").exists()){
            this.file.mkdirs();
            this.config = new Config(file + "/" + name + ".json",Config.JSON);
            this.config.set("Title",main.titleConfig.defaultTitle);
            List titleList = new ArrayList();
            this.config.set("List",titleList);
            this.config.save();
        }
        this.config = new Config(file + "/" + name + ".json",Config.JSON);
        this.name = name;
    }

    /**
     * 获取玩家当前称号
     * @return String 称号
     */
    public String getTitle(){
        return this.config.getString("Title");
    }

    /**
     * 设置玩家当前称号
     * @param title String 称号
     */
    public void setTitle(String title){
        this.config.set("Title",title);
        this.config.save();
        Player player = main.getServer().getPlayer(name);
        if(player != null){
            player.setNameTag("["+getTitle()+"]"+name);
        }
    }

    /**
     * 设置玩家当前称号（称号id）
     * @param i int 称号id
     * @return boolean true 设置成功 false 设置失败 玩家没有拥有这个称号
     */
    public boolean setTitle(int i){
        List titleList = this.getTitleList();
        String title;
        if(titleList.size() >= i && i > 0){
            title = (String) titleList.get(i-1);
            this.config.set("Title",title);
            this.config.save();
            Player player = main.getServer().getPlayer(name);
            if(player != null){
                player.setNameTag("["+getTitle()+"]"+name);
            }
            return true;
        }else{
            return false;
        }

    }

    /**
     * 获取玩家拥有的称号列表
     * @return ArrayList 称号列表
     */
    public List getTitleList(){
        return this.config.getList("List");
    }


    /**
     * 检测玩家是否拥有该称号
     * @param title String 称号
     * @return boolean 是否拥有
     */
    public boolean ifHave(String title){
        ArrayList titleList = (ArrayList) this.config.get("List");
        int index = titleList.indexOf(title);
        if(index == -1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 给玩家增加一个称号
     * @param title String 称号
     */
    public boolean addTitle(String title){
        List titleList = this.config.getStringList("List");
        if(titleList == null){
            titleList = new ArrayList();
            titleList.add(title);
            this.config.set("List",titleList);
            this.config.save();
            return true;
        }else {
            if(titleList.indexOf(title)!=-1){
                return false;
            }else {
                titleList.add(title);
                this.config.set("List",titleList);
                this.config.save();
                return true;
            }
        }

    }

    /**
     * 删除玩家的一个称号
     * @param title String 称号
     * @return boolean true 删除成功 false 删除失败 玩家没有这个称号
     */
    public boolean delTitle(String title){
        List titleList = this.config.getStringList("List");
        int index = titleList.indexOf(title);
        if(index == -1){
            return false;
        }else{
            titleList.remove(title);
            this.config.set("List",titleList);
            this.config.save();
            return true;
        }
    }


}
