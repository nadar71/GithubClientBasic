package com.mapelli.simone.githubclient.data.dummy;

import android.content.Intent;

import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Helper class for providing sample content Replace all uses of this class before publishing your app.
public class DummyContent {

    // An array of sample (dummy) items.
    public static final List<UserProfile_Mini> ITEMS = new ArrayList<UserProfile_Mini>();

    // A map of sample (dummy) items, by ID.
    public static final Map<String, UserProfile_Mini> ITEM_MAP = new HashMap<String, UserProfile_Mini>();

    private static final int COUNT = 100;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(UserProfile_Mini item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getLogin(), item);
    }

    private static UserProfile_Mini createDummyItem(int position) {
        return new UserProfile_Mini(Integer.toString(position),"nadar71 " + position,
                "https://avatars0.githubusercontent.com/u/3753994?v=4",
                "nadar71 " + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
