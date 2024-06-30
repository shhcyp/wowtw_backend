package cn.wowtw_backend.controller;

import cn.wowtw_backend.model.infoGroup.GearPlateDTO;
import cn.wowtw_backend.service.TestService;
import cn.wowtw_backend.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gear")
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/{talentId}")
    public Result response(@PathVariable Integer talentId) {
        List<GearPlateDTO> result = testService.findThingByTalentId(talentId);
        return Result.success(result);
    }
}
