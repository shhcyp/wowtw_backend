package cn.wowtw_backend.model;

import lombok.Data;

import java.util.List;

@Data
public class WeaponDTO {
    private String title;
    private List<GearDTO> weapons;
}
