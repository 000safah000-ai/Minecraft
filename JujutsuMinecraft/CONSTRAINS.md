##1. *Firstly, info and main rules.*
1. *This is the main file containing the Prohibited items that you can't do or cross.*
2. *You must deal with the rules here as forbidden borders that you commit to.*
3. *Read this file before doing any work, (note : if you encounter a problem that requires breaking the rules, take my permission on it, if I say yes do it and the same for no.).*
## 2. *Secondly, The borders and rules.*
- *DO NOT USE* any external libraries other than mentioned in AGENTS.md without my permission. 
- *DO NOT DELETE* any files without my commission or permission.
- *DO NOT END* any task without testing build with (gradle/gradlew) if failure fix the error then test again until you get a successful build.
- *USE* javadoc comments and 'LOGGER' for errors in new classes.
- *YOU MUST* register any new classes, shaders and files in the main (client/server) classes and files so the mod should load all shaders and classes in it.
DO NOT CREATE* multiple empty terminals if there was already one open, also if you want to test build open *ONLY* one terminal /*THEN SEND A REAL BUILD COMMAND USING TOOLS I GAVE YOU IN AGENTS.md DO NOT DO A LOOP OF EMPTY TERMINALS*/ /( *note :* If you want to send a command or build and you opened a terminal, After opening a terminal send the command immediately because the terminal you created will be automatically made and used as default at the codespace so make a terminal then send the command dont check if the terminal is available or open just concentrate on sending the command to the terminal you opened just assume its open, no need to over-check because there is no official tool for checking terminals.) /
- *AFTER SENDING* to ensure that the terminal and the command opened and sent successfully check the output using /last_terminal_command/ I suppose you know that earlier. 

##3. Thirdly, Strict Architecture Rules.
### 1. Library Versions :
- *BEFORE* adding any library, verify compatibility with Minecraft 1.20.1.
- *Use* `curl` to query Modrinth API: `curl -s https://api.modrinth.com/v2/project/<project>/version | jq`.
### 2. Event Registration :
- All event listeners *MUST* be registered in the mod initializer (or client initializer).
- Fabric API does *NOT* auto-register events.
- *Example:* /`YourEvent.EVENT.register(() -> { ... });`/
### 3. Client vs Server Code :
- *Mark* client-only code with /`@Environment(EnvType.CLIENT)`./
- *Mark* server-only code with /`@Environment(EnvType.SERVER)`./
- Common code goes in /`src/main/java/`./
- Client code goes in /`src/client/java/`./
### 4. Assets :
- Client assets: /`src/client/resources/assets/<modid>/`./
- Common assets: `src/main/resources/assets/<modid>/`.
- Textures, models, shaders: place in correct subfolder (`textures/`, `models/`, `shaders/`).
### 5. Keybinds
- Keybinds *MUST* be registered in /`ClientModInitializer`./
- Example: /`KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mod.laser", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.mod"));`/
### 6. Mixins
- *ONLY* add mixins if absolutely necessary.
- If added, the mixin config file *AND* at least one mixin class *MUST* exist.
- Mixin config goes in /`src/main/resources/<modid>.mixins.json`./
### 7. Entrypoints & Classes
- For *EVERY* entrypoint in /`fabric.mod.json`,/ the corresponding Java class MUST exist.
- Client entrypoint class *MUST* be in `src/client/java/<package>/<ClassName>.java` and implement `ClientModInitializer`.
- Main entrypoint class *MUST* be in /`src/main/java/<package><ClassName>.java`/ and implement `ModInitializer`.
- *NEVER* add an entrypoint without creating its class.
- *Example:* If `"client"` entrypoint points to `com.jujutsuminecraft.client.JujutsuMinecraftClient`, the file `src/client/java/com/jujutsuminecraft/client/JujutsuMinecraftClient.java` *MUST* exist with `onInitializeClient()` method.