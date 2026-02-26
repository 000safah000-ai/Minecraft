#!/usr/bin/env python3
import subprocess
import urllib.request
import os
import shutil

project_dir = "/workspaces/Minecraft/JujutsuShenanigans"
wrapper_jar = os.path.join(project_dir, "gradle", "wrapper", "gradle-wrapper.jar")

# Step 1: Try to regenerate wrapper using system gradle
print("Step 1: Regenerating gradle wrapper...")
result = subprocess.run(
    ["gradle", "wrapper", "--gradle-version", "8.10"],
    cwd=project_dir,
    capture_output=True,
    text=True
)
print(result.stdout)
if result.stderr:
    print(result.stderr)

if os.path.exists(wrapper_jar):
    print(f"Wrapper jar exists: {os.path.getsize(wrapper_jar)} bytes")
else:
    print("Wrapper jar NOT found after gradle wrapper command")

# Step 2: Run the build
print("\nStep 2: Running ./gradlew build...")
env = os.environ.copy()
java_home = "/usr/local/sdkman/candidates/java/21.0.9-ms"
if os.path.exists(java_home):
    env["JAVA_HOME"] = java_home
    env["PATH"] = f"{java_home}/bin:{env['PATH']}"

result = subprocess.run(
    ["./gradlew", "build", "--stacktrace"],
    cwd=project_dir,
    env=env,
    capture_output=True,
    text=True,
    timeout=300
)

print("=== STDOUT (last 4000 chars) ===")
print(result.stdout[-4000:] if len(result.stdout) > 4000 else result.stdout)
print("=== STDERR (last 4000 chars) ===")
print(result.stderr[-4000:] if len(result.stderr) > 4000 else result.stderr)
print(f"=== Return code: {result.returncode} ===")
