package com.erp.backend.entities;

import com.erp.backend.entities.base.AuditableBase;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
//@SQLDelete(sql = "UPDATE category SET is_deleted = true WHERE id = ?")
//@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = true)
public class Category extends AuditableBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Article> articles;

    // getters and setters
}
