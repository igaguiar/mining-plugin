package igaodev.miningplugin.manager;

import igaodev.miningplugin.data.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Map<UUID, User> userMap = new HashMap<>();

    public void createUser(UUID uniqueId) {
        User user = new User(uniqueId);
        userMap.put(uniqueId, user);
    }

    public User getUser(UUID uniqueId) {
        return userMap.get(uniqueId);
    }
}
