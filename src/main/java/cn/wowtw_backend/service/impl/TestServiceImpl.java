package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.infoGroup.*;
import cn.wowtw_backend.repository.GearPlateRepository;
import cn.wowtw_backend.service.TestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final GearPlateRepository gearPlateRepository;

    @Override
    public List<GearPlateDTO> findThingByTalentId(Integer talentId) {

        List<GearPlateDTO> gearPlateDTOS = new ArrayList<>();

        List<GearPlate> gearPlates = gearPlateRepository.findGearPlateByTalentId(talentId);

        for (GearPlate gearPlate: gearPlates) {
            GearPlateDTO gearPlateDTO = new GearPlateDTO();
            gearPlateDTO.setIcon(gearPlate.getIcon());
            gearPlateDTO.setPart(gearPlate.getPart());
            gearPlateDTO.setName(gearPlate.getName());
            gearPlateDTO.setQuality(gearPlate.getQuality());
            gearPlateDTO.setIsMark(gearPlate.getIsMark());

            if (gearPlate.getIsMark()) {
                List<GearMark> gearMarks = gearPlateRepository.findGearMarksByGearId(gearPlate.getId());
                log.info("getMarksByGearId - Marks size: {} - gear id: {}", gearMarks.size(), gearPlate.getId());
                for (GearMark gearMark : gearMarks) {
                    log.info("Gear plate name: {}", gearMark.getName());
                }
                gearPlateDTO.setGearMarkDTOS(convertGearMarksToDTOs(gearMarks));
            } else {
                gearPlateDTO.setGearMarkDTOS(null);
            }

            gearPlateDTO.setDrop(gearPlate.getDrop());
            gearPlateDTO.setIsExtra(gearPlate.getIsExtra());

            if (gearPlate.getIsExtra()) {
                List<GearExtra> gearExtras = gearPlateRepository.findGearExtrasByTalentId(talentId, gearPlate.getId());
                log.info("getExtrasByTalentId - Extras size: {} - Talent id: {} - plate id: {}", gearExtras.size(), talentId, gearPlate.getId());
                for (GearExtra gearExtra : gearExtras) {
                    log.info("gearExtra desc: {}", gearExtra.getDescription());
                }
                gearPlateDTO.setGearExtraDTOS(convertGearExtrasToDTOs(gearExtras));
            } else {
                gearPlateDTO.setGearExtraDTOS(null);
            }

            gearPlateDTOS.add(gearPlateDTO);
        }
        return gearPlateDTOS;
    }

    private List<GearMarkDTO> convertGearMarksToDTOs(List<GearMark> gearMarks) {
        List<GearMarkDTO> gearMarkDTOS = new ArrayList<>();
        for (GearMark gearMark : gearMarks) {
            GearMarkDTO gearMarkDTO = new GearMarkDTO();
            gearMarkDTO.setIcon(gearMark.getIcon());
            gearMarkDTO.setName(gearMark.getName());

            gearMarkDTOS.add(gearMarkDTO);
        }
        return gearMarkDTOS;
    }

    private List<GearExtraDTO> convertGearExtrasToDTOs(List<GearExtra> gearExtras) {
        List<GearExtraDTO> gearExtraDTOS = new ArrayList<>();
        for (GearExtra gearExtra : gearExtras) {
            GearExtraDTO gearExtraDTO = new GearExtraDTO();
            gearExtraDTO.setIcon(gearExtra.getIcon());
            gearExtraDTO.setDescription(gearExtra.getDescription());
            gearExtraDTO.setQuality(gearExtra.getQuality());

            gearExtraDTOS.add(gearExtraDTO);
        }
        return gearExtraDTOS;
    }
}
