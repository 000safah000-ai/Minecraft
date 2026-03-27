import os
import subprocess
import glob

print("Starting python script to run gradle with java 17")
os.chdir("/workspaces/Minecraft/AutoFakePlayer")
# Find Java 17
java17_candidates = glob.glob("/home/codespace/.sdkman/candidates/java/17*")
if java17_candidates:
    os.environ["JAVA_HOME"] = java17_candidates[0]
else:
    java17_candidates = glob.glob("/usr/lib/jvm/*17*")
    if java17_candidates:
        os.environ["JAVA_HOME"] = java17_candidates[0]

print("USING JAVA:", os.environ.get("JAVA_HOME"))

res = subprocess.run(["./gradlew", "build"], capture_output=True, text=True)
print("Return code:", res.returncode)
print("STDOUT:")
print(res.stdout)
print("STDERR:")
print(res.stderr)
