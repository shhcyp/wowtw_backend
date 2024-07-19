package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.infoGroup.InfoGroupsResponseDTO;
import cn.wowtw_backend.service.InfoGroupService;
import cn.wowtw_backend.utils.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class InfoGroupController {

    private final InfoGroupService infoGroupService;

    @GetMapping("/infoGroup/{talentId}")
    public Result getInfoGroup(@PathVariable Integer talentId) {
        log.info("talentId: {}", talentId);
        List<InfoGroupsResponseDTO> infoGroupResponse = infoGroupService.getInfoGroupByTalentId(talentId);
        return Result.success(infoGroupResponse);
    }
}
