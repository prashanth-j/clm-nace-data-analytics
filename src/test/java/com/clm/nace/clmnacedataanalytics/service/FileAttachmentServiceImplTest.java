package com.clm.nace.clmnacedataanalytics.service;

import com.clm.nace.clmnacedataanalytics.controller.AttachmentController;
import com.clm.nace.clmnacedataanalytics.entity.FileAttachment;
import com.clm.nace.clmnacedataanalytics.entity.NaceDataEntity;
import com.clm.nace.clmnacedataanalytics.model.NaceData;
import com.clm.nace.clmnacedataanalytics.repository.ClmNaceRepository;
import com.clm.nace.clmnacedataanalytics.repository.FileAttachmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FileAttachmentServiceImplTest {

    @InjectMocks
    private FileAttachmentServiceImpl fileAttachmentService;

    @Mock
    private FileAttachmentRepository fileAttachmentRepository;

    @Mock
    private ClmNaceRepository clmNaceRepository;

    @Test
    public void testShouldAbleToSaveNaceDetails() throws Exception {
        MockMultipartFile csvFile = new MockMultipartFile("test.json", "", "text/csv", "1,2,3".getBytes());
        List<NaceDataEntity> naceDataEntityList = new ArrayList<>();
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setId("test");
        fileAttachment.setFileType("text/csv");
        fileAttachment.setFileName("test.csv");
        Mockito.when(fileAttachmentRepository.save(Mockito.any())).thenReturn(fileAttachment);
        FileAttachment attachment = fileAttachmentService.saveNaceDetails(csvFile);
        assertNotNull(attachment);
        assertNotEquals("text/csv", attachment.getFileType());
    }

    @Test
    public void testShouldAbleToGetAttachmentFile() throws Exception {
        FileAttachment fileAttachment = new FileAttachment();
        fileAttachment.setId("test");
        fileAttachment.setFileType("text/csv");
        fileAttachment.setFileName("test.csv");
        Optional<FileAttachment> opt = Optional.of(fileAttachment);
        Mockito.when(fileAttachmentRepository.findById(Mockito.any())).thenReturn(opt);
        FileAttachment fileAttachmentResponse = fileAttachmentService.getAttachmentFile("csvFile");
        assertNotNull(fileAttachmentResponse);
        assertNotEquals("text/csv", fileAttachmentResponse.getFileType());
    }

    @Test
    public void testShouldAbleTosaveNaceDetailsAsList() {
        List<NaceData> naceDataList = new ArrayList<>();
        NaceData naceData = new NaceData();
        naceData.setReference("test");
        naceData.setRulings("er");
        naceDataList.add(naceData);
        fileAttachmentService.saveNaceDetailsAsList(naceDataList);
        // assertNotNull(naceDataEntities);
    }

    @Test
    public void testShouldAbleToReadNaceDetails() {
        List<NaceDataEntity> naceDataList = new ArrayList<>();
        NaceDataEntity naceData = new NaceDataEntity();
        naceData.setReference("test");
        naceData.setRulings("er");
        naceDataList.add(naceData);
        Mockito.when(clmNaceRepository.findAll()).thenReturn(naceDataList);
        List<NaceDataEntity> naceDataEntities = fileAttachmentService.readNaceDetails();
        assertNotNull(naceDataEntities);
    }

    @Test
    public void testShouldAbleToReadReadNaceDetailsByOrder() {
    }
}