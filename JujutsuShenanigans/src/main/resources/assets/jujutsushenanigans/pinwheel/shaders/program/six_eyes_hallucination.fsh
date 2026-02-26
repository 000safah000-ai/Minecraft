#version 150

uniform sampler2D DiffuseSampler;
uniform float Time;
uniform float Intensity; // 0.0 to 1.0 (increases as timer goes up)

in vec2 texCoord;
out vec4 fragColor;

// Pseudo-random noise
float hash(vec2 p) {
    return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
}

// Fractal Brownian Motion
float fbm(vec2 p) {
    float f = 0.0;
    float w = 0.5;
    for (int i = 0; i < 5; i++) {
        f += w * hash(p);
        p *= 2.0;
        w *= 0.5;
    }
    return f;
}

void main() {
    vec2 uv = texCoord;
    
    // 1. Distortion (fBM based on time)
    float noiseVal = fbm(uv * 10.0 + Time * 2.0);
    vec2 distortedUV = uv + vec2(noiseVal - 0.5) * 0.05 * Intensity;
    
    // 2. Chromatic Aberration (Glitch)
    float glitchOffset = 0.02 * Intensity * sin(Time * 10.0);
    float r = texture(DiffuseSampler, distortedUV + vec2(glitchOffset, 0.0)).r;
    float g = texture(DiffuseSampler, distortedUV).g;
    float b = texture(DiffuseSampler, distortedUV - vec2(glitchOffset, 0.0)).b;
    vec3 color = vec3(r, g, b);
    
    // 3. Blue/Purple Overlay
    vec3 overlayColor = vec3(0.2, 0.0, 0.8); // Deep purple/blue
    color = mix(color, overlayColor, 0.4 * Intensity);
    
    // 4. Vignette & Darkening
    float dist = distance(uv, vec2(0.5));
    float vignette = smoothstep(0.8, 0.2, dist * (1.0 + Intensity));
    color *= vignette;
    
    // Overall darkening as intensity increases
    color *= (1.0 - Intensity * 0.8);
    
    fragColor = vec4(color, 1.0);
}
