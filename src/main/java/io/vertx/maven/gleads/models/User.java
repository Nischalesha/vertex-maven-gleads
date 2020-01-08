package io.vertx.maven.gleads.models;
import io.vertx.core.json.JsonObject;

public class User {

  String username;

  String email;

  String password;

  String token;

  String bio;

  String image;

  String salt;

  String _id;

  public JsonObject toGleadsJson(){

    JsonObject retVal = new JsonObject();
    JsonObject user = new JsonObject()
      .put("username", this.username)
      .put("email", this.email)
      .put("token", this.token)
      .put("bio", this.bio)
      .put("image", this.image);
    retVal.put("user", user);
    return retVal;

  }
  public User()
  {

  }
  public User(String username, String email, String password, String token, String bio, String image) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.token = token;
    this.bio = bio;
    this.image = image;
  }

  public User(JsonObject jsonObject) {

    if (jsonObject.containsKey("_id")) this._id = jsonObject.getString("_id");
    this.username = jsonObject.getString("username");
    this.email = jsonObject.getString("email");
    this.password = jsonObject.getString("password");
    if (jsonObject.containsKey("token")) this.token = jsonObject.getString("token");
    if (jsonObject.containsKey("image")) this.image = jsonObject.getString("image");
    if (jsonObject.containsKey("bio")) this.bio = jsonObject.getString("bio");
    if (jsonObject.containsKey("salt")) this.salt = jsonObject.getString("salt");

  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }


  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public JsonObject toJson() {
    JsonObject retVal = new JsonObject()
      .put("username", username)
      .put("bio", bio)
      .put("image", image)
      .put("email", email);

       return retVal;
  }
  public JsonObject toMongoJson() {
    JsonObject retVal = new JsonObject();
    if (this._id != null) {
      retVal.put("_id", this._id);
    }
    retVal.put("username", username)
      .put("email", email)
      .put("token", token)
      .put("bio", bio)
      .put("password", this.password)
      .put("salt", this.salt);

    return retVal;
  }
}
