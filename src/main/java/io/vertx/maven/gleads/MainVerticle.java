package io.vertx.maven.gleads;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    vertx.createHttpServer()
        .requestHandler(req -> req.response().end("Hello gleads!"))
        .listen(8080);
  }

}
