TemplateWorlds
==============

Library plugin to have Bukkit worlds that can be restored to an unchanging template.

This project uses [semantic versioning](http://semver.org/spec/v2.0.0.html) for its `api` package. The `impl` package is not versioned.

### Usage guide
Only the classes in the `api` package should be used by other plugins.

For development with Maven, put this in your pom.xml `dependencies` section:
```xml
    <dependency>
      <groupId>com.github.riking.lib.templateworlds</groupId>
    	<artifactId>templateworlds</artifactId>
    	<version>[2.1.0, 3.0.0)</version>
    	<scope>provided</scope>
    </dependency>
```
This lets you develop with updates for any API upgrades until a breakage, which would be version 3.0.0.
*(If you are able to provide Maven repository hosting, please contact me and I will put the info here!)*


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

#### Multiple templates
If you want to have multiple maps that you cycle through for your game, you will need to call `changeTemplate(World, World)` followed by a `resetArea`.
changeTemplate will modify the internal reference used by the generator, which will be referred to during resetArea.

Here's an example map cycle procedure. Q is some arbitrary part of your plugin that holds the variables shown.
```java
public class Q {
    // ...
    ApiMain twApi;
    World gameWorld; // Playing world. This is 'templated'
    World currentMap;
    List<String> maps;
    // ...
    public String nextMap() {
        int ind = random.nextInt(maps.size());
        return maps.get(ind);
    }
    // ...
}

public class ChangeMapPart1 implements Runnable {
    private Q q;
    public ChangeMapPart1(Q q) {
        this.q = q;
    }

    public void run() {
        // Get everyone out
        // !! Beware of BUKKIT-1331 - if you trigger on death, this must be run through scheduler
        for (Player p : q.gameWorld.getPlayers()) {
            p.teleport(q.lobbyEntry);
        }

        // Unload the old map (optional)
        Bukkit.unloadWorld(q.currentMap, false);

        // Choose the next map. Load it with a VoidGenerator to avoid save inflation. 
        String nextMap = q.nextMap();
        // broadcast something in chat
        // [G] Loading map ctf_towers...
        q.currentMap = new WorldCreator(nextMap).generator(q.twApi.getVoidGenerator());

        // Change the template being used by the game world
        q.twApi.changeTemplate(q.gameWorld, q.currentMap);

        // Change the GameWorld to the new map (this is an approx. 1000x1000 area - (-512, +512))
        q.twApi.resetAreaGradually(q.gameWorld, -32, -32, 32, 32, new ChangeMapPart2(q));
    }
}

```
