package data;

import builder.bank.BankController;
import data.levels.LevelData;
import data.user.InvalidLoginException;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Enemy;
import engine.gameobject.platform.CheckPoint;
import engine.gameobject.platform.Goal;
import engine.gameobject.platform.StationaryHazardPlatform;
import engine.gameobject.platform.StationaryPlatform;
import engine.gameobject.player.SimplePlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) throws InvalidLoginException, ReadSaveException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        LevelData ld = new LevelData();
        BankController bc = ld.getBank(0);
        bc.getBankModel();
        List<GameObject> go = new ArrayList<>();
        go.add(new StationaryPlatform("resources/images/objects/grasstile.png", 1d, 1d, 3d,2d));
        go.add(new CheckPoint("resources/images/objects/checkpoint.png", 1d, 2d, 27d,3d));
        for (Double i = 25d; i < 30; i++) {
            go.add(new StationaryPlatform("resources/images/objects/grasstile.png", 1d, 1d, i,4d));
        }
        for (Double i = 18d; i < 21; i++) {
            go.add(new StationaryPlatform("resources/images/objects/grasstile.png", 1d, 1d, i,6d));
        }
        for (Double i = 7d; i < 25; i++) {
            go.add(new StationaryPlatform("resources/images/objects/siderock.png", 1d, 1d, 18d,i));
        }
        for (Double i = 7d; i < 25; i++) {
            go.add(new StationaryPlatform("resources/images/objects/innerblock.png", 1d, 1d, 19d,i));
        }
        for (Double i = 7d; i < 25; i++) {
            go.add(new StationaryPlatform("resources/images/objects/siderock.png", 1d, 1d, 20d,i));
        }
        go.add(new Enemy("dontusethis.png", 1d, 1d, 11d, 12d, 1d, 1d));
        for (Double i = 11d; i < 15; i++) {
            go.add(new StationaryPlatform("resources/images/objects/grasstile.png", 1d, 1d, 12d,i));
        }
        go.add(new CheckPoint("resources/images/objects/checkpoint.png", 1d, 1d, 3d, 16d));
        for (Double i = 1d; i < 6; i++) {
            go.add(new StationaryPlatform("resources/images/objects/grasstile.png", 1d, 1d, i, 17d));
        }
        go.add(new Enemy("dontusethis.png", 1d, 1d, 15d, 22d, 1d, 1d));
        for (Double i = 13d; i < 18; i++) {
            go.add(new StationaryPlatform("resources/images/objects/grasstile.png", 1d, 1d, i, 23d));
        }
        for (Double i = 13d; i < 18; i++) {
            go.add(new StationaryPlatform("resources/images/objects/innerblocks.png", 1d, 1d, i, 24d));
        }
        for (Double i = 1d; i < 31; i++) {
            go.add(new StationaryPlatform("resources/images/objects/grasstile.png", 1d, 1d, i,25d));
        }
        ld.saveLevel(go, 0);

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
