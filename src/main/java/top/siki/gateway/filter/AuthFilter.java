package top.siki.gateway.filter;//package com.anyun.cloud.gateway.server.filter;
//
//import com.anyun.cloud.gateway.server.config.RedisRouteDefinitionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
//import org.springframework.cloud.gateway.filter.FilterDefinition;
//import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.util.UriComponentsBuilder;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author: wiki
// * @date: 2018/8/3
// * @description: 权限
// */
//@Controller
//public class AuthFilter {
//
//    @Autowired
//    private RouteDefinitionWriter routeDefinitionWriter;
//
//    @Autowired
//    private RedisRouteDefinitionRepository redisRouteDefinitionRepository;
//
//    @ResponseBody
//    @RequestMapping(value = "/createRoute")
//    public String createRoute(@RequestParam(name = "id", required = false) String id,
//                              @RequestParam(name = "name") String name,
//                              @RequestParam(name = "pattern") String pattern,
//                              @RequestParam(name = "url") String url) {
//        RouteDefinition definition = new RouteDefinition();
//        PredicateDefinition predicate = new PredicateDefinition();
//        Map<String, String> predicateParams = new HashMap<>(8);
//
//        if (!"".equals(id) && id != null) {
//            definition.setId(id);
//        }
//        //Spring会根据名称去查找对应的FilterFactory，目前支持的名称有：After、Before、Between、Cookie、Header、Host、Method、Path、Query、RemoteAddr。
//        predicate.setName(name);
//        predicateParams.put("pattern", pattern);
//        predicate.setArgs(predicateParams);
//        definition.setPredicates(Arrays.asList(predicate));
//        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
//        definition.setUri(uri);
//        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
//        redisRouteDefinitionRepository.save(Mono.just(definition));
//        return "success";
//    }
//}
