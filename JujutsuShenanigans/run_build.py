import subprocess
import sys

try:
    result = subprocess.run(['./gradlew', 'build'], cwd='/workspaces/Minecraft/JujutsuShenanigans', capture_output=True, text=True)
    print("STDOUT:")
    print(result.stdout)
    print("STDERR:")
    print(result.stderr)
    print("Return code:", result.returncode)
except Exception as e:
    print("Error:", e)
