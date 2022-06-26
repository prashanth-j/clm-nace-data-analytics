package com.clm.nace.clmnacedataanalytics.controller;

import com.clm.nace.clmnacedataanalytics.entity.FileAttachment;
import com.clm.nace.clmnacedataanalytics.entity.NaceDataEntity;
import com.clm.nace.clmnacedataanalytics.exception.ApplicationException;
import com.clm.nace.clmnacedataanalytics.model.NaceData;
import com.clm.nace.clmnacedataanalytics.model.ResponseData;
import com.clm.nace.clmnacedataanalytics.service.FileAttachmentService;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/clm")
@Api(value = "CLM Service")
public class AttachmentController {

    private FileAttachmentService fileAttachmentService;

    public AttachmentController(FileAttachmentService fileAttachmentService) {
        this.fileAttachmentService = fileAttachmentService;
    }

    @PostMapping("/upload/nacedetails")
    @ApiOperation(value = "save nace api")
    public ResponseData putNaceDetails(@RequestParam("file") MultipartFile file) throws Exception, ApplicationException {
        FileAttachment fileAttachment = null;
        if (Objects.isNull(file)) {
            throw new MultipartException("File not uploaded/File is Invalid");
        }
        InputStream inputStream = file.getInputStream();
        List<NaceData> naceDataList = new ArrayList<>();
        CSVReader reader = null;
        try (Reader inputReader = new InputStreamReader(inputStream, "UTF-8")) {
            reader = new CSVReader(inputReader);
            reader.readNext();
            CsvToBean csvToBean = new CsvToBean();
            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            mappingStrategy.setType(NaceData.class);
            String[] columns = new String[]{"order", "level", "code", "parent", "description", "itemIncludeA", "itemIncludeB", "rulings", "itemExludes", "reference"};
            mappingStrategy.setColumnMapping(columns);
            naceDataList = csvToBean.parse(mappingStrategy, reader);

        } catch (IOException e) {
            // handle exception
        }
        fileAttachmentService.saveNaceDetailsAsList(naceDataList);
        fileAttachment = fileAttachmentService.saveNaceDetails(file);
        String downladURl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileAttachment.getId()).toUriString();
        return new ResponseData(fileAttachment.getFileName(), downladURl, file.getContentType(), file.getSize());

    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        FileAttachment fileAttachment = null;
        fileAttachment = fileAttachmentService.getAttachmentFile(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileAttachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: file name" + fileAttachment.getFileName())
                .body(new ByteArrayResource(fileAttachment.getData()));
    }

    @GetMapping("/readNaceDetails")
    public ResponseEntity<List<NaceDataEntity>> readNaceDetails() throws Exception {
        List<NaceDataEntity> naceDataList = fileAttachmentService.readNaceDetails();
        return ResponseEntity.ok().body(naceDataList);
    }

    @GetMapping("/readNaceDetails/{order}")
    public ResponseEntity<NaceDataEntity> readNaceDetailsByOrder(@PathVariable("order") String order) throws Exception {
        Optional<NaceDataEntity> naceData = fileAttachmentService.readNaceDetailsByOrder(order);
        return ResponseEntity.ok().body(naceData.orElse(NaceDataEntity.builder().build()));
    }
}
