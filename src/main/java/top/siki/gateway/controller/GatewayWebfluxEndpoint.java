package top.siki.gateway.controller;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author: wiki
 * @date: 2018/8/31
 * @description:
 */

@RestController
public class GatewayWebfluxEndpoint implements ApplicationEventPublisherAware {

    /**
     * 应用事件发布器
     */
    private ApplicationEventPublisher publisher;

    @GetMapping("/refresh")
    public Mono<Void> refresh() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return Mono.empty();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }
}