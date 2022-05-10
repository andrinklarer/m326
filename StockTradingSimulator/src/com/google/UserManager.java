package com.google;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    public static User currentUser = new User("admin", "admin", new Portfolio(100000, new ArrayList<>()));
    private static List<User> users = new ArrayList<>();

    /**
     * This method load the user from a file
     */
    public static void loadUsers(){
        users = (List<User>) Filehandler.readFile(DefaultValues.USER_DATA_FILE_PATH);
    }

    /**
     * This method will save the users into a file
     */
    public static void save() {
        Filehandler.writeFile(DefaultValues.USER_DATA_FILE_PATH, users);
    }

    /**
     * This method will return a user based on the username
     * @param username the username of a user
     * @return either a user if found or null
     */
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
