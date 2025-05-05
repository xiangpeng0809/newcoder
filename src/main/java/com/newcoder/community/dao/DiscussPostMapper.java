package com.newcoder.community.dao;

import com.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: DiscussPostMapper
 * Package: com.newcoder.community.dao
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/4/4 16:57
 */
@Mapper
public interface DiscussPostMapper {

    /**
     *
     * @param userId 用户id
     * @param offset 起始行行号
     * @param limit 每页最多多少个数据
     * @return 已发布的所有数据
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit, int orderMode);

    /**
     *
     * @param userId 用户id
     * @return 总数
     */
    //@Param注解用于给参数取别名
    //如果只有一个参数，并且在<if>里使用,则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);

    int updateType(int id, int type);

    int updateStatus(int id, int status);

    int updateScore(int id, double score);
}
