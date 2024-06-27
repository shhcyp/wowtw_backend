package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.*;
import cn.wowtw_backend.repository.GearRepository;
import cn.wowtw_backend.service.GearService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GearServiceImpl implements GearService {

    private GearRepository gearRepository;

    @Override
    public List<GearDTO> getGearsByTalentId(Integer talentId) {

        // 1.初始化gearDTOS
        List<GearDTO> gearDTOS = new ArrayList<>();
        // 2.携带talentId查询所有对应装备gears
        List<Gear> gears = gearRepository.findGearsByTalentId(talentId);

        // 3.封装gears到gearDTO
        for (Gear gear : gears) {
            GearDTO gearDTO = new GearDTO();
            gearDTO.setIcon(gear.getIcon());
            gearDTO.setPart(gear.getPart());
            gearDTO.setName(gear.getName());
            gearDTO.setQuality(gear.getQuality());
            gearDTO.setIsMark(gear.getIsMark());
            if (gear.getIsMark() == 1) {
                List<GearMark> gearMarks = gearRepository.findGearMarksByGearId(gear.getId());
                gearDTO.setMarks(convertGearMarksToDTOs(gearMarks));
            } else {
                gearDTO.setMarks(null);
            }
            gearDTO.setDrop(gear.getDrop());
            gearDTO.setIsExtra(gear.getIsExtra());
            if(gear.getIsExtra() == 1) {
                List<GearExtra> gearExtras = gearRepository.findGearExtrasByGearId(gear.getId());
                gearDTO.setGearExtra(convertGearExtrasToDTOs(gearExtras));
            } else {
                gearDTO.setGearExtra(null);
            }
            gearDTOS.add(gearDTO);
        }

        // 4.分组

        return gearDTOS;
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

    /*
    private <S, T> List<T> convertSetToList(Set<S> set, Class<T> targetClass) {
        List<T> dtos = new ArrayList<>();
        for (S source : set) {
            T dto;
            try {
                dto = targetClass.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source , dto);
                dtos.add(dto);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Failed to convert set to DTOs", e);
            }
        }
        return dtos;
    }
    */
}
