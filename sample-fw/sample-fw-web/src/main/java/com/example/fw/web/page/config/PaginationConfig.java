package com.example.fw.web.page.config;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.fw.web.page.PageInfoDialect;

import lombok.RequiredArgsConstructor;

/**
 * ページネーションの設定クラス
 *
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({PaginationConfigurationProperties.class})
public class PaginationConfig implements WebMvcConfigurer {
    private final PaginationConfigurationProperties paginationConfigurationProperties;
    
    /**
     * ページネーションの設定
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 参考
        // https://macchinetta.github.io/server-guideline-thymeleaf/1.8.1.SP1.RELEASE/ja/ArchitectureInDetail/WebApplicationDetail/Pagination.html#pageablehandlermethodargumentresolver
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setMaxPageSize(paginationConfigurationProperties.getMaxPageSize());
        resolver.setFallbackPageable(PageRequest.of(paginationConfigurationProperties.getDefaultPage(), paginationConfigurationProperties.getDefaultPageSize()));
        resolvers.add(resolver);
    }

    /**
     * ページネーションのページリンクで使用するThymeleafのカスタムDialectの設定
     */
    @Bean
    PageInfoDialect pageInfoDialect() {
        return new PageInfoDialect();
    }
}
