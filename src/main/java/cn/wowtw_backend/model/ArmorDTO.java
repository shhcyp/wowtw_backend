package cn.wowtw_backend.model;

import lombok.Data;

import java.util.List;

@Data
public class ArmorDTO {
    private String title;
    private List<GearDTO> armors;
}
