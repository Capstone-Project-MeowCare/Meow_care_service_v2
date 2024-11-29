package com.meow_care.meow_care_service.repositories;

import java.io.ByteArrayOutputStream;

public interface ContractFileRepository {
    String savePdfFileToFirebase(String fileName, ByteArrayOutputStream pdfStream);
}
