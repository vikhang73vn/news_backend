package com.erp.backend.entities;

import com.erp.backend.entities.base.AuditableBase;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.util.List;
import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publication")
//@SQLDelete(sql = "UPDATE publication SET is_deleted = true WHERE id = ?")
//@Where(clause = "is_deleted= false")
@EqualsAndHashCode(callSuper = true)
public class Publication extends AuditableBase {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "publication")
    private List<Article> articles;

// getters and setters
}
