package com.erp.backend.dtos.request;

import com.erp.backend.entities.SearchKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    @NotNull(message = "title is require")
    private String title;
    @NotNull(message = "content is require")
    private String content;
    @NotNull(message = "idCategory is require")
    private Long idCategory;
 //   private Long idAuthor;
    @NotNull(message = "name author is require")
    private String authorName;
    @NotNull(message = "description Author is require")
    private String descriptionAuthor;
    private String url;
  //  @NotNull(message = "idUserCreate is require")
  //  private Long idUserCreate;
    @NotNull(message = "publicationName is require")
    private String publicationName;
    private List<String> SearchKeywords;
  //  private List<Long> idRelatedArticle;
}
