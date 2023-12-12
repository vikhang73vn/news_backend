package com.erp.backend.entities;

import com.erp.backend.entities.base.AuditableBase;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
//@SQLDelete(sql = "UPDATE authors SET is_deleted = true WHERE id = ?")
//@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = true)
public class Author extends AuditableBase {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 5000)
    private String description;

    @OneToMany(mappedBy = "author")
    private List<Article> articles;

}
