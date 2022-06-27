package com.clm.nace.clmnacedataanalytics.service;

import com.clm.nace.clmnacedataanalytics.entity.FileAttachment;
import com.clm.nace.clmnacedataanalytics.entity.NaceDataEntity;
import com.clm.nace.clmnacedataanalytics.model.NaceData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileAttachmentService {
    FileAttachment saveNaceDetails(MultipartFile file) throws Exception;

    FileAttachment getAttachmentFile(String fileId) throws Exception;

    void saveNaceDetailsAsList(List<NaceData> naceDataList);

    List<NaceDataEntity> readNaceDetails();

    Optional<NaceDataEntity> readNaceDetailsByOrder(String order);
}
