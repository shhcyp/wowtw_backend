package cn.wowtw_backend.model.infoGroup;

import lombok.Data;

import java.util.List;

@Data
public class InfoGroupResponseDTO {

    private Integer id;

    private String title;

    private List<InfoGroupDetail> details;
}