package io.vertx.maven.gleads;

import io.vertx.core.*;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    CompositeFuture.all(
      deployVerticle(HttpVerticle.class.getName()),
      deployVerticle(PersistenceVerticle.class.getName())
    ).setHandler(f->{
      if(f.succeeded()){
        startFuture.complete();
      }else{
        startFuture.fail(f.cause());
      }
    });
  }

  private Future<Void> deployVerticle(String verticleName) {
    Future<Void> retVal = Future.future();
    vertx.deployVerticle(verticleName, event -> {
      if(event.succeeded()) {
        retVal.complete();
      }
      else{
        retVal.fail(event.cause());
      }
    });
    return retVal;
  }
}
