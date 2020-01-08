package io.vertx.maven.gleads;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.maven.gleads.models.User;


public class HttpVerticle extends AbstractVerticle {


  @Override
  public void start(Future<Void> startFuture) {

    // create a apiRouter to handle the API
    Router baseRouter = Router.router(vertx);
    Router apiRouter = Router.router(vertx);

    baseRouter.route("/").handler(routingContext -> {
      HttpServerResponse response = routingContext.response();
      response.putHeader("content-type", "text/plain").end("Hello gleads!");
    });

    apiRouter.route("/user*").handler(BodyHandler.create());
    apiRouter.post("/users").handler(this::registerUser);
    baseRouter.mountSubRouter("/api", apiRouter);

    vertx.createHttpServer()
      .requestHandler(baseRouter::accept)
      .listen(8080, result -> {
        if (result.succeeded()) {
          startFuture.complete();
        } else {
          startFuture.fail(result.cause());
        }
      });


  }

  private void registerUser(RoutingContext routingContext) {

    User retVal = new User("Jacob", "jake@jake.jake", "jakejake", null, null,null);

    routingContext.response().setStatusCode(201)
      .putHeader("Content-Type", "application/json")
      .end(Json.encodePrettily(retVal.toGleadsJson()));
  }
}
