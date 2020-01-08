package io.vertx.maven.gleads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;


import java.util.function.Supplier;

public class PersistenceVerticle extends AbstractVerticle {
  @Override
  public void start(Future<Void> startFuture) {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<JsonObject> consumer = eventBus.consumer("persistence-address");

    consumer.handler(message -> {
      String action = message.body().getString("action");

      switch (action) {
        case "register-user":
          registerUser(message);
          break;
        default:
          message.fail(1,"Unknown action: "+message.body());
      }
    });
    startFuture.complete();
  }
  private void registerUser(Message<JsonObject> message){
    JsonObject retVal = new JsonObject()
      .put("email","jake2@jake.jake")
      .put("username","Jacob");
    message.reply(retVal);
  }
  }
