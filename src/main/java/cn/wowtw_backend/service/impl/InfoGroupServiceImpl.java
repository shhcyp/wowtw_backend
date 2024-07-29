package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.infoGroup.*;
import cn.wowtw_backend.repository.*;
import cn.wowtw_backend.service.InfoGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Slf4j
@Service
@AllArgsConstructor
public class InfoGroupServiceImpl implements InfoGroupService {

    private final TalentsRepository talentsRepository;
    private final InfoGroupsRepository infoGroupsRepository;
    private final PlatesRepository platesRepository;
    private final MailsRepository mailsRepository;
    private final LeathersRepository leathersRepository;
    private final ClothesRepository clothesRepository;
    private final MiscellaneaRepository miscellaneaRepository;
    private final WeaponsRepository weaponsRepository;
    private final GearMarksRepository gearMarksRepository;
    private final GearExtrasRepository gearExtrasRepository;
    private final SpecializationsRepository specializationsRepository;

    // 根据talentId将护甲分类
    private enum GearType {
        PLATE, MAIL, LEATHER, CLOTH, UNKNOWN
    }

    private static final Set<Integer> PLATE_TALENT_IDS = Set.of(1, 2, 3, 4, 5, 6, 7, 43, 44, 45, 46, 47, 48, 64, 65, 66, 67, 68, 69, 70);
    private static final Set<Integer> MAIL_TALENT_IDS = Set.of(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 82, 83, 84);
    private static final Set<Integer> LEATHER_TALENT_IDS = Set.of(21, 22, 23, 24, 25, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81);
    private static final Set<Integer> CLOTH_TALENT_IDS = Set.of(28, 29, 30, 31, 32, 33, 34, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63);

    private GearType getGearByTalentId(Integer talentId) {
        if (PLATE_TALENT_IDS.contains(talentId)) {
            return GearType.PLATE;
        } else if (MAIL_TALENT_IDS.contains(talentId)) {
            return GearType.MAIL;
        } else if (LEATHER_TALENT_IDS.contains(talentId)) {
            return GearType.LEATHER;
        } else if (CLOTH_TALENT_IDS.contains(talentId)) {
            return GearType.CLOTH;
        } else {
            return GearType.UNKNOWN;
        }
    }

    @Override
    public List<InfoGroupsResponseDTO> getInfoGroupByTalentId(Integer talentId) {

        // 初始化InfoGroupResponseDTO数组
        List<InfoGroupsResponseDTO> infoGroupsResponseDTOS = new ArrayList<>();

        // 根据talentId查询对应天赋的信息组
        List<InfoGroupsDTO> infoGroups = infoGroupsRepository.findInfoGroupsByTalentId(talentId);

        // 遍历信息组设置不同的details
        for (InfoGroupsDTO infoGroup : infoGroups) {

            // 初始化InfoGroupResponseDTO元素
            InfoGroupsResponseDTO infoGroupsResponseDTO = new InfoGroupsResponseDTO();

            // 设置id和title
            infoGroupsResponseDTO.setId(infoGroup.getId());
            infoGroupsResponseDTO.setTitle(infoGroup.getTitle());

            // 分条件设置details，分为Gear和TalentTree两类，Gear又分为护甲、杂项、武器，护甲又分板、锁、皮、布，武器分武器、武器-主手、武器-副手
            switch (infoGroup.getId()) {
                case 1:
                    // 匹配到护甲组,根据装备类型走不同的通道查询
                    switch (getGearByTalentId(talentId)) {
                        case PLATE:
                            // 查询对应板甲
                            List<GearBaseDTO> gearPlates = platesRepository.findPlatesByTalentId(talentId);
                            log.info("{}", gearPlates.size());
                            // GearBaseDTO类型转换为InfoGroupsDetailsGearDTO类型，写入InfoGroupsResponseDTO的List<InfoGroupsDetails> details
                            infoGroupsResponseDTO.setDetails(convertGearsToGearDTOs(
                                    gearPlates,
                                    gearMarksRepository::findPlatesMarksByGearId,
                                    gearExtrasRepository::findPlatesExtrasByGearId
                            ));
                            break;
                        case MAIL:
                            // 查询锁甲
                            List<GearBaseDTO> gearMails = mailsRepository.findMailsByTalentId(talentId);
                            infoGroupsResponseDTO.setDetails(convertGearsToGearDTOs(
                                    gearMails,
                                    gearMarksRepository::findMailsMarksByGearId,
                                    gearExtrasRepository::findMailsExtrasByGearId
                            ));
                            break;
                        case LEATHER:
                            // 查询皮甲
                            List<GearBaseDTO> gearLeather = leathersRepository.findLeathersByTalentId(talentId);
                            infoGroupsResponseDTO.setDetails(convertGearsToGearDTOs(
                                    gearLeather,
                                    gearMarksRepository::findLeathersMarksByGearId,
                                    gearExtrasRepository::findLeathersExtrasByGearId
                            ));
                            break;
                        case CLOTH:
                            // 查询布甲
                            List<GearBaseDTO> gearCloth = clothesRepository.findClothesByTalentId(talentId);
                            infoGroupsResponseDTO.setDetails(convertGearsToGearDTOs(
                                    gearCloth,
                                    gearMarksRepository::findClothesMarksByGearId,
                                    gearExtrasRepository::findClothesExtrasByGearId
                            ));
                            break;
                    }
                    break;
                case 2:
                    // 匹配到杂项组
                    List<GearBaseDTO> gearMiscellaneous = miscellaneaRepository.findMiscellaneaByTalentId(talentId);
                    infoGroupsResponseDTO.setDetails(convertGearsToGearDTOs(
                            gearMiscellaneous,
                            gearMarksRepository::findMiscellaneaMarksByGearId,
                            gearExtrasRepository::findMiscellaneaExtrasByGearId
                    ));
                    break;
                case 3:
                    // 匹配到武器组
                    List<GearBaseDTO> twoHandWeapons = weaponsRepository.findTwoHandWeaponsByTalentId(talentId);
                    infoGroupsResponseDTO.setDetails(convertGearsToGearDTOs(
                            twoHandWeapons,
                            gearMarksRepository::findTwoHandWeaponsMarksByGearId,
                            gearExtrasRepository::findTwoHandWeaponsExtrasByGearId
                    ));
                    break;
                case 4:
                    // 匹配到主手武器组
                    List<GearBaseDTO> gearMainHandWeapons = weaponsRepository.findMainHandWeaponsByTalentId(talentId);
                    infoGroupsResponseDTO.setDetails(convertGearsToGearDTOs(
                            gearMainHandWeapons,
                            gearMarksRepository::findMainHandWeaponsMarksByGearId,
                            gearExtrasRepository::findMainHandWeaponsExtrasByGearId
                    ));
                    break;
                case 5:
                    // 匹配到副手武器组
                    List<GearBaseDTO> gearOffHandWeapons = weaponsRepository.findOffHandWeaponsByTalentId(talentId);
                    infoGroupsResponseDTO.setDetails(convertGearsToGearDTOs(
                            gearOffHandWeapons,
                            gearMarksRepository::findOffHandWeaponsMarksByGearId,
                            gearExtrasRepository::findOffHandWeaponsExtrasByGearId
                    ));
                    break;
                case 6:
                    // 匹配到天赋树
                    List<SpecializationsDTO> specializations = specializationsRepository.findSpecializationsByTalentId(talentId);
                    infoGroupsResponseDTO.setDetails(convertSpecializationsDTOs(specializations));
                    break;
            }

            // 写入DTO数组
            infoGroupsResponseDTOS.add(infoGroupsResponseDTO);
        }

        return infoGroupsResponseDTOS;
    }

    @Override
    public List<Talents> getLatestTalentsVersion() {
        return talentsRepository.findAll();
    }

    private List<InfoGroupsDetails> convertGearsToGearDTOs(
            List<GearBaseDTO> gears,
            Function<Integer, List<GearMarksDTO>> gearMarkFetcher,
            Function<Integer, List<GearExtrasDTO>> gearExtraFetcher
    ) {
        // List<GearBase> gears = gearPlateRepository.findGearPlateByTalentId(talentId);
        // 装备类的DTO,由于有多个种类使用多态
        List<InfoGroupsDetails> detailGearDTOS = new ArrayList<>();
        for (GearBaseDTO gear : gears) {
            InfoGroupsDetailsGearDTO detailGearDTO = new InfoGroupsDetailsGearDTO();
            detailGearDTO.setIcon(gear.getIcon());
            detailGearDTO.setPart(gear.getPart());
            detailGearDTO.setName(gear.getName());
            detailGearDTO.setQuality(gear.getQuality());
            detailGearDTO.setIsMark(gear.getIsMark());
            if (gear.getIsMark()) {
                List<GearMarksDTO> gearMarksDTOS = gearMarkFetcher.apply(gear.getId());
                detailGearDTO.setMarks(gearMarksDTOS);
            } else {
                detailGearDTO.setMarks(null);
            }
            detailGearDTO.setDrop(gear.getDrop());
            detailGearDTO.setIsExtra(gear.getIsExtra());
            if (gear.getIsExtra()) {
                List<GearExtrasDTO> gearExtrasDTOS = gearExtraFetcher.apply(gear.getId());
                detailGearDTO.setExtras(gearExtrasDTOS);
            } else {
                detailGearDTO.setExtras(null);
            }
            detailGearDTOS.add(detailGearDTO);
        }
        return detailGearDTOS;
    }

    private List<InfoGroupsDetails> convertSpecializationsDTOs(List<SpecializationsDTO> specializations) {
        List<InfoGroupsDetails> detailsSpecializationsDTOs = new ArrayList<>();
        for (SpecializationsDTO specialization : specializations) {
            InfoGroupsDetailsSpecializationsDTO detailsSpecializationsDTO = new InfoGroupsDetailsSpecializationsDTO();
            detailsSpecializationsDTO.setTalentName(specialization.getName());
            detailsSpecializationsDTO.setString(specialization.getString());
            detailsSpecializationsDTOs.add(detailsSpecializationsDTO);
        }
        return detailsSpecializationsDTOs;
    }
}