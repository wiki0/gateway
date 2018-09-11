package top.siki.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author: wiki
 * @date: 2018/9/6
 * @description:
 */
@Slf4j
@RestController
public class GatewayRouteController {

    /**
     * 存储器 RouteDefinitionLocator 对象
     */
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @PostMapping("/routes/{id}")
    @SuppressWarnings("unchecked")
    public Mono<ResponseEntity<Void>> save(@PathVariable String id, @RequestBody Mono<RouteDefinition> route) {
        System.out.println(id);
        return this.routeDefinitionWriter.save(route.map(r ->  {
            r.setId(id);
            log.debug("Saving route: " + route);
            return r;
        })).then(Mono.defer(() ->
                Mono.just(ResponseEntity.created(URI.create("/routes/"+id)).build())
        ));
    }

    @DeleteMapping("/routes/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id) {
        return this.routeDefinitionWriter.delete(Mono.just(id))
                .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build()));
    }

    /**
     * 回退
     * @return
     */
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
}
