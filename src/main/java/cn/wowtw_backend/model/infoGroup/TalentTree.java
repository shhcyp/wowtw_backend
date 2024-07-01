package cn.wowtw_backend.model.infoGroup;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_talent_trees")
public class TalentTree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
