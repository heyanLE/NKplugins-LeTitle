package cn.Le.Title;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.File;


/**
 * Created by eke_l on 2016/4/2 0002.
 * The main-class of LeTitle
 */
public class TitleMain extends PluginBase{

    static TitleConfig titleConfig;

    @Override
    public void onEnable(){
        this.getLogger().info(TextFormat.GREEN + "称号插件 hello");
        this.getServer().getPluginManager().registerEvents(new TitleListener(),this);

        new TitleListener().setMain(this);
        new TitleAdmin(this);

        this.saveResource("config.yml");
        this.saveResource("chs.json");
        this.saveResource("eng.json");

        titleConfig = new TitleConfig(this);
        titleConfig.load();

    }
    @Override
    public void onDisable(){
        this.getLogger().info(TextFormat.RED + "称号插件 see you");
    }

    /**
     * 获取语言文件中的语句
     * @param sentenceName 语句名字
     * @param name &变量设置
     * @param title %变量设置
     * @return 语句
     */
    String getLanguage(String sentenceName,String name,String title){
        File file = new File("plugins/LeTitle/").getAbsoluteFile();
        String language = titleConfig.language;
        Config config = new Config(file + "/" + language + ".json",Config.JSON);
        String sentence = config.getString(sentenceName);
        String sentence1;
        String sentence2;
        sentence1 = sentence.replace("&",name);
        sentence2 = sentence1.replace("%",title);
        return sentence2;
    }

    /**
     * 获取语言文件中的语句
     * @param sentenceName 语句名字
     * @param title %变量设置
     * @return 语句
     */
    String getLanguage(String sentenceName,String title){
        File file = new File("plugins/LeTitle/").getAbsoluteFile();
        String language = titleConfig.language;
        Config config = new Config(file + "/" + language + ".json",Config.JSON);
        String sentence = config.getString(sentenceName);
        String sentence1;
        sentence1 = sentence.replace("%",title);
        return sentence1;
    }

    /**
     * 获取语言文件中的语句
     * @param sentenceName 语句名字
     * @return 语句
     */
    String getLanguage(String sentenceName){
        File file = new File("plugins/LeTitle/").getAbsoluteFile();
        String language = titleConfig.language;
        Config config = new Config(file + "/" + language + ".json",Config.JSON);
        String sentence = config.getString(sentenceName);
        return sentence;
    }

    /**
     * 检测字符串是否纯数字
     * @param str 字符串
     * @return boolean 是否纯数字
     */
    public boolean ifNum(String str){
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 命令方法
     * @param sender 命令使用者
     * @param command 命令的相关信息
     * @param label 命令的名字
     * @param args 命令的参数
     * @return 未知
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String help = "";
        for(int i = 1;i <= 13 ; i++){
            help = help + "\n" + this.getLanguage("help" + i);
        }
        Player player = this.getServer().getPlayer(sender.getName());
        switch (command.getName()){
            case "tit":
                switch (args.length){
                    case 0:
                        if(player == null){
                            this.getLogger().info(help);
                        }else{
                            sender.sendMessage(help);
                        }
                        break;
                    default:
                        switch (args[0]){
                            case "set":
                                switch (args.length){
                                    case 2:
                                        if(player == null){
                                            String str = this.getLanguage("is_no_player");
                                            this.getLogger().info(str);
                                            break;
                                        }else{
                                            if(sender.isOp()){
                                                TitleAdmin admin = new TitleAdmin(sender.getName());
                                                admin.setTitle(args[1]);
                                                String str = this.getLanguage("set_title_himself",args[1]);
                                                sender.sendMessage(str);
                                            }else{
                                                String str = this.getLanguage("is_not_op");
                                                sender.sendMessage(str);
                                            }
                                        }
                                    case 3:
                                        if(player == null || player.isOp()){
                                            TitleAdmin admin = new TitleAdmin(args[1]);
                                            admin.setTitle(args[2]);
                                            if(this.getServer().getPlayer(args[1]) != null){
                                                String str = this.getLanguage("be_set_title",args[2]);
                                                this.getServer().getPlayer(args[1]).sendMessage(str);
                                            }
                                            if(player == null){
                                                String str = this.getLanguage("set_title",args[1],args[2]);
                                                this.getLogger().info(str);
                                            }else{
                                                String str = this.getLanguage("set_title",args[1],args[2]);
                                                sender.sendMessage(str);
                                            }
                                            break;
                                        }else{
                                            String str = this.getLanguage("is_not_op");
                                            sender.sendMessage(str);
                                            break;
                                        }
                                    default:
                                        if(player == null){
                                            this.getLogger().info(help);
                                        }else{
                                            sender.sendMessage(help);
                                        }
                                }
                                break;
                            case "def":
                                switch (args.length){
                                    case 1:
                                        if(player == null){
                                            String str = this.getLanguage("is_not_player");
                                            this.getLogger().info(str);
                                            break;
                                        }else{
                                            TitleAdmin admin = new TitleAdmin(sender.getName());
                                            admin.setTitle(titleConfig.defaultTitle);
                                            String str = this.getLanguage("set_title_def_himself",titleConfig.defaultTitle);
                                            sender.sendMessage(str);
                                        }
                                    case 2:
                                        if(player == null || player.isOp()){
                                            TitleAdmin admin = new TitleAdmin(args[1]);
                                            admin.setTitle(titleConfig.defaultTitle);
                                            if(this.getServer().getPlayer(args[1]) != null){
                                                String str = this.getLanguage("be_set_title_def",titleConfig.defaultTitle);
                                                this.getServer().getPlayer(args[1]).sendMessage(str);
                                            }
                                            if(player == null){
                                                String str = this.getLanguage("set_title_def",args[1],titleConfig.defaultTitle);
                                                this.getLogger().info(str);
                                            }else{
                                                String str = this.getLanguage("set_title_def",args[1],titleConfig.defaultTitle);
                                                sender.sendMessage(str);
                                            }
                                            break;
                                        }else{
                                            String str = this.getLanguage("is_not_op");
                                            sender.sendMessage(str);
                                            break;
                                        }
                                    default:
                                        if(player == null){
                                            this.getLogger().info(help);
                                        }else{
                                            sender.sendMessage(help);
                                        }
                                }
                                break;
                            case "use":
                                switch (args.length){
                                    case 2:
                                        if(player != null) {
                                            TitleAdmin admin = new TitleAdmin(sender.getName());
                                            if (this.ifNum(args[1])) {
                                                String str = this.getLanguage("is_not_num");
                                                sender.sendMessage(str);
                                            }else{
                                                if(admin.setTitle( Integer.parseInt(args[1]))){
                                                    String str = this.getLanguage("set_title_himself",args[1]+"、"+admin.getTitle());
                                                    sender.sendMessage(str);
                                                }else{
                                                    String str = this.getLanguage("use_not_have");
                                                    sender.sendMessage(str);
                                                }
                                            }
                                            break;
                                        }else{
                                            String str = this.getLanguage("is_not_player");
                                            this.getLogger().info(str);
                                            break;
                                        }
                                    default:
                                        if(player == null){
                                            this.getLogger().info(help);
                                        }else{
                                            sender.sendMessage(help);
                                        }
                                }
                                break;
                            case "see":
                                switch (args.length){
                                    case 1:
                                        if(player == null){
                                            String str = this.getLanguage("is_not_player");
                                            this.getLogger().info(str);
                                            break;
                                        }else{
                                            TitleAdmin admin = new TitleAdmin(sender.getName());
                                            String str = this.getLanguage("see_title",admin.getTitle());
                                            sender.sendMessage(str);
                                            break;
                                        }
                                    case 2:
                                        TitleAdmin admin = new TitleAdmin(args[1]);
                                        if(player == null){
                                            String str = this.getLanguage("see_title",admin.getTitle());
                                            this.getLogger().info(str);
                                            break;
                                        }else{
                                            String str = this.getLanguage("see_title",admin.getTitle());
                                            sender.sendMessage(str);
                                            break;
                                        }
                                    default:
                                        if(player == null){
                                            this.getLogger().info(help);
                                        }else{
                                            sender.sendMessage(help);
                                        }
                                }
                                break;
                            case "list":
                                switch ((args.length)){
                                    case 1:
                                        if(player != null){
                                            TitleAdmin admin = new TitleAdmin(sender.getName());
                                            List titleList = admin.getTitleList();
                                            String str = this.getLanguage("see_title_list");
                                            sender.sendMessage(str);
                                            for(int i = 0 ; i < titleList.size() ; i++){
                                                sender.sendMessage((i+1) + "、" + titleList.get(i));
                                            }
                                            break;
                                        }else{
                                            String str = this.getLanguage("is_not_player");
                                            this.getLogger().info(str);
                                            break;
                                        }
                                    case 2:
                                        if(player == null || sender.isOp()){
                                            TitleAdmin admin = new TitleAdmin(args[1]);
                                            List titleList = admin.getTitleList();
                                            if(player == null){
                                                String str = this.getLanguage("see_title_list_him",args[1]);
                                                this.getLogger().info(str);
                                            }else{
                                                String str = this.getLanguage("see_title_list_him",args[1]);
                                                sender.sendMessage(str);
                                            }
                                            for(int i = 0 ; i < titleList.size() ; i++){
                                                if(player == null){
                                                    this.getLogger().info((i+1) + "、" + titleList.get(i));
                                                }else{
                                                    sender.sendMessage((i+1) + "、" + titleList.get(i));
                                                }
                                            }
                                            break;
                                        }else{
                                            String str = this.getLanguage("is_not_op");
                                            sender.sendMessage(str);
                                        }
                                    default:
                                        if(player == null){
                                            this.getLogger().info(help);
                                        }else{
                                            sender.sendMessage(help);
                                        }
                                }
                                break;
                            case "give":
                                switch (args.length){
                                    case 3:
                                        if(player == null || sender.isOp()){
                                            TitleAdmin admin = new TitleAdmin(args[1]);
                                            if(admin.addTitle(args[2])){
                                                if(player == null){
                                                    String str = this.getLanguage("give_title",args[1],args[2]);
                                                    this.getLogger().info(str);
                                                }else{
                                                    String str = this.getLanguage("give_title",args[1],args[2]);
                                                    sender.sendMessage(str);
                                                }
                                                if(this.getServer().getPlayer(args[1]) != null){
                                                    String str = this.getLanguage("be_give_op_title",args[2]);
                                                    this.getServer().getPlayer(args[1]).sendMessage(str);
                                                }
                                                break;
                                            }else{
                                                if(player == null){
                                                    String str = this.getLanguage("give_have");
                                                    this.getLogger().info(str);
                                                }else{
                                                    String str = this.getLanguage("give_have");
                                                    sender.sendMessage(str);
                                                }
                                            }
                                            break;


                                        }else{
                                            TitleAdmin adminS = new TitleAdmin(sender.getName());
                                            TitleAdmin adminA = new TitleAdmin(args[1]);
                                            if(adminS.ifHave(args[2])){
                                                adminS.delTitle(args[2]);
                                                adminA.addTitle(args[2]);
                                                String str = this.getLanguage("give_title",args[1],args[2]);
                                                sender.sendMessage(str);
                                                if(this.getServer().getPlayer(args[1]) != null){
                                                    String str1 = this.getLanguage("be_give_title",sender.getName(),args[2]);
                                                    this.getServer().getPlayer(args[1]).sendMessage(str1);
                                                }
                                            }else{
                                                String str = this.getLanguage("is_not_have");
                                                sender.sendMessage(str);
                                            }
                                            break;
                                        }
                                    default:
                                        if(player == null){
                                            this.getLogger().info(help);
                                        }else{
                                            sender.sendMessage(help);
                                        }
                                }
                                break;
                            case "del":
                                switch (args.length){
                                    case 2:
                                        if(player != null){
                                            TitleAdmin admin = new TitleAdmin(sender.getName());
                                            if(admin.delTitle(args[1])){
                                                String str = this.getLanguage("set_title_del_himself",args[1]);
                                                sender.sendMessage(str);
                                            }else{
                                                String str = this.getLanguage("is_not_have");
                                                sender.sendMessage(str);
                                            }
                                            break;
                                        }else{
                                            String str = this.getLanguage("is_not_player");
                                            this.getLogger().info(str);
                                            break;
                                        }
                                    case 3:
                                        if(player == null || sender.isOp()){
                                            TitleAdmin admin = new TitleAdmin(args[1]);
                                            if(admin.delTitle(args[2])){
                                                String str = this.getLanguage("set_title_del",args[1],args[2]);
                                                sender.sendMessage(str);
                                            }else{
                                                String str = this.getLanguage("is_not_have");
                                                sender.sendMessage(str);
                                            }
                                            break;
                                        }else{
                                            String str = this.getLanguage("is_not_op");
                                            sender.sendMessage(str);
                                            break;
                                        }
                                    default:
                                        if(player == null){
                                            this.getLogger().info(help);
                                        }else{
                                            sender.sendMessage(help);
                                        }
                                }
                                break;
                            default:
                                if(player == null){
                                    this.getLogger().info(help);
                                }else{
                                    sender.sendMessage(help);
                                }
                        }
                }

        }
        return true;
    }




}
