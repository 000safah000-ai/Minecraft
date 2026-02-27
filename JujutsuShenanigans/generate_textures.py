#!/usr/bin/env python3
"""Generate valid PNG textures for JujutsuShenanigans mod."""
import struct, zlib, os

def create_png(width, height, pixels, filepath):
    """Create a minimal PNG file. pixels is a list of (r,g,b,a) tuples."""
    def chunk(chunk_type, data):
        c = chunk_type + data
        return struct.pack('>I', len(data)) + c + struct.pack('>I', zlib.crc32(c) & 0xFFFFFFFF)
    sig = b'\x89PNG\r\n\x1a\n'
    ihdr_data = struct.pack('>IIBBBBB', width, height, 8, 6, 0, 0, 0)
    ihdr = chunk(b'IHDR', ihdr_data)
    raw = b''
    for y in range(height):
        raw += b'\x00'
        for x in range(width):
            r, g, b, a = pixels[y * width + x]
            raw += struct.pack('BBBB', r, g, b, a)
    idat = chunk(b'IDAT', zlib.compress(raw))
    iend = chunk(b'IEND', b'')
    os.makedirs(os.path.dirname(filepath), exist_ok=True)
    with open(filepath, 'wb') as f:
        f.write(sig + ihdr + idat + iend)
    print(f"  Created {filepath} ({width}x{height}, {os.path.getsize(filepath)} bytes)")

base = os.path.join(os.path.dirname(os.path.abspath(__file__)),
    'src/main/resources/assets/jujutsushenanigans')

print("Generating textures...")

# 1. six_eyes_glow.png - 64x64 RGBA for RenderLayer.getEyes()
w, h = 64, 64
px = [(0, 0, 0, 0)] * (w * h)
for y in range(12, 14):
    for x in range(20, 24):
        px[y * w + x] = (100, 200, 255, 255)
    for x in range(28, 32):
        px[y * w + x] = (100, 200, 255, 255)
for y in range(11, 15):
    for x in range(19, 25):
        if px[y * w + x][3] == 0:
            px[y * w + x] = (50, 100, 200, 100)
    for x in range(27, 33):
        if px[y * w + x][3] == 0:
            px[y * w + x] = (50, 100, 200, 100)
create_png(w, h, px, os.path.join(base, 'textures/entity/six_eyes_glow.png'))

# 2. infinity_shield.png - 1x1 transparent placeholder
create_png(1, 1, [(0, 0, 0, 0)], os.path.join(base, 'textures/entity/infinity_shield.png'))

print("Done!")
