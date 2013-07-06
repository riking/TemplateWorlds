TemplateWorlds
==============

Library plugin to have Bukkit worlds that can be restored to an unchanging template.

This project uses [semantic versioning](http://semver.org/spec/v2.0.0.html) for its `api` package. The `impl` package is not versioned.

### Usage guide
Only the classes in the `api` package should be used by other plugins.

```java
ApiMain twApi = ((TemplateWorlds) getPluginManager().getPlugin("TemplateWorlds")).getApi();
World templateLobby = getServer().createWorld(new WorldCreator("lobby-template"));
this.lobby = twApi.createWorld("lobby", templateLobby);
```
