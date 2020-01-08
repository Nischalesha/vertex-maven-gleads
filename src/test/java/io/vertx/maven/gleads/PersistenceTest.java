package io.vertx.maven.gleads;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import io.vertx.maven.gleads.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class PersistenceTest {
  private Vertx vertx;
  @Before
  public void setUp(TestContext testContext) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(new PersistenceVerticle(), testContext.asyncAssertSuccess());
  }
  @After
  public void tearDown(TestContext testContext) {
    vertx.close();
  }

  @Test
  public void testRegisterUser(TestContext testContext) {
    Async async = testContext.async();

    JsonObject userToRegister=new JsonObject()
      .put("email", "jake2@jake.jake")
      .put("username", "Jacob")
      .put("password", "jakejake");

    JsonObject message=new JsonObject()
      .put("action", "register-user")
      .put("user", userToRegister);

    vertx.<JsonObject>eventBus().send("persistence-address",message,ar->{
      if (ar.succeeded()) {
        testContext.assertNotNull(ar.result().body());
        User returnedUser= Json.decodeValue(new JsonObject(ar.result().body().toString()).getJsonObject("details").toString(),User.class);
        testContext.assertEquals("jake2@jake.jake", returnedUser.getEmail());
        testContext.assertEquals("Jacob", returnedUser.getUsername());
        async.complete();

      }else{
        testContext.assertTrue(ar.succeeded());
        async.complete();
    }
    });

  }


}
