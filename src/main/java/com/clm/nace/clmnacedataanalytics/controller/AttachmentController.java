package com.clm.nace.clmnacedataanalytics.controller;

import com.clm.nace.clmnacedataanalytics.entity.FileAttachment;
import com.clm.nace.clmnacedataanalytics.entity.NaceDataEntity;
import com.clm.nace.clmnacedataanalytics.model.NaceData;
import com.clm.nace.clmnacedataanalytics.model.ResponseData;
import com.clm.nace.clmnacedataanalytics.service.FileAttachmentService;
import com.clm.nace.clmnacedataanalytics.utility.FileUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    @ApiOperation(value = "Api call to save the NACE details as csv")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Save the csv file into database"),
            @ApiResponse(code = 404, message = "Not available")
    })
    public ResponseEntity putNaceDetails(@RequestParam("file") MultipartFile file) throws Exception {
        if (Objects.isNull(file)) {
            throw new MultipartException("File not uploaded/File is Invalid");
        }
        List<NaceData> naceDataList = FileUtility.processNaceCSV(file.getInputStream());
        fileAttachmentService.saveNaceDetailsAsList(naceDataList);
        return ResponseEntity.ok().body("Successfully saved the csv File");
    }

    @GetMapping("/readNaceDetails")
    @ApiOperation(value = "Api call to read the NACE details as List")
    public ResponseEntity<List<NaceDataEntity>> readNaceDetails() throws Exception {
        List<NaceDataEntity> naceDataList = fileAttachmentService.readNaceDetails();
        return ResponseEntity.ok().body(naceDataList);
    }

    @GetMapping("/readNaceDetails/{order}")
    @ApiOperation(value = "Api call to read the NACE details by Order")
    public ResponseEntity<NaceDataEntity> readNaceDetailsByOrder(@PathVariable("order") String order) throws Exception {
        Optional<NaceDataEntity> naceData = fileAttachmentService.readNaceDetailsByOrder(order);
        return ResponseEntity.ok().body(naceData.orElse(NaceDataEntity.builder().build()));
    }
}
