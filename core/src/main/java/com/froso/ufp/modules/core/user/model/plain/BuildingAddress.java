package com.froso.ufp.modules.core.user.model.plain;

import io.swagger.annotations.*;

import javax.validation.*;
import java.io.*;

@ApiModel(description = "detailed location information for buildings")
public class BuildingAddress implements Serializable {
    private static final long serialVersionUID = 1;

    // Having is better than being ;) so model the address as component rather than extention!

    @Valid
    @ApiModelProperty(notes = "the adress this building description belongs to")
    private AdressData address = new AdressData();

    @ApiModelProperty(notes = "the building type")
    private String buildingType;
    @ApiModelProperty(notes = "the building part")
    private String buildingPart;
    @ApiModelProperty(notes = "building part information")
    private String buildingPartNotes;
    @ApiModelProperty(notes = "detailed building part information")
    private String buildingPartDetail;
    @ApiModelProperty(notes = "floor level information")
    private String floor;

    public String getBuildingType() {

        return buildingType;
    }

    public void setBuildingType(String buildingType) {

        this.buildingType = buildingType;
    }

    public String getBuildingPart() {

        return buildingPart;
    }

    public void setBuildingPart(String buildingPart) {

        this.buildingPart = buildingPart;
    }

    public String getBuildingPartNotes() {

        return buildingPartNotes;
    }

    public void setBuildingPartNotes(String buildingPartNotes) {

        this.buildingPartNotes = buildingPartNotes;
    }

    public String getBuildingPartDetail() {

        return buildingPartDetail;
    }

    public void setBuildingPartDetail(String buildingPartDetail) {

        this.buildingPartDetail = buildingPartDetail;
    }

    public String getFloor() {

        return floor;
    }

    public void setFloor(String floor) {

        this.floor = floor;
    }

    public AdressData getAddress() {

        return address;
    }

    public void setAddress(AdressData address) {

        this.address = address;
    }
}
