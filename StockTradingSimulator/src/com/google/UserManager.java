package com.google;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static List<User> users = new ArrayList<>();
    private static final String userFilePath = "users.txt";

    public static void loadUsers(){
        users = (List<User>) Filehandler.readFile(userFilePath);
    }

    public static void save() {
        Filehandler.writeFile(userFilePath, users);
    }

    public static User getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> stocks) {
        UserManager.users = stocks;
    }

}
