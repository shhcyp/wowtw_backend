package cn.wowtw_backend.model;

import lombok.Data;

import java.util.List;

@Data
public class GearGroupDTO {
    private String title;
    private List<GearDTO> gears;
}
