package io.vertx.maven.gleads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) {

    vertx.deployVerticle(new HttpVerticle());
    startFuture.complete();
  }

}
