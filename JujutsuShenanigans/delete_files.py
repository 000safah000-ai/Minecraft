import os
import shutil

src_dir = '/workspaces/Minecraft/JujutsuShenanigans/src'
keep_file = 'six_eyes.png'

for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file != keep_file:
            os.remove(os.path.join(root, file))

print("Deleted all files except", keep_file)
