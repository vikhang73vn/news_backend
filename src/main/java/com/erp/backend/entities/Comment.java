package com.erp.backend.entities;

import com.erp.backend.entities.base.AuditableBase;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.util.Date;
import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
//@SQLDelete(sql = "UPDATE comment SET is_deleted = true WHERE id = ?")
//@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = true)
public class Comment extends AuditableBase {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
  //  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User userCreate;

  // @ManyToOne
  //  @JoinColumn(name = "article_id")
//    private Article article;



// getters and setters
}
