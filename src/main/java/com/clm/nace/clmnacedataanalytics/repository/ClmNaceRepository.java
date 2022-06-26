package com.clm.nace.clmnacedataanalytics.repository;

import com.clm.nace.clmnacedataanalytics.entity.NaceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClmNaceRepository extends JpaRepository<NaceDataEntity, String> {
}
