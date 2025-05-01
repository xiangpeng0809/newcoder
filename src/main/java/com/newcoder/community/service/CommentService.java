package com.newcoder.community.service;

import com.newcoder.community.dao.CommentMapper;
import com.newcoder.community.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: CommentService
 * Package: com.newcoder.community.service
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/1 21:12
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentsByEntity(entityType, entityId, offset, limit);
    }

    public int findCommentCount (int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

}
