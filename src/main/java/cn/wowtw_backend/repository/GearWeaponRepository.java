package cn.wowtw_backend.repository;

import cn.wowtw_backend.model.infoGroup.GearBase;
import cn.wowtw_backend.model.infoGroup.GearWeapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GearWeaponRepository extends JpaRepository<GearWeapon, Integer> {

    @Query("SELECT gw FROM GearWeapon gw JOIN TalentInfoGroupGearWeapon tigw ON gw.id = tigw.gearWeapon.id WHERE tigw.talent.id = :talentId")
    List<GearBase> findGearWeaponByTalentId(Integer talentId);

    @Query("SELECT gw FROM GearWeapon gw JOIN TalentInfoGroupGearWeapon tigw ON gw.id = tigw.gearWeapon.id WHERE tigw.talent.id = :talentId")
    List<GearBase> findWeaponMainHandByTalentId(Integer talentId);

    @Query("SELECT gw FROM GearWeapon gw JOIN TalentInfoGroupGearWeapon tigw ON gw.id = tigw.gearWeapon.id WHERE tigw.talent.id = :talentId")
    List<GearBase> findWeaponOffHandByTalentId(Integer talentId);
}