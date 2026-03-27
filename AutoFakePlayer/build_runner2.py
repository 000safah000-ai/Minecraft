import os
import subprocess
import glob

os.chdir("/workspaces/Minecraft/AutoFakePlayer")
# Find Java 17
java17_candidates = glob.glob("/usr/local/sdkman/candidates/java/17*")
if java17_candidates:
    os.environ["JAVA_HOME"] = java17_candidates[0]
else:
    java17_candidates = glob.glob("/usr/lib/jvm/*17*")
    if java17_candidates:
        os.environ["JAVA_HOME"] = java17_candidates[0]

res = subprocess.run(["./gradlew", "build"], capture_output=True, text=True)
print("STDOUT:")
print(res.stdout)
print("STDERR:")
print(res.stderr)
