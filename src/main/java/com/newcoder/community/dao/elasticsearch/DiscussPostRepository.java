package com.newcoder.community.dao.elasticsearch;

import com.newcoder.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * ClassName: DiscussPostRepository
 * Package: com.newcoder.community.dao.elasticsearch
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/4 13:55
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {

}
