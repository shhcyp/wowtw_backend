package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.infoGroup.InfoGroupsResponseDTO;
import cn.wowtw_backend.model.infoGroup.Talents;
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
@RequestMapping("/api/infoGroup")
@AllArgsConstructor
public class InfoGroupController {

    private final InfoGroupService infoGroupService;

    // 根据天赋id查询信息组
    @GetMapping("/{talentId}")
    public Result getInfoGroup(@PathVariable Integer talentId) {
        log.info("talentId: {}", talentId);
        List<InfoGroupsResponseDTO> infoGroupResponse = infoGroupService.getInfoGroupByTalentId(talentId);
        return Result.success(infoGroupResponse);
    }

    // 获取装备数据版本
    @GetMapping("/latestTalents")
    public Result getLatestTalentsVersion() {
        List<Talents> result = infoGroupService.getLatestTalentsVersion();
        return Result.success(result);
    }
}
