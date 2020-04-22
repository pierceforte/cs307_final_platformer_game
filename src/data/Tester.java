package data;

import data.levels.LevelData;
import data.user.InvalidLoginException;
import engine.gameobject.GameObject;
import engine.gameobject.platform.Goal;
import engine.gameobject.platform.StationaryHazardPlatform;
import engine.gameobject.platform.StationaryPlatform;
import engine.gameobject.player.SimplePlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) throws InvalidLoginException, ReadSaveException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Long meep = 9l;
        LevelData save = new LevelData();
        List<GameObject> game = new ArrayList<>();
        for (Double count  = 0.0; count <= 32.0; count = count + 1.0) {
            game.add(new StationaryHazardPlatform("resources/images/objects/watertile.png", 10.0, 10.0, count, 12.0));
        }
        game.add(new SimplePlayer("resources/images/avatars/basicsnake.png", 10d, 10d, 9.0, 11.0, 1.0, 1.0));
        game.add(new Goal("resources/images/avatar.png", 10.0, 10.0, 11.0, 9.0));
        for (Double count = 10.0; count < 16; count = count + 1.0) {
            game.add(new StationaryPlatform("resources/images/objects/grasstile.png", 10.0, 10.0, count, 10.0));
        }
        for (Double count = 22.0; count < 25; count = count + 1.0) {
          game.add(new StationaryPlatform("resources/images/objects/grasstile.png", 10.0, 10.0, count, 13.0));
        }
        for (Double count = 24.0; count < 27.0; count = count + 1.0) {
          game.add(new StationaryPlatform("resources/images/objects/grasstile.png", 10.0, 10.0, count, 14.0));
        }
        for (Double count = 10.0; count < 16; count = count + 1.0) {
          game.add(new StationaryPlatform("resources/images/objects/innerblock.png", 10.0, 10.0, count, 11.0));
        }
        save.saveLevel(game, 1);

        List<GameObject> games = save.getSavedLevel(0);
//        List<Class> me = new ArrayList<>();
//        List<Object> obj = new ArrayList<>();
//        System.out.println(me.getClass().toString());
//        System.out.println(obj.getClass().toString());
        //System.out.println(Arrays.toString());
        //User oops = new User("bcb44", "benburnett");
        //NewUser user = new NewUser("pierce", "password", "home.img");
//        Firebase database = new Firebase();
//        Firestore db = database.getDatabase();
//        DocumentReference docRef = db.collection("users").document("alovelace");
//        Map<String, Object> data = new HashMap<>();
//        data.put("first", "Nicole");
//        data.put("last", "Lovelace");
//        //data.put("dob", new int[]{8,28,1998});
//        ApiFuture<WriteResult> result = docRef.set(data);
//        ApiFuture<QuerySnapshot> query = db.collection("users").get();
//        QuerySnapshot querySnapshot = query.get();
//        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println("User: " + document.getId());
//            System.out.println("First: " + document.getString("firstname"));
//            if (document.contains("middle")) {
//                System.out.println("Middle: " + document.getString("middlename"));
//            }
//            System.out.println("Last: " + document.getString("lastname"));
//            System.out.println("Born: " + document.getLong("born"));
//            //System.out.println(": " + document.getLong("born"));
//        }
    }


}
