package ee.demo.sectorSelector.models;

import lombok.Data;

@Data
public class SectorDto {

    private Long id;
    private String code;
    private String name;
    private int numericValue;
    private int subTypeLevel;
    private boolean selected;

}
