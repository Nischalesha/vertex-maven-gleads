package io.vertx.maven.gleads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.mongo.MongoAuth;
import io.vertx.ext.auth.mongo.impl.DefaultHashStrategy;
import io.vertx.ext.auth.mongo.impl.MongoUser;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.maven.gleads.models.User;


import java.util.function.Supplier;

public class PersistenceVerticle extends AbstractVerticle {

  // for DB access
  private MongoClient mongoClient;

  // Authentication provider for logging in
  private MongoAuth loginAuthProvider;
  @Override
  public void start(Future<Void> startFuture) {

    // Configure the MongoClient inline. This should be externalized into a config file
    mongoClient = MongoClient.createShared(vertx, new JsonObject().put("db_name", config().getString("db_name","maven_gleads")).put("connection_string", config().getString("connection_string", "mongodb://localhost:27017")));

    // Configure authentication with MongoDB
    loginAuthProvider= MongoAuth.create(mongoClient, new JsonObject());
    loginAuthProvider.setUsernameField("email");
    loginAuthProvider.setUsernameCredentialField("email");

    JsonObject authProperties = new JsonObject();
    MongoAuth authProvider= MongoAuth.create(mongoClient, authProperties);

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

    final User userToRegister = new User(message.body().getJsonObject("user"));
    //JsonObject jsonString=message.body().getJsonObject("user");
    //final User userToRegister = Json.decodeValue(jsonString.toString(),User.class);
    insertUser(userToRegister).setHandler(ar -> {

      if (ar.succeeded()) {
        message.reply(new JsonObject()
          .put("details", userToRegister.toJson()));
      } else {
        message.fail(1, "\"Insert Failed: \"" + ar.cause().getMessage());
      }
    });

  }

  private Future<Void> insertUser(User user) {
    Future<Void> retVal = Future.future();

    user.setSalt(DefaultHashStrategy.generateSalt());
    String hashedPassword = loginAuthProvider
      .getHashStrategy().computeHash(user.getPassword(),
        new MongoUser(
          new JsonObject()
            .put("email", user.getEmail()),
          loginAuthProvider));
    user.setPassword(hashedPassword);

    mongoClient.save("user", user.toMongoJson(), ar -> {
      if (ar.succeeded()) {
        user.set_id(ar.result());
        retVal.complete();
      } else {
        retVal.fail(ar.cause());
      }
    });

    return retVal;
  }
  }
