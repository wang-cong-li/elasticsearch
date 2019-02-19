package com.demo.search;

import com.demo.entity.WechatUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface WechatUserRepository extends ElasticsearchRepository<WechatUser,String> {

}
