#*AGENTS. MD*
1. *Firstly And before anything you do,*
- This Project is a Minecraft Jujutsu-Kaisen Mod based on fabric mod loader, libraries and GLSL shaders, You have to work with Satin for post-processing, GLSL and Veil for Managing and creating raw new shaders particularly for the mod and finally Geckolib for any 3d - models work that we will do. 
- Check TODO.md file, if it has any work in-progress that's an indicator that this is the work we need to do (task) in this request and if you are planning on doing something, when you finish planning (thinking) put it in the TODO file as in-progress task. 
- When you finish Working on in-progress or the request I asked you, make sure u mark it as a Done  task in  the TODO file and unmark it from in-progress tasks. 
2. *Secondly - Before Planning - Rules,*
- Read PROMPTS.md file and take the role of the prompt and treat them as your main instructions and follow them consistently - the file may include multiple prompts. Take them all, compress the information and follow their rules - peacefully and constantly.
- Read CONSTRAINTS.md Carefully and *DO NOT CROSS ANY BORDERS FORBIDDEN IN IT* follow the rules and don't hallucinate.
3. *Thirdly And after planning.*
- Do the TODO list file based on MY request and your planning on it.
- Commit to all previous instructions.
- Start working on the requests - based on the TODO file of course - And think hard before creating any files and concentrate. 
- I also want to indicate that you must ensure ultra high quality AAA cinematic work including anything in the mod (etc: skills, domains, scenes, curses, systems, animations.). 

4. *Build Configuration (Gradle)*
*Repositories*
Add these repositories to `build.gradle`:
- `https://maven.ladysnake.org/releases` (for Satin)
- `https://api.modrinth.com/maven` (for Veil, use `exclusiveContent` pattern)

 *Dependencies*
 Use Fabric Loom (Fabric's Gradle plugin). Add dependencies with:
 - **Satin**: `modImplementation "org.ladysnake:satin:${satin_version}"` and `include "org.ladysnake:satin:${satin_version}"`
 - **Veil**: `modImplementation "maven.modrinth:veil:${veil_version}"` (no `include` needed unless required)
 - **Fabric API*: `modImplementation "net.fabricmc.fabric-api:fabric-api:${fabric_version}"`

  *Version Properties*
  Define versions in `gradle.properties`:
  ```properties
  minecraft_version=1.20.1
  fabric_version=0.86.1+1.20.1
  loader_version=0.15.11
  satin_version=1.12.0+1.20.1
  veil_version=3.2.0+1.20.1
  
  ##*4. Build Workflow and constraints.*
  - . *DO NOT USE* the default 'run_in_terminal' tool because it's broken (ENOPRO error)
  - *DO NOT RELY* on subagents to run terminal commands, subagents can't use the right tool 'terminal-tools_sendCommand' Only the main agent (you) can, only use them for workspace indexing (e.g., listing files, reading files, summarizing code and others) or planning. 
  - *ALWAYS USE* 'terminal-tools_sendCommand' if available to execute shell commands.
  - *ALWAYS TARGET* a specific terminal name (e.g.,   '"build"'.) 

  - *To run a build :*
  1. *Use* /'terminal-tools_sendCommand'/
  - *Terminal* : /"build"/
  - *Command* : "cd /workspaces/Test/Project && ./gradlew build --no-daemon" /( *ALWAYS* replace "Test","Project" With the real names of the directories and foldiers you are working on don't use these example names.) /
  2. *Read* the output of the build after it finishes using /"terminal_last_command"/ tool with the same terminal name. /( note : if you check the terminal and the build isn't finished yet retry the command several times until the build finishes and if you dont see any build or output retry the command with the right inputs and names.) /
  - *Example for a build :*
  1. Send the build command : /"'terminal-tools_sendCommand(terminal="build", command="cd /workspaces/Test/Project && ./gradlew build")'"/
  2. Reading the output until it finishes : /"'terminal_last_command(terminal="build")'"/
  3. If a build fails follow the previous instructions of this file and other files ( PROMPTS.md, CONSTRAINTS.md .) 
  - Fix the issue in the relevant source files (build.gradle, fabric.mod.json, Java classes).
  - Repeat the build cycle until it succeeds.

  