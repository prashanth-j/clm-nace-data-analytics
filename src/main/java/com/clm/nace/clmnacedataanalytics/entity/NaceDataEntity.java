package com.clm.nace.clmnacedataanalytics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NaceDataEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String orderInfo;
    private String level;
    private String code;
    private String parent;
    @Lob
    @Column(name = "description")
    private String description;
    @Lob
    @Column(name = "itemIncludeA")
    private String itemIncludeA;
    @Lob
    @Column(name = "itemIncludeB")
    private String itemIncludeB;
    @Lob
    @Column(name = "rulings")
    private String rulings;
    @Lob
    @Column(name = "itemExludes")
    private String itemExludes;
    @Lob
    @Column(name = "reference")
    private String reference;
}
