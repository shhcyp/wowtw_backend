package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.GearDTO;
import cn.wowtw_backend.service.GearService;
import cn.wowtw_backend.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gears")
@AllArgsConstructor
public class GearController {

    private final GearService gearService;

    // 根据talentId查询装备，还需要修改，装备分组、查询天赋树
    @GetMapping("/{talentId}")
    public Result getGearsByTalentId(@PathVariable Integer talentId) {
        List<GearDTO> gears = gearService.getGearsByTalentId(talentId);
        return Result.success(gears);
    }
}
