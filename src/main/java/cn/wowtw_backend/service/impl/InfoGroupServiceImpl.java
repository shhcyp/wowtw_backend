package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.infoGroup.*;
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

            infoGroupResponseDTO.setId(group.getId());
            infoGroupResponseDTO.setTitle(group.getName());

            // 初始化信息组的details属性，分为Gear和TalentTree两个
            // ！！！先做Gear
            List<InfoGroupDetail> infoGroupDetailGearDTOS = new ArrayList<>();

            // 为信息组设置title），查询details
            switch (group.getId()) {
                case 1:

                    // 根据talentID查询护甲组

                    switch (getGearByTalentId(talentId)) {
                        case PLATE:
                            // 查询对应板甲
                            List<GearPlate> gearPlates = gearPlateRepository.findGearPlateByTalentId(talentId);
                            // GearPlate类型转换为GearPlateDTO类型，写入infoGroupDetailGearDTOS
                            for (GearPlate gearPlate : gearPlates) {
                                InfoGroupDetailGearDTO detailGearDTO = new InfoGroupDetailGearDTO();

                                detailGearDTO.setIcon(gearPlate.getIcon());
                                detailGearDTO.setPart(gearPlate.getPart());
                                detailGearDTO.setName(gearPlate.getName());
                                detailGearDTO.setQuality(gearPlate.getQuality());
                                detailGearDTO.setIsMark(gearPlate.getIsMark());

                                if (gearPlate.getIsMark()) {
                                    List<GearMark> gearMarks = gearPlateRepository.findGearMarksByGearId(gearPlate.getId());
                                    List<GearMarkDTO> gearMarkDTOS = new ArrayList<>();
                                    for (GearMark gearMark : gearMarks) {
                                        GearMarkDTO gearMarkDTO = new GearMarkDTO();

                                        gearMarkDTO.setIcon(gearMark.getIcon());
                                        gearMarkDTO.setName(gearMark.getName());

                                        gearMarkDTOS.add(gearMarkDTO);
                                    }
                                    detailGearDTO.setGearMarkDTOS(gearMarkDTOS);
                                } else {
                                    detailGearDTO.setGearMarkDTOS(null);
                                }

                                detailGearDTO.setDrop(gearPlate.getDrop());
                                detailGearDTO.setIsExtra(gearPlate.getIsExtra());

                                if (gearPlate.getIsExtra()) {
                                    List<GearExtra> gearExtras = gearPlateRepository.findGearExtrasByTalentId(talentId, gearPlate.getId());
                                    List<GearExtraDTO> gearExtraDTOS = new ArrayList<>();
                                    for (GearExtra gearExtra : gearExtras) {
                                        GearExtraDTO gearExtraDTO = new GearExtraDTO();
                                        gearExtraDTO.setIcon(gearExtra.getIcon());
                                        gearExtraDTO.setDescription(gearExtra.getDescription());
                                        gearExtraDTO.setQuality(gearExtra.getQuality());

                                        gearExtraDTOS.add(gearExtraDTO);
                                    }
                                    detailGearDTO.setGearExtraDTOS(gearExtraDTOS);
                                } else {
                                    detailGearDTO.setGearExtraDTOS(null);
                                }
                                infoGroupDetailGearDTOS.add(detailGearDTO);
                            }
                            infoGroupResponseDTO.setDetails(infoGroupDetailGearDTOS);
                        case MAIL:
                            // 查询锁甲
                        case LEATHER:
                            // 查询皮甲
                        case CLOTH:
                            // 查询布甲
                    }
                case 2:

                    // 查询杂项组
                case 3:

                    // 查询武器组
                case 4:

                    // 查询主手武器组
                case 5:

                    // 查询副手武器组
                case 6:

                    // 查询爆发天赋树
                case 7:

                    // 查询常规天赋树
            }

            infoGroupResponseDTOS.add(infoGroupResponseDTO);
        }

        return infoGroupResponseDTOS;
    }
}
