package cn.wowtw_backend.model.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wowtw_visit_count")
public class VisitCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer count;
}
