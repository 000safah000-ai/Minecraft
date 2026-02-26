import shutil
import subprocess

src = '/workspaces/Minecraft/Imported/orbital-railgun-main/gradle/wrapper/gradle-wrapper.jar'
dst = '/workspaces/Minecraft/JujutsuShenanigans/gradle/wrapper/gradle-wrapper.jar'

shutil.copy2(src, dst)

result = subprocess.run(
    ['./gradlew', 'build'],
    cwd='/workspaces/Minecraft/JujutsuShenanigans',
    capture_output=True,
    text=True
)

print("STDOUT:")
print(result.stdout)
print("STDERR:")
print(result.stderr)
