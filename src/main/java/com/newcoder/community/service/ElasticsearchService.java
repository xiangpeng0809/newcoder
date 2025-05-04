package com.newcoder.community.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.newcoder.community.dao.elasticsearch.DiscussPostRepository;
import com.newcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ElasticsearchService
 * Package: com.newcoder.community.service
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/4 14:44
 */
@Service
public class ElasticsearchService {

    @Autowired
    private DiscussPostRepository postRepository;

    @Autowired
    private ElasticsearchClient client;

    public void saveDiscussPost(DiscussPost post) {
        postRepository.save(post);
    }

    public void deleteDiscussPost(int id) {
        postRepository.deleteById(id);
    }

    public Page<DiscussPost> searchDiscussPost(String keyword, int current, int limit) {
        SearchRequest request = SearchRequest.of(s -> s
                .index("discusspost")
                .query(q->q
                        .multiMatch(m->m
                                .query(keyword)
                                .fields("title", "content")
                        )
                )
                .sort(sorts->sorts
                        .field(f->f.field("type").order(SortOrder.Desc))
                ).sort(sorts -> sorts
                        .field(f -> f.field("score").order(SortOrder.Desc))
                )
                .sort(sorts -> sorts
                        .field(f -> f.field("createTime").order(SortOrder.Desc))
                )
                .highlight(h -> h
                        .fields("title", f -> f
                                .preTags("<em>").postTags("</em>")
                        )
                        .fields("content", f -> f
                                .preTags("<em>").postTags("</em>")
                        )
                )
                .from(current)
                .size(limit)
        );
        // 执行搜索
        SearchResponse<DiscussPost> response = null;
        try {
            response = client.search(request, DiscussPost.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 获取命中结果
        List<DiscussPost> posts = new ArrayList<>();
        for (Hit<DiscussPost> hit : response.hits().hits()) {
            DiscussPost post = hit.source();
            // 处理高亮字段
            Map<String, List<String>> highlight = hit.highlight();
            if (highlight != null) {
                if (highlight.containsKey("title")) {
                    post.setTitle(highlight.get("title").get(0));
                }
                if (highlight.containsKey("content")) {
                    post.setContent(highlight.get("content").get(0));
                }
            }

            posts.add(post);
        }

        long totalHits = response.hits().total() != null ? response.hits().total().value() : 0L;
        return new PageImpl<>(posts, PageRequest.of(current, limit), totalHits);
    }
}
