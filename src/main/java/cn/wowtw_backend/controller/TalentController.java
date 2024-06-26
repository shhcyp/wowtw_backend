package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.GearDTO;
import cn.wowtw_backend.service.TalentService;
import cn.wowtw_backend.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talents")
@AllArgsConstructor
public class TalentController {
    private final TalentService talentService;

    // 根据talentId查询装备，还需要修改，装备分组、查询天赋树
    @GetMapping("/{talentId}")
    public Result getGearsByTalent(@PathVariable Integer talentId) {
        List<GearDTO> gears = talentService.getGearsByTalent(talentId);
        return Result.success(gears);
    }
}
