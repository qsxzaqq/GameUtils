package cc.i9mc.gameutils.chat;

import cc.i9mc.gameutils.BukkitGameUtils;
import cc.i9mc.gameutils.GameUtilsAPI;
import cc.i9mc.gameutils.listeners.bukkit.ChatListener;
import cc.i9mc.gameutils.utils.LoggerUtil;
import cc.i9mc.k8sgameack.K8SGameACK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatHandler {
    private final BukkitGameUtils main;
    private boolean k8s = false;

    public ChatHandler(BukkitGameUtils main) {
        this.main = main;

        k8s = main.getServer().getPluginManager().isPluginEnabled("K8SGameACK");

        load();
    }

    public void load() {
        loadChat(K8SGameACK.getInstance().getServerData().getGameType());

        if (k8s) {
            return;
        }

        main.getConnectionPoolHandler().registerDatabase("bungee");
        String group = null;

        try {
            Connection connection = main.getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM LobbyChatGroups Where server=?;");
            preparedStatement.setString(1, GameUtilsAPI.getServerName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                loadChat(resultSet.getString("group"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadChat(String group) {
        GameUtilsAPI.setLobbyChatGroup(group);
        LoggerUtil.info("ChatSync Group :" + group);

        main.getConnectionPoolHandler().registerDatabase("bungee");

        try {
            Connection connection = main.getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM LobbyChatConfig Where `group`=?;");
            preparedStatement.setString(1, group);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                GameUtilsAPI.setLobbyChatConfig(resultSet.getString("config"));
                GameUtilsAPI.setLobbyChatHover(resultSet.getString("hover"));
                new ChatListener(main);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            if (group == null) {
                main.getConnectionPoolHandler().unregisterDatabase("bungee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        if (k8s) {
            return;
        }

        main.getConnectionPoolHandler().registerDatabase("bungee");
        String group = null;

        try {
            Connection connection = main.getConnectionPoolHandler().getConnection("bungee");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM LobbyChatGroups Where server=?;");
            preparedStatement.setString(1, GameUtilsAPI.getServerName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                GameUtilsAPI.setLobbyChatGroup(resultSet.getString("group"));
                group = resultSet.getString("group");
            }
            LoggerUtil.info("ChatSync Group :" + group);

            if (group != null) {
                preparedStatement = connection.prepareStatement("SELECT * FROM LobbyChatConfig Where `group`=?;");
                preparedStatement.setString(1, group);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    GameUtilsAPI.setLobbyChatConfig(resultSet.getString("config"));
                    GameUtilsAPI.setLobbyChatHover(resultSet.getString("hover"));
                }
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            if (group == null) {
                main.getConnectionPoolHandler().unregisterDatabase("bungee");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
