package data;

/*
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        firebase database = new firebase();
        Firestore db = database.getDatabase();
        DocumentReference docRef = db.collection("users").document("alovelace");
        Map<String, Object> data = new HashMap<>();
        data.put("firstname", "Ada");
        data.put("lastname", "Lovelace");
        ArrayList<Long> numss = new ArrayList<>(Arrays.asList(8L, 28L, 1998L));
        data.put("dob", numss);
        ApiFuture<WriteResult> result = docRef.set(data);
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println("User: " + document.getId());
            System.out.println("First: " + document.getString("firstname"));
            if (document.contains("middle")) {
                System.out.println("Middle: " + document.getString("middlename"));
            }
            System.out.println("Last: " + document.getString("lastname"));
            System.out.println("Born: " + document.getLong("born"));
            if (document.contains("dob")) {
                List<Long> num = (ArrayList<Long>) document.get("dob");
                for (Long nums : num) {
                    System.out.println(nums);
                }
            }
        }

    }
}
*/