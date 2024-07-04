package cn.wowtw_backend.model.infoGroup;

import lombok.Data;

import java.util.List;

@Data
public class InfoGroupsResponseDTO {

    private Integer id;

    private String title;

    private List<InfoGroupsDetails> details;
}