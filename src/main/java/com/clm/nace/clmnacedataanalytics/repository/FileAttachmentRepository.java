package com.clm.nace.clmnacedataanalytics.repository;

import com.clm.nace.clmnacedataanalytics.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, String> {

}
