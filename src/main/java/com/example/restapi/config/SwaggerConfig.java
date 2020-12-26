//package com.example.restapi.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.hateoas.client.LinkDiscoverer;
//import org.springframework.hateoas.client.LinkDiscoverers;
//import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
//import org.springframework.plugin.core.SimplePluginRegistry;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.*;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    private static final Contact DEFAULT_CONTACT = new Contact(
//            "Gisulee", "http://www.joneconsultion.co.kr","px2008@naver.com"
//    );
//
//    private static ApiInfo DEFAULT_API_INFO = new ApiInfo("Awesome API Tilte",
//            "My User management REST API service", "1.0", "urn:tos",DEFAULT_CONTACT,
//            "Apache 2.0", "http://www.apache.org/license/LICENSE-2.0", new ArrayList<>());
//
//    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMERS =
//            new HashSet<>(Arrays.asList("application/json"));
//
//
//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(DEFAULT_API_INFO)
//                .produces(DEFAULT_PRODUCES_AND_CONSUMERS)
//                .consumes(DEFAULT_PRODUCES_AND_CONSUMERS);
//    }
//
//
//    // swagger 랑 HATEOAS 가 충돌이 난서 추가..
//    @Bean
//    public LinkDiscoverers discoverers() {
//        List<LinkDiscoverer> plugins = new ArrayList<>();
//        plugins.add(new CollectionJsonLinkDiscoverer());
//        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
//
//    }
//}
