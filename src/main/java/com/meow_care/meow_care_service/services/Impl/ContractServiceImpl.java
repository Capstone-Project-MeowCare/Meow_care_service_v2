package com.meow_care.meow_care_service.services.Impl;

import com.lowagie.text.pdf.BaseFont;
import com.meow_care.meow_care_service.dto.contract.ContractCreateRequestDto;
import com.meow_care.meow_care_service.dto.contract.ContractDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Contract;
import com.meow_care.meow_care_service.entities.ContractTemplate;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.ContractMapper;
import com.meow_care.meow_care_service.repositories.ContractFileRepository;
import com.meow_care.meow_care_service.repositories.ContractRepository;
import com.meow_care.meow_care_service.services.ContractService;
import com.meow_care.meow_care_service.services.ContractTemplateService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class ContractServiceImpl extends BaseServiceImpl<ContractDto, Contract, ContractRepository, ContractMapper>
        implements ContractService {

    private final ContractTemplateService contractTemplateService;

    private final ContractFileRepository contractFileRepository;

    public ContractServiceImpl(ContractRepository repository, ContractMapper mapper, ContractTemplateService contractTemplateService, ContractFileRepository contractFileRepository) {
        super(repository, mapper);
        this.contractTemplateService = contractTemplateService;
        this.contractFileRepository = contractFileRepository;
    }

    @Override
    public ApiResponse<ContractDto> create(ContractCreateRequestDto contractCreateRequestDto) {

        ContractTemplate contractTemplate = contractTemplateService.findEntityById(contractCreateRequestDto.getTemplateId());

        try {
            ByteArrayOutputStream byteArrayOutputStream = generatePdfFromTemplate(contractTemplate.getContent(), contractCreateRequestDto.getData());
            String fileName = contractTemplate.getName() + "_" + System.currentTimeMillis() + ".pdf";
            String outputUrl = contractFileRepository.savePdfFileToFirebase(fileName, byteArrayOutputStream);

            Contract contract = Contract.builder()
                    .user(User.builder().id(contractCreateRequestDto.getUserId()).build())
                    .sitter(User.builder().id(contractCreateRequestDto.getSitterId()).build())
                    .contractUrl(outputUrl)
                    .build();
            contract = repository.save(contract);
            return ApiResponse.success(mapper.toDto(contract));

        } catch (Exception e) {
            throw new ApiException(ApiStatus.ERROR, "Failed to generate PDF from template");
        }
    }

    private ByteArrayOutputStream  generatePdfFromTemplate(String template, Map<String, String> data) throws Exception {
        // Replace placeholders
        for (Map.Entry<String, String> entry : data.entrySet()) {
            template = template.replace("[" + entry.getKey() + "]", entry.getValue());
        }

        // Generate PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        // Load the custom font
        renderer.getFontResolver().addFont(
                "src/main/resources/fonts/Roboto-Regular.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );

        template = addContentToBody(template, "Contract");
        renderer.setDocumentFromString(template);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream;
    }

    private String addContentToBody(String bodyContent, String title) {
        return """
                <!DOCTYPE html>
                <html xmlns="http://www.w3.org/1999/xhtml">
                <head>
                    <title>""" + title + """
                    </title>
                    <style>
                        @font-face {
                            font-family: 'Roboto';
                            src: url('file:src/main/resources/fonts/Roboto-Regular.ttf') format('truetype');
                        }
                        body {
                            font-family: 'Roboto', sans-serif;
                        }
                    </style>
                </head>
                <body>
                """ + bodyContent + """
                </body>
                </html>
                """;
    }
}
