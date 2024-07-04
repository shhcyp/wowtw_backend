package cn.wowtw_backend.model.infoGroup;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gear_marks")
public class GearMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String icon;
}