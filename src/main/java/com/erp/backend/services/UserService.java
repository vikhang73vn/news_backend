package com.erp.backend.services;

import com.erp.backend.dtos.ArticleDto;
import com.erp.backend.dtos.UserDto;
import com.erp.backend.dtos.mappers.ArticleDTOMapper;
import com.erp.backend.dtos.mappers.UserDTOMapper;
import com.erp.backend.entities.Article;
import com.erp.backend.entities.FavoriteArticle;
import com.erp.backend.entities.User;
import com.erp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDTOMapper userDTOMapper;
    @Autowired
    ArticleDTOMapper articleDTOMapper;


    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(userDTOMapper::apply).collect(Collectors.toList());
        return userDtos;
    }

    public User getProfile(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(userDTOMapper::apply).orElse(null);
    }
    public List<ArticleDto> getArticleFollows(String email){
        User user = userRepository.findByEmail(email).get();
             //   .orElseThrow(() -> new RuntimeException("User not found"));
            // Trả về danh sách bài viết mà người dùng đã follow
            return user.getFavoriteArticles().stream()
                    .map(FavoriteArticle::getArticle)
                    .collect(Collectors.toList()).stream().map(articleDTOMapper::apply).collect(Collectors.toList());

        }



}
