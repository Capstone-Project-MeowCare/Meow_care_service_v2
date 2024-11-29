package com.meow_care.meow_care_service.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private static final String SERVICE_ACCOUNT_PATH = "meowcare-22fd8-firebase-adminsdk-phjpa-e44281991d.json";

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // Load service account credentials
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(SERVICE_ACCOUNT_PATH);

        if (serviceAccount == null) {
            throw new IllegalArgumentException("Firebase service account key file not found");
        }

        // Initialize Firebase App
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        return FirestoreClient.getFirestore(firebaseApp);
    }

    @Bean
    public Storage storage() throws IOException {
        // Use the same credentials for Google Cloud Storage
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(SERVICE_ACCOUNT_PATH);

        if (serviceAccount == null) {
            throw new IllegalArgumentException("Firebase service account key file not found");
        }

        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .getService();
    }
}
