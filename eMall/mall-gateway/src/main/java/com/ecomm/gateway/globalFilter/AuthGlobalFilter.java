package com.ecomm.gateway.globalFilter;

import cn.hutool.core.text.AntPathMatcher;
import com.ecomm.common.exception.UnauthorizedException;
import com.ecomm.gateway.configuration.AuthProperties;
import com.ecomm.gateway.util.JwtTool;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchange.Builder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {
  private static final Logger log = LoggerFactory.getLogger(AuthGlobalFilter.class);

  private final AntPathMatcher antPathMatcher = new AntPathMatcher();
  private final AuthProperties authProperties;
  private final JwtTool jwtTool;


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//    log.info("AuthGlobalFilter is being executed for path: {}", exchange.getRequest().getPath());
    //1. Get the request
    ServerHttpRequest request = exchange.getRequest();
//    log.info("AuthGlobalFilter executing for path: {}", request.getPath());
//    log.info("The auth order is : {}", getOrder());
    // check if we need to intercept
    if (isPathExclude(request.getPath().toString())){
      return chain.filter(exchange);
    }
    // if not, we need to  get token
    List<String> authorization = request.getHeaders().get("authorization");
    String token = null;
    if (authorization == null || authorization.isEmpty()){
      return chain.filter(exchange);
    }else{
      token = authorization.get(0);
    }
    //check and parse token
    Long userId = null;
    try{
      userId = jwtTool.parseToken(token);
    }catch (UnauthorizedException e){
      //401 -> not authorized
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    // pass the user information to the next layer (interceptor) stateful exchange
    String userIdStr = userId.toString();

    ServerWebExchange exchange1 = exchange.mutate()
        .request(builder -> builder.header("user-header", userIdStr)).build();

    //6. pass to the netty router
    return chain.filter(exchange1);
  }

  /**
   * Path filter check
   * @param string
   * @return
   */
  private boolean isPathExclude(String string) {
    for (String path : authProperties.getExcludePaths()) {
      if (antPathMatcher.match(path, string)) {
        return true;
      }

    }
    return false;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
