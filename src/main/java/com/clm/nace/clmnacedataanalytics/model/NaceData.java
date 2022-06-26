package com.clm.nace.clmnacedataanalytics.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NaceData {

    private String order;
    private String level;
    private String code;
    private String parent;
    private String description;
    private String itemIncludeA;
    private String itemIncludeB;
    private String rulings;
    private String itemExludes;
    private String reference;
}
