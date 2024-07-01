package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.infoGroup.*;
import cn.wowtw_backend.repository.GearExtraRepository;
import cn.wowtw_backend.repository.GearMarkRepository;
import cn.wowtw_backend.repository.GearPlateRepository;
import cn.wowtw_backend.repository.InfoGroupRepository;
import cn.wowtw_backend.service.InfoGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class InfoGroupServiceImpl implements InfoGroupService {

    private final InfoGroupRepository infoGroupRepository;
    private final GearPlateRepository gearPlateRepository;
    private final GearMarkRepository gearMarkRepository;
    private final GearExtraRepository gearExtraRepository;

    // 根据talentId将护甲分类
    private enum InfoGroupType {
        PLATE, MAIL, LEATHER, CLOTH, UNKNOWN
    }

    private static final Set<Integer> PLATE_TALENT_IDS = Set.of(1, 2, 3, 4, 5, 6, 7, 43, 44, 45, 46, 47, 48, 61, 62, 63, 64, 65, 66, 67);
    private static final Set<Integer> MAIL_TALENT_IDS = Set.of(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 35, 36, 37, 38, 39, 40, 41, 42, 78, 79, 80);
    private static final Set<Integer> LEATHER_TALENT_IDS = Set.of(21, 22, 23, 24, 25, 26, 27, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77);
    private static final Set<Integer> CLOTH_TALENT_IDS = Set.of(28, 29, 30, 31, 32, 33, 34, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60);

    private InfoGroupType getGearByTalentId(Integer talentId) {
        if (PLATE_TALENT_IDS.contains(talentId)) {
            return InfoGroupType.PLATE;
        } else if (MAIL_TALENT_IDS.contains(talentId)) {
            return InfoGroupType.MAIL;
        } else if (LEATHER_TALENT_IDS.contains(talentId)) {
            return InfoGroupType.LEATHER;
        } else if (CLOTH_TALENT_IDS.contains(talentId)) {
            return InfoGroupType.CLOTH;
        } else {
            return InfoGroupType.UNKNOWN;
        }
    }

    @Override
    public List<InfoGroupResponseDTO> getInfoGroupByTalentId(Integer talentId) {

        // 初始化InfoGroupResponseDTO数组
        List<InfoGroupResponseDTO> infoGroupResponseDTOS = new ArrayList<>();

        // 根据talentId查询对应的信息组
        List<InfoGroup> infoGroups = infoGroupRepository.findInfoGroupByTalentId(talentId);

        // 遍历信息组得到分别有哪些组
        for (InfoGroup group : infoGroups) {

            // 初始化InfoGroupResponseDTO元素
            InfoGroupResponseDTO infoGroupResponseDTO = new InfoGroupResponseDTO();

            // 设置id和title
            infoGroupResponseDTO.setId(group.getId());
            infoGroupResponseDTO.setTitle(group.getName());

            // 分条件进行设置details，分为Gear和TalentTree两种，通过查询数据库实现
            List<InfoGroupDetail> detailGearDTOS = new ArrayList<>(); // 装备类的DTO

            switch (group.getId()) {
                case 1:
                    // 匹配到护甲组,根据装备类型走不同的通道查询
                    switch (getGearByTalentId(talentId)) {
                        case PLATE:
                            // 查询对应板甲
                            List<GearBase> gearPlates = gearPlateRepository.findGearPlateByTalentId(talentId);
                            // GearPlate类型转换为GearPlateDTO类型，写入infoGroupDetailGearDTOS
                            infoGroupResponseDTO.setDetails(convertGearsToGearDTOs(gearPlates, talentId));
                            break;
                        case MAIL:
                            // 查询锁甲
                            break;
                        case LEATHER:
                            // 查询皮甲
                            break;
                        case CLOTH:
                            // 查询布甲
                            break;
                    }
                    break;
                case 2:
                    // 匹配到杂项组
                    break;
                case 3:
                    // 匹配到武器组
                    break;
                case 4:
                    // 匹配到主手武器组
                    break;
                case 5:
                    // 匹配到副手武器组
                    break;
                case 6:
                    // 匹配到爆发天赋树
                    break;
                case 7:
                    // 匹配到常规天赋树
                    break;
            }

            // 写入DTO数组
            infoGroupResponseDTOS.add(infoGroupResponseDTO);
        }

        return infoGroupResponseDTOS;
    }

    private List<InfoGroupDetail> convertGearsToGearDTOs(List<GearBase> gears, Integer talentId) {
        List<InfoGroupDetail> detailGearDTOS = new ArrayList<>(); // 装备类的DTO
        // List<GearBase> gears = gearPlateRepository.findGearPlateByTalentId(talentId);
        for (GearBase gear : gears) {
            InfoGroupDetailGearDTO detailGearDTO = new InfoGroupDetailGearDTO();
            detailGearDTO.setIcon(gear.getIcon());
            detailGearDTO.setPart(gear.getPart());
            detailGearDTO.setName(gear.getName());
            detailGearDTO.setQuality(gear.getQuality());
            detailGearDTO.setIsMark(gear.getIsMark());
            if (gear.getIsMark()) {
                List<GearMark> gearMarks = gearMarkRepository.findGearMarksByGearId(gear.getId());
                detailGearDTO.setMarks(convertGearMarksToGearMarkDTOs(gearMarks));
            } else {
                detailGearDTO.setMarks(null);
            }
            detailGearDTO.setDrop(gear.getDrop());
            detailGearDTO.setIsExtra(gear.getIsExtra());
            if (gear.getIsExtra()) {
                List<GearExtra> gearExtras = gearExtraRepository.findGearExtrasByTalentId(talentId, gear.getId());
                detailGearDTO.setExtras(convertGearExtrasToDTOs(gearExtras));
            } else {
                detailGearDTO.setExtras(null);
            }
            detailGearDTOS.add(detailGearDTO);
        }
        return detailGearDTOS;
    }

    private List<GearMarkDTO> convertGearMarksToGearMarkDTOs(List<GearMark> gearMarks) {
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
