package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.infoGroup.InfoGroupResponseDTO;
import cn.wowtw_backend.service.InfoGroupService;
import cn.wowtw_backend.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/info")
@AllArgsConstructor
public class InfoGroupController {

    private final InfoGroupService infoGroupService;

    @GetMapping("/{talentId}")
    public Result getInfoGroup(@PathVariable Integer talentId) {
        List<InfoGroupResponseDTO> infoGroupResponse = infoGroupService.getInfoGroupByTalentId(talentId);
        return Result.success(infoGroupResponse);
    }
}
