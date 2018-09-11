package top.siki.gateway.config;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import reactor.core.publisher.Flux;

import java.net.URI;

/**
 * @author: yangfan
 * @date: 2018/7/18
 * @description: 通过调用 DiscoveryClient 获取注册在注册中心的服务列表，生成对应的 RouteDefinition 数组
 */
public class DiscoveryClientRouteDefinitionLocator implements RouteDefinitionLocator {

    /**注册发现客户端，用于向注册中心发起请求 */
    private final DiscoveryClient discoveryClient;
    /**路由配置编号前缀，以 DiscoveryClient 类名 + _ */
    private final String routeIdPrefix;

    public DiscoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.routeIdPrefix = this.discoveryClient.getClass().getSimpleName() + "_";
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(this.discoveryClient.getServices()).map((serviceId) -> {
            // 设置 ID URI
            RouteDefinition routeDefinition = new RouteDefinition();
            routeDefinition.setId(this.routeIdPrefix + serviceId);
            //LoadBalancerClientFilter 根据 lb:// 前缀过滤处理，使用 serviceId 选择一个服务实例，从而实现负载均衡。
            routeDefinition.setUri(URI.create("lb://" + serviceId));
            // 添加 Path 匹配断言
            PredicateDefinition subPredicate = new PredicateDefinition();
            subPredicate.setName(NameUtils.normalizeRoutePredicateName(PathRoutePredicateFactory.class));
            subPredicate.addArg("pattern", "/" + serviceId + "/**");
            routeDefinition.getPredicates().add(subPredicate);

            // 添加 Path 重写过滤器
            FilterDefinition filter = new FilterDefinition();
            filter.setName(NameUtils.normalizeFilterFactoryName(RewritePathGatewayFilterFactory.class));
            String regex = "/" + serviceId + "/(?<remaining>.*)";
            String replacement = "/${remaining}";
            filter.addArg("regexp", regex);
            filter.addArg("replacement", replacement);
            routeDefinition.getFilters().add(filter);
            //TODO: support for default filters
            return routeDefinition;
        });
    }
}
