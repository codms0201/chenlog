package com.example.repository;

import com.example.entity.Post;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostRepository {

    private final SqlSession sqlSession;

    public PostRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Post> findAll() {
        return sqlSession.selectList("com.example.repository.PostRepository.findAll");  // full namespace로 수정
    }

    public void add(Post post) {
        sqlSession.insert("com.example.repository.PostRepository.add", post);
    }

    public Post findById(Long id) {
        return sqlSession.selectOne("com.example.repository.PostRepository.findById", id);
    }

    public void update(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        sqlSession.update("com.example.repository.PostRepository.update", post);
    }

    public void delete(Long id) {
        sqlSession.delete("com.example.repository.PostRepository.delete", id);
    }
}