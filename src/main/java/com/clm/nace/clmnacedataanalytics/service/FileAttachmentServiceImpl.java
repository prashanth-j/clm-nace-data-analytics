package com.clm.nace.clmnacedataanalytics.service;

import com.clm.nace.clmnacedataanalytics.entity.FileAttachment;
import com.clm.nace.clmnacedataanalytics.entity.NaceDataEntity;
import com.clm.nace.clmnacedataanalytics.model.NaceData;
import com.clm.nace.clmnacedataanalytics.repository.ClmNaceRepository;
import com.clm.nace.clmnacedataanalytics.repository.FileAttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileAttachmentServiceImpl implements FileAttachmentService {

    private FileAttachmentRepository fileAttachmentRepository;

    private ClmNaceRepository clmNaceRepository;

    public FileAttachmentServiceImpl(FileAttachmentRepository fileAttachmentRepository, ClmNaceRepository clmNaceRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.clmNaceRepository = clmNaceRepository;
    }

    @Override
    public FileAttachment saveNaceDetails(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("File name contains invalide sequence" + fileName);
            }
            FileAttachment fileAttachment = new FileAttachment(fileName, file.getContentType(), file.getBytes());
            return fileAttachmentRepository.save(fileAttachment);
        } catch (Exception e) {
            throw new Exception("Could not save file" + fileName);
        }

    }

    @Override
    public FileAttachment getAttachmentFile(String fileId) throws Exception {
        return fileAttachmentRepository.findById(fileId).orElseThrow(() -> new Exception("File Not Found Excpetion"));
    }

    @Override
    public List<NaceDataEntity> saveNaceDetailsAsList(List<NaceData> naceDataList) {
        List<NaceDataEntity> naceDataEntities = new ArrayList<>();
        naceDataList.parallelStream().forEach(naceData -> {
            NaceDataEntity naceDataEntity = NaceDataEntity.builder()
                    .orderInfo(naceData.getOrder())
                    .level(naceData.getLevel())
                    .code(naceData.getCode())
                    .parent(naceData.getParent())
                    .description(naceData.getDescription())
                    .itemIncludeA(naceData.getItemIncludeA())
                    .itemIncludeB(naceData.getItemIncludeB())
                    .rulings(naceData.getRulings())
                    .itemExludes(naceData.getItemExludes())
                    .reference(naceData.getReference())
                    .build();
            naceDataEntities.add(naceDataEntity);
        });
        List<NaceDataEntity> naceDataEntityList = (List<NaceDataEntity>) clmNaceRepository.saveAll(naceDataEntities);
        return naceDataEntityList;
    }

    @Override
    public List<NaceDataEntity> readNaceDetails() {
        return clmNaceRepository.findAll();
    }

    @Override
    public Optional<NaceDataEntity> readNaceDetailsByOrder(String order) {
        return clmNaceRepository.findById(order);
    }
}
