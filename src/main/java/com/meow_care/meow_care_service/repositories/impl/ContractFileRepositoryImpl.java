package com.meow_care.meow_care_service.repositories.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.meow_care.meow_care_service.repositories.ContractFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;

@Repository
@RequiredArgsConstructor
public class ContractFileRepositoryImpl implements ContractFileRepository {

    private final Storage storage;

    private final String folderName = "contracts";

    private final String bucketName = "meowcare-22fd8.appspot.com";


    @Override
    public String savePdfFileToFirebase(String fileName, ByteArrayOutputStream pdfStream) {
        // Define the Blob ID and Blob Info
        BlobId blobId = BlobId.of(bucketName, folderName + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/pdf").build();

        // Upload the PDF to Firebase Storage
        storage.create(blobInfo, pdfStream.toByteArray());

        // Return the download URL
        return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s%%2F%s?alt=media", bucketName, folderName, fileName);
    }
}
