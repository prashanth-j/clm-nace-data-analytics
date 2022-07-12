package com.clm.nace.clmnacedataanalytics.controller;

import com.clm.nace.clmnacedataanalytics.entity.FileAttachment;
import com.clm.nace.clmnacedataanalytics.entity.NaceDataEntity;
import com.clm.nace.clmnacedataanalytics.exception.ApplicationException;
import com.clm.nace.clmnacedataanalytics.exception.ApplicationExceptionHandler;
import com.clm.nace.clmnacedataanalytics.model.ResponseData;
import com.clm.nace.clmnacedataanalytics.service.FileAttachmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AttachmentControllerTest {

    @InjectMocks
    private AttachmentController attachmentController;

    @Mock
    private FileAttachmentService fileAttachmentService;


    @BeforeEach()
    public void init() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setContextPath("/your-app-context");
        ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attrs);
    }

    @Test
    public void testShouldAbleToSaveTheMultipartCSVFileTODbReturnReponseData() throws Exception, ApplicationException {

        MockMultipartFile csvFile = new MockMultipartFile("test.json", "", "text/csv", "1,2,3".getBytes());
        List<NaceDataEntity> naceDataEntityList = new ArrayList<>();
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setId("test");
        Mockito.when(fileAttachmentService.saveNaceDetails((Mockito.any()))).thenReturn(fileAttachment);
        ResponseEntity responseData = attachmentController.putNaceDetails(csvFile);
        assertNotNull(responseData);
    }

/*    @Test
    public void testShouldAbleToDownloadStoreCSVFileFromDb() throws Exception {
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setId("test");
        fileAttachment.setFileName("testFile");
        fileAttachment.setFileType("text/csv");
        byte[] CDRIVES = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        fileAttachment.setData(CDRIVES);
        Mockito.when(fileAttachmentService.getAttachmentFile(Mockito.any())).thenReturn(fileAttachment);
        ResponseEntity<Resource> downloadFileData = attachmentController.downloadFile("csvFile");
        assertNotNull(downloadFileData);
        assertEquals(200, downloadFileData.getStatusCodeValue());
    }*/

    @Test
    public void testShouldAbleToreadNaceDetails() throws Exception {

        ResponseEntity<List<NaceDataEntity>> naceDataEntities =
                attachmentController.readNaceDetails();
        assertNotNull(naceDataEntities);
        assertEquals(200, naceDataEntities.getStatusCodeValue());

    }

    @Test
    public void testShouldAblereadNaceDetailsByOrder() throws Exception {
        NaceDataEntity data = new NaceDataEntity();
        Optional<NaceDataEntity> opt = Optional.of(data);
        Mockito.when(fileAttachmentService.readNaceDetailsByOrder(any())).thenReturn(opt);
        ResponseEntity<NaceDataEntity> naceDataEntity =
                attachmentController.readNaceDetailsByOrder("test");
        assertNotNull(naceDataEntity);
        assertTrue(opt.isPresent());
    }
}