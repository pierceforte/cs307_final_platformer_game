package data;

import data.user.InvalidLoginException;
import data.user.ReadSaveException;
import engine.gameobject.opponent.Mongoose;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;


public class Tester {
    public static void main(String[] args) throws InvalidLoginException, ReadSaveException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Mongoose mini = new Mongoose(1,1,1);
        Class wee = mini.getClass();
        Constructor ctor = wee.getConstructor();
        System.out.println(ctor.toString());
        Type[] me = ctor.getParameterTypes();
        for (Type param : me) {
            System.out.println(param);
            System.out.println(param.getClass());
        }
        Double eep = 9.0;
        Object we = (Object) eep;
        System.out.println("0" + we.getClass().toString());
        String className = "java.lang.Double";
        Class e = Class.forName(className);
        Mongoose v = Mongoose.class.getConstructor(e, e, e).newInstance(2.2, 2.2, 2.2);
        v.getImgPath();
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
