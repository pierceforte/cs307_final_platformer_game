package data;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Firebase {

    Firestore db;

    public Firebase() throws IOException {
        // Use a service account
        InputStream serviceAccount = new FileInputStream("src/data/ServiceAccountKey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        // TODO: fix this
        try {
            FirebaseApp.initializeApp(options);
        }
        catch (IllegalStateException e) {
            // don't try to re-initialize
        }
        db = FirestoreClient.getFirestore();
    }

    public Firestore getDatabase() {
        return db;
    }
}