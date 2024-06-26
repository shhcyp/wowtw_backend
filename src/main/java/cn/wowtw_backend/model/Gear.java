package cn.wowtw_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "wow_gears")
@NoArgsConstructor
@AllArgsConstructor
public class Gear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String icon;
    private String part;
    private String name;
    private Integer quality;
    private Integer isMark;
    private String drop;
    private Integer isExtra;
    private Integer talentId;
}
