package cn.wowtw_backend.model.infoGroup;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GearExtrasDTO {

    private String icon;

    private String description;

    private Byte quality;
}