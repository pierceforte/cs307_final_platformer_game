package data;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        firebase database = new firebase();
        Firestore db = database.getDatabase();
        DocumentReference docRef = db.collection("users").document("alovelace");
        Map<String, Object> data = new HashMap<>();
        data.put("first", "Ada");
        data.put("last", "Lovelace");
        //data.put("dob", new int[]{8,28,1998});
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
            //System.out.println(": " + document.getLong("born"));
        }

    }
}
