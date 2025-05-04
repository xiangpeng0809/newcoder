package com.newcoder.community;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.elasticsearch.DiscussPostRepository;
import com.newcoder.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * ClassName: ElasticsearchTests
 * Package: com.newcoder.community
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/4 13:56
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticsearchTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchClient client;

    /**
     *  测试插入单条数据
     */
    @Test
    public void insertTest() {
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(243));
    }

    /**
     *  测试插入多条数据
     */
    @Test
    public void insertListTest() {
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(101,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(102,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(103,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(111,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(112,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(131,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(132,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(133,0,100));
    }

    /**
     *  测试更新数据
     */
    @Test
    public void updateTest() {
        DiscussPost post = discussPostMapper.selectDiscussPostById(231);
        post.setContent("我是新人，使劲灌水");
        discussPostRepository.save(post);
    }

    /**
     * 测试删除数据
     */
    @Test
    public void deleteTest() {
        discussPostRepository.deleteById(231);
    }

    /**
     * 删除所有数据
     */
    @Test
    public void deleteAllTest() {
        discussPostRepository.deleteAll();
    }

    /**
     * 搜索
     */
    @Test
    public void searchByRepositoryTest() {
        SearchRequest request = SearchRequest.of(s -> s
                .index("discusspost")
                .query(q->q
                        .multiMatch(m->m
                                .query("互联网寒冬")
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
                .from(0)
                .size(10)
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

                // 处理高亮字段（可选）
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

        // 获取分页元数据
            long totalHits = response.hits().total() != null ? response.hits().total().value() : 0L;
            int page = 0; // 如果你有分页逻辑可以替换
            int size = 10;

        // 封装为 Page 对象（Spring 的 PageImpl）
            Page<DiscussPost> resultPage = new PageImpl<>(posts, PageRequest.of(page, size), totalHits);


            System.out.println(resultPage.getTotalElements());
            System.out.println(resultPage.getTotalPages());
            System.out.println(resultPage.getNumber());
            System.out.println(resultPage.getSize());
            for (DiscussPost post : resultPage) {
                System.out.println(post);
            }
        }
}
