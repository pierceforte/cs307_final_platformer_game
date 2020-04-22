package data;

import builder.bank.BankItem;
import data.levels.LevelData;
import data.user.InvalidLoginException;
import engine.gameobject.GameObject;
import engine.gameobject.opponent.Mongoose;
import engine.gameobject.opponent.TesterDifferentTypes;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class Tester {
    public static void main(String[] args) throws InvalidLoginException, ReadSaveException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Long meep = 9l;
        LevelData save = new LevelData();
        save.saveTemp(Arrays.asList(new Mongoose("location1.png",1d, 1d, 1d, 1d, 1d), new TesterDifferentTypes("whelp.png", 1d)));
        List<GameObject> list = save.getTempSave();
        for (GameObject item : list) {
            System.out.println(item.getClass().toString());
        }
        List<BankItem> bank = save.getBank(1);
        for (BankItem item : bank) {
            System.out.println(item.getGameObject().getClass().toString());
        }
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
