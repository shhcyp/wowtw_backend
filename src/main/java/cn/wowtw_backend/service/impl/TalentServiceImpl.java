package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.*;
import cn.wowtw_backend.repository.GearExtraRepository;
import cn.wowtw_backend.repository.GearMarkRepository;
import cn.wowtw_backend.repository.GearRepository;
import cn.wowtw_backend.service.TalentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    private final GearRepository gearRepository;
    private final GearMarkRepository gearMarkRepository;
    private final GearExtraRepository gearExtraRepository;
    @Override
    public List<GearDTO> getGearsByTalent(Integer talentId) {
        // 1.携带talentId查询所有对应装备gears
        List<Gear> gears = gearRepository.findByTalentId(talentId);
        // 2.初始化gearDTOS
        List<GearDTO> gearDTOS = new ArrayList<>();

        // 3.封装gears到gearDTO
        for (Gear gear : gears) {
            GearDTO gearDTO = new GearDTO();
            gearDTO.setIcon(gear.getIcon());
            gearDTO.setPart(gear.getPart());
            gearDTO.setName(gear.getName());
            gearDTO.setQuality(gear.getQuality());
            gearDTO.setIsMark(gear.getIsMark());

            if (gear.getIsMark() == 1) {
                // 携带gearId查询gearMarks，并封装gearMarks到gearMarkDTO
                List<GearMark> gearMarks = gearMarkRepository.findByGearId(gear.getId());
                List<GearMarkDTO> gearMarkDTOS = new ArrayList<>();
                for (GearMark gearMark : gearMarks) {
                    GearMarkDTO gearMarkDTO = new GearMarkDTO();
                    gearMarkDTO.setIcon(gearMark.getIcon());

                    gearMarkDTOS.add(gearMarkDTO);
                }
                gearDTO.setMark(gearMarkDTOS);
            } else {
                gearDTO.setMark(null);
            }

            gearDTO.setDrop(gear.getDrop());
            gearDTO.setIsExtra(gear.getIsExtra());

            if (gear.getIsExtra() == 1) {
                // 携带gearId查询gearExtras，并封装gearExtras到gearExtraDTO
                List<GearExtra> gearExtras = gearExtraRepository.findByGearId(gear.getId());
                List<GearExtraDTO> gearExtraDTOS = new ArrayList<>();
                for (GearExtra gearExtra : gearExtras) {
                    GearExtraDTO gearExtraDTO = new GearExtraDTO();
                    gearExtraDTO.setIcon(gearExtra.getIcon());
                    gearExtraDTO.setDescription(gearExtra.getDescription());
                    gearExtraDTO.setQuality(gearExtra.getQuality());

                    gearExtraDTOS.add(gearExtraDTO);
                }
                gearDTO.setGearExtra(gearExtraDTOS);
            } else {
                gearDTO.setIsExtra(null);
            }

            gearDTOS.add(gearDTO);
        }
        return gearDTOS;
    }
}
