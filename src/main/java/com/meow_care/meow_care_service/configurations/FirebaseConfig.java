package com.meow_care.meow_care_service.configurations;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        // Load the service account key file from resources
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("meowcare-22fd8-firebase-adminsdk-phjpa-e44281991d.json");

        if (serviceAccount == null) {
            throw new IllegalArgumentException("Firebase service account key file not found");
        }

        // Create Firebase options
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        // Initialize Firebase if not already initialized
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }

}
