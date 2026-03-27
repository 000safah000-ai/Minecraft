import os
import glob
import subprocess

def find_java_17():
    for path in glob.glob("/usr/lib/jvm/*17*"):
        if os.path.exists(os.path.join(path, "bin", "java")):
            return path
    # Try alternative locations
    for path in glob.glob("/etc/alternatives/java"):
         res = subprocess.run(["readlink", "-f", "/etc/alternatives/java"], capture_output=True, text=True)
         if "17" in res.stdout:
              return res.stdout.strip().replace("/bin/java", "")
    return None

java_home = find_java_17()
env = os.environ.copy()
if java_home:
    print(f"Found Java 17 at {java_home}")
    env["JAVA_HOME"] = java_home
else:
    print("Java 17 not found, trying anyway")

subprocess.run(["./gradlew", "build"], cwd="/workspaces/Minecraft/AutoFakePlayer", env=env)
