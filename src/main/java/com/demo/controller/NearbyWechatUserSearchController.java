package com.demo.controller;

import com.demo.entity.WechatUser;
import com.demo.search.WechatUserRepository;
import com.demo.util.Constants;
import com.demo.util.DataRandomUtil;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/webchat")
public class NearbyWechatUserSearchController {

    @Autowired
    private WechatUserRepository wechatUserRepository;

    @RequestMapping("/add")
    public Object addWechatUser(){
        System.out.println("先删除所有微信用户！");
        wechatUserRepository.deleteAll();
        WechatUser user = DataRandomUtil.randomWechatUser(Constants.LON,Constants.LAT);
        System.out.println("用户：" + user);
        wechatUserRepository.save(user);
        return "init data success";
    }

    @RequestMapping("/initData")
    public Object initWechatData(){
        System.out.println("先删除所有微信用户！");
        wechatUserRepository.deleteAll();
        List<WechatUser> userList = new ArrayList<WechatUser>();
        for (int i = 0; i < 10000; i++) {
            WechatUser user = DataRandomUtil.randomWechatUser(Constants.LON,Constants.LAT);
            System.out.println("用户：" + user);
            userList.add(user);
        }
        wechatUserRepository.saveAll(userList);
        return "init data success";
    }

    @RequestMapping("/queryNearby")
    public Object queryNearbyUser(@RequestParam(required = false) String sex, @RequestParam String radus){
        System.out.println("开始查询附近的微信用户！");
        String unit = DistanceUnit.METERS.toString();
        GeoPoint point = new GeoPoint(Constants.LAT,Constants.LON);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder geoDistanceRangeQueryBuilder = QueryBuilders
                .geoDistanceQuery("location")
                .point(point)
                .distance(radus + unit)
                .geoDistance(GeoDistance.PLANE);
        nativeSearchQueryBuilder.withQuery(geoDistanceRangeQueryBuilder);
        if (null != sex && sex.length() != 0) {
            QueryBuilder booleanQueryBuilder = QueryBuilders
                    .boolQuery()
                    .queryName("sex")
                    .must(QueryBuilders.matchQuery("sex",sex));
            nativeSearchQueryBuilder.withQuery(booleanQueryBuilder);
        }
        GeoDistanceSortBuilder sortBuilder = SortBuilders
                .geoDistanceSort("location",Constants.LAT,Constants.LON)
                .unit(DistanceUnit.METERS)
                .order(SortOrder.ASC)
                .points(point);
        nativeSearchQueryBuilder.withSort(sortBuilder);
        Iterable<WechatUser> users = wechatUserRepository.search(nativeSearchQueryBuilder.build());
        int i = 1;
        for (WechatUser user : users) {
            if(++i > 5) {
                break;
            }
            System.out.println(user);
        }
        return "init data success";
    }

}
