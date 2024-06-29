package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.infoGroup.InfoGroup;
import cn.wowtw_backend.repository.InfoGroupRepository;
import cn.wowtw_backend.service.InfoGroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class InfoGroupServiceImpl implements InfoGroupService {

    private final InfoGroupRepository infoGroupRepository;

    private enum InfoGroupType {
        PLATE, MAIL, LEATHER, CLOTH, UNKNOWN
    }

    private static final Set<Integer> PLATE_TALENT_IDS = Set.of(1, 2, 3, 4, 5, 6, 7, 43, 44, 45, 46, 47, 48, 61, 62, 63, 64, 65, 66, 67);
    private static final Set<Integer> MAIL_TALENT_IDS = Set.of(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 78, 79, 80);

    private InfoGroupType getInfoByTalentId(Integer talentId) {
        if (PLATE_TALENT_IDS.contains(talentId)) {
            return InfoGroupType.PLATE;
        } else if (MAIL_TALENT_IDS.contains(talentId)) {
            return InfoGroupType.MAIL;
        } else {
            return InfoGroupType.UNKNOWN;
        }
    }

    @Override
    public List<InfoGroup> getInfoGroupByTalentId(Integer talentId) {
        List<InfoGroup> infoGroups = infoGroupRepository.findInfoGroupByTalentId(talentId);

        return infoGroups;
    }
}
