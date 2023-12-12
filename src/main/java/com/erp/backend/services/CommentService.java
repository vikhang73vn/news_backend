package com.erp.backend.services;

import com.erp.backend.dtos.request.CommentRequest;
import com.erp.backend.entities.Article;
import com.erp.backend.entities.Comment;
import com.erp.backend.entities.User;
import com.erp.backend.repositories.ArticleRepository;
import com.erp.backend.repositories.CommentRepository;
import com.erp.backend.repositories.UserRepository;
import com.erp.backend.socket_io.SocketHandleGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
@Autowired
private ArticleRepository articleRepository;
@Autowired
SocketHandleGlobal socket;
    @Transactional
    public Comment createComment(String email, CommentRequest request){
        User user=userRepository.findByEmail(email).get();
        Article article= articleRepository.findById(request.getIdArticle()).get();
        Comment comment=Comment.builder().content(request.getContent())
                .userCreate(user)
                .build();
        Comment saveComment=commentRepository.save(comment);
        article.getComments().add(comment);
        articleRepository.save(article);
        List<User> listClient=userRepository.findAll();
        for(User client:listClient){
            socket.sendEvent(client.getId(),"comment",saveComment);
        }
        return saveComment;
    }

}
