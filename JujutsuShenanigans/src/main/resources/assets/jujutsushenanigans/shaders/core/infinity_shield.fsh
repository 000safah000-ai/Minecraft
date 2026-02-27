#version 150

uniform float GameTime;
uniform vec4 ColorModulator;

in vec4 vertexColor;
in vec2 texCoord0;
in vec3 vNormal;
in vec3 vPos;

out vec4 fragColor;

// Simple noise function
float hash(vec2 p) {
    return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(mix(hash(i + vec2(0.0, 0.0)), hash(i + vec2(1.0, 0.0)), u.x),
               mix(hash(i + vec2(0.0, 1.0)), hash(i + vec2(1.0, 1.0)), u.x), u.y);
}

void main() {
    // Base color: Light Blue
    vec3 baseColor = vec3(0.2, 0.6, 1.0);
    
    // Calculate a flowing energy effect using noise and time
    float time = GameTime * 1000.0; // Adjust speed
    
    // Create a hexagonal or grid-like pattern using UVs or position
    vec2 uv = texCoord0 * 10.0;
    float n = noise(uv + vec2(time * 0.1, time * 0.2));
    
    // Edge detection / Fresnel effect (brighter at the edges)
    // Assuming view direction is roughly along Z in view space, but we don't have view pos here easily.
    // We'll fake it with normals.
    float edge = 1.0 - abs(vNormal.z);
    edge = smoothstep(0.5, 1.0, edge);
    
    // Combine effects
    float intensity = n * 0.5 + edge * 0.8;
    
    // Final color
    vec4 finalColor = vec4(baseColor * intensity, intensity * 0.6); // Semi-transparent
    
    fragColor = finalColor * ColorModulator;
}