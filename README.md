TemplateWorlds
==============

Library plugin to have Bukkit worlds that can be restored to an unchanging template.

This project uses [semantic versioning](http://semver.org/spec/v2.0.0.html) for its `api` package. The `impl` package is not versioned.

### Usage guide
Only the classes in the `api` package should be used by other plugins.

#### Simple usage
Here, we create a 'lobby' world which is a clone of 'lobby-template'. Note that the template world is not stored, while the created lobby world is. 
```java
ApiMain templateApi = getServer().getServicesManager().getRegistration(ApiMain.class).getProvider();
World templateLobby = getServer().createWorld(new WorldCreator("lobby-template"));
this.lobby = twApi.createWorld("lobby", templateLobby);
```
Let's say that while the players are waiting for the game to start, the lobby world gets a bit trashed. To revert it back, you want to first remove all players from that world - maybe the game starts, so there's nobody there anymore.
Use the resetArea() method over the whole area that the lobby is in:
```java
twApi.resetArea(this.lobby, -5, -5, 5, 5);
```
