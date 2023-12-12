package com.erp.backend.services;

import com.erp.backend.dtos.ArticleDto;
import com.erp.backend.dtos.ArticleSearchDto;
import com.erp.backend.dtos.SearchDto;
import com.erp.backend.dtos.mappers.ArticleDTOMapper;
import com.erp.backend.dtos.mappers.ArticleSearchDTOMapper;
import com.erp.backend.dtos.request.ArticleRequest;
import com.erp.backend.dtos.request.UpdateArticleRequest;
import com.erp.backend.entities.*;
import com.erp.backend.exceptions.ExitException;
import com.erp.backend.exceptions.ResourceNotFoundException;
import com.erp.backend.models.Response;
import com.erp.backend.repositories.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
@Autowired
    ArticleRepository articleRepository;
@Autowired
    ArticleDTOMapper articleDTOMapper;
@Autowired
    CategoryRepository categoryRepository;
@Autowired
    AuthorRepository authorRepository;
@Autowired
    UserRepository userRepository;
@Autowired
    PublicationRepository publicationRepository;
@Autowired
    SearchKeywordRepository searchKeywordRepository;
@Autowired
    FavoriteArticleRepository favoriteArticleRepository;
@Autowired
RelatedArticleRepository relatedArticleRepository;
@Autowired
CommentRepository commentRepository;
@Autowired
    ArticleSearchDTOMapper articleSearchDTOMapper;

    @PersistenceContext
    private EntityManager entityManager;


public List<ArticleDto> getAll(){
List<Article> articles = articleRepository.findAll();
List<ArticleDto> articleDtos =articles.stream().map(articleDTOMapper::apply).collect(Collectors.toList());
return articleDtos;
}
public Page<ArticleDto> getAllArticle(int page, int size){
    Page<Article> articles = articleRepository.findAll(PageRequest.of(page,size));
        Page<ArticleDto> articleDtos = articles.map(articleDTOMapper);
        return  articleDtos;
}

public List<ArticleDto> topArticle(){
    List<Article> articles= articleRepository.findFirst10ByOrderByCreatedAtDesc();
    List<ArticleDto> articleDtos =articles.stream().map(articleDTOMapper::apply).collect(Collectors.toList());
    return articleDtos;
}
public ArticleDto uploadArticle(String email ,ArticleRequest request){
    User user=userRepository.findByEmail(email).get();
    ObjectMapper mapper=new ObjectMapper();

      //  ArticleRequest request= mapper.readValue(jsonObject,ArticleRequest.class);
    Optional<Category> optionalCategory = categoryRepository.findById(request.getIdCategory());
    if (!optionalCategory.isPresent()){ throw new ResourceNotFoundException("directory does not exist");}
        Category category= optionalCategory.get();

        Optional<Author> optionalAuthor=authorRepository.findByNameIgnoreCase(request.getAuthorName());
        Author author;
        if(!optionalAuthor.isPresent()){
            author = Author.builder()
                    .name(request.getAuthorName())
                    .description(request.getDescriptionAuthor())
                    .build();
            author=authorRepository.save(author);
        }else {
            author =optionalAuthor.get();
        }
    //    User user= userRepository.findById(request.getIdUserCreate()).get();
        Optional<Publication> optionalPublication= publicationRepository.findByNameIgnoreCase(request.getPublicationName());
        Publication publication ;
        if(!optionalPublication.isPresent()){
            publication = Publication.builder()
                    .name(request.getPublicationName())
                    .build();
            publication=publicationRepository.save(publication);
        }else
        {
            publication=optionalPublication.get();
        }
        List<SearchKeyword> keywords = new ArrayList<>();

        List<String> searchKeywords = request.getSearchKeywords();
        searchKeywords.stream()
                .filter(keyword ->!keyword.isEmpty())//loai bo tu khoa rong
                .forEach(keyword -> {
                    Optional<SearchKeyword> searchKeyword = searchKeywordRepository.findByKeyword(keyword);
                    SearchKeyword newkey;
                    if (!searchKeyword.isPresent()){
                        newkey = SearchKeyword.builder()
                                .keyword(keyword)
                                .build();
                        newkey=searchKeywordRepository.save(newkey);
                        keywords.add(newkey);
                    }
                    else{
                        keywords.add(searchKeyword.get());
                    }
                });
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .author(author)

                .url(request.getUrl())
                .userCreate(user)
                .publication(publication)
.searchKeywords(keywords)
                .build();
        articleRepository.save(article);

    return articleDTOMapper.apply(article);
}
   @Transactional
public Response deleteArticle(Long idArticle){
Optional<Article> optionalArticle=articleRepository.findById(idArticle);
if(optionalArticle.isPresent()){
    Article article = optionalArticle.get();
    favoriteArticleRepository.deleteByArticle(article);
    relatedArticleRepository.deleteByArticleOrRelatedArticle(article, article);
   // commentRepository.deleteByArticle(article);
    articleRepository.delete(article);
    return new Response(200,null,null);
}else {
    return new Response(404, "Article not found", null);
}



}
   /* @Transactional
    public Response deleteArticle(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.setDeleted(true);
            entityManager.remove(article);
            return new Response(200, null, null);
        } else {
            return new Response(404, "Article not found", null);
        }
    }*/
    @Transactional
public ArticleDto favorite(String email, Long idArticle){
    User user = userRepository.findByEmail(email).get();
    Article article = articleRepository.findById(idArticle).get();
    List<FavoriteArticle> favoriteArticles = user.getFavoriteArticles();
        for (FavoriteArticle favoriteArticle : favoriteArticles) {
            if (favoriteArticle.getArticle().getId().equals(idArticle)) {
                return articleDTOMapper.apply(article);
            }
        }
        FavoriteArticle favoriteArticle = FavoriteArticle.builder()
                .user(user)
                .article(article)
                .build();
        favoriteArticleRepository.save(favoriteArticle);

        favoriteArticles.add(favoriteArticle);
        user.setFavoriteArticles(favoriteArticles);
        userRepository.save(user);

return articleDTOMapper.apply(article);
    }
    @Transactional
    public ArticleDto unfavorite(String email,Long idArticle) {
        User user=userRepository.findByEmail(email).get();
        Article article = articleRepository.findById(idArticle).get();
// Kiểm tra xem bài viết đã được yêu thích hay chưa
        List<FavoriteArticle> favoriteArticles = user.getFavoriteArticles();
        FavoriteArticle favoriteArticleToRemove = null;
        for (FavoriteArticle favoriteArticle : favoriteArticles) {
            if (favoriteArticle.getArticle().getId().equals(idArticle)) {
                // Bài viết đã được yêu thích, xóa khỏi danh sách yêu thích
                favoriteArticleToRemove = favoriteArticle;
                break;
            }
        }
        if (favoriteArticleToRemove != null) {
            favoriteArticles.remove(favoriteArticleToRemove);
            user.setFavoriteArticles(favoriteArticles);
            userRepository.save(user);
            // Xóa đối tượng FavoriteArticle khỏi cơ sở dữ liệu
            favoriteArticleRepository.delete(favoriteArticleToRemove);
        }
        // Trả về bài viếtđã bỏ yêu thích
        return articleDTOMapper.apply(article);
    }
    public ArticleDto getArticle(Long idArticle){
    Article article= articleRepository.findById(idArticle).get();
return articleDTOMapper.apply(article);

    }
    public List<ArticleDto> findArticleByCategory(Long idCategory){
    List<Article> articles = articleRepository.findByCategoryId(idCategory);
    List<ArticleDto> articleDtos = articles.stream().map(articleDTOMapper::apply).collect(Collectors.toList());
    return  articleDtos;
    }
    public List<ArticleDto> findArticleByCategory(String nameCategory){
        List<Article> articles = articleRepository.findByCategoryNameIgnoreCase(nameCategory);
        List<ArticleDto> articleDtos = articles.stream().map(articleDTOMapper::apply).collect(Collectors.toList());
        return  articleDtos;
    }
    public List<ArticleSearchDto> sreachArticle(String keyword){
        List<Article> articles = articleRepository.findByTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrPublicationNameContainingIgnoreCaseOrSearchKeywordsKeywordContainingIgnoreCase(keyword,keyword,keyword,keyword);
        List<ArticleSearchDto> articleDtos = articles.stream().map(articleSearchDTOMapper::apply).collect(Collectors.toList());
        return  articleDtos;
    }
    public ArticleDto updateArticle (UpdateArticleRequest request){
        Article article = articleRepository.findById(request.getId()).get();
        if (article != null) {
            if (request.getTitle() != null) {
                article.setTitle(request.getTitle());
            }
            if (request.getContent() != null) {
                article.setContent(request.getContent());
            }
            if (request.getIdCategory()!=null){
                Optional<Category> optionalCategory = categoryRepository.findById(request.getIdCategory());
                if (!optionalCategory.isPresent()){ throw new ResourceNotFoundException("category does not exist");}
                Category category= optionalCategory.get();
                article.setCategory(category);
            }
            if (request.getAuthorName() != null) {

                Optional<Author> optionalAuthor=authorRepository.findByNameIgnoreCase(request.getAuthorName());
                Author author;
                if(!optionalAuthor.isPresent()){
                    if (request.getDescriptionAuthor()==null){throw new ResourceNotFoundException("Author has no data in the system, a Description Author is required");
                    }
                    author = Author.builder()
                            .name(request.getAuthorName())
                            .description(request.getDescriptionAuthor())
                            .build();
                    author=authorRepository.save(author);
                }else {
                    author =optionalAuthor.get();
                    if (request.getDescriptionAuthor()!=null){
                        author.setDescription(request.getDescriptionAuthor());
                    }
                }
                article.setAuthor(author);
            }
            if (request.getUrl()!=null){
                article.setUrl(request.getUrl());
            }
            if(request.getPublicationName()!=null){
                Optional<Publication> optionalPublication= publicationRepository.findByNameIgnoreCase(request.getPublicationName());
                Publication publication ;
                if(!optionalPublication.isPresent()){
                    publication = Publication.builder()
                            .name(request.getPublicationName())
                            .build();
                    publication=publicationRepository.save(publication);
                }else
                {
                    publication=optionalPublication.get();
                }
                article.setPublication(publication);
            }
            if (!request.getSearchKeywords().isEmpty()){
                List<SearchKeyword> keywords = new ArrayList<>();
                List<String> searchKeywords = request.getSearchKeywords();
                searchKeywords.stream()
                        .filter(keyword ->!keyword.isEmpty())//loai bo tu khoa rong
                        .forEach(keyword -> {
                            Optional<SearchKeyword> searchKeyword = searchKeywordRepository.findByKeyword(keyword);
                            SearchKeyword newkey;
                            if (!searchKeyword.isPresent()){
                                newkey = SearchKeyword.builder()
                                        .keyword(keyword)
                                        .build();
                                newkey=searchKeywordRepository.save(newkey);
                                keywords.add(newkey);
                            }
                            else{
                                keywords.add(searchKeyword.get());
                            }
                        });
                article.setSearchKeywords(keywords);
            }



            articleRepository.save(article);
            return articleDTOMapper.apply(article);
        }
        return null;
    }
    public List<ArticleDto> getArticleByKeywordId(Long id){
        return articleRepository.findBySearchKeywords_Id(id).stream().map(articleDTOMapper::apply).collect(Collectors.toList());
    }
    public List<ArticleDto> getArticleByKeywordName(String keyword){
        return articleRepository.findBySearchKeywords_Keyword(keyword).stream().map(articleDTOMapper::apply).collect(Collectors.toList());
    }

}
