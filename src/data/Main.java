package data;

import data.user.InvalidLoginException;
import data.user.ReadSaveException;
import data.user.User;

public class Main {
    public static void main(String[] args) throws InvalidLoginException, ReadSaveException {
        User oops = new User("bcb44", "benburnett");
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
