package cn.wowtw_backend.model.infoGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wow_talent_trees_tree_images")
public class TalentTreeTreeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "talent_tree_id")
    @JsonIgnore
    private TalentTree talentTree;

    @ManyToOne
    @JoinColumn(name = "tree_image_id")
    @JsonIgnore
    private TreeImage treeImage;

    private Integer sort;
}
