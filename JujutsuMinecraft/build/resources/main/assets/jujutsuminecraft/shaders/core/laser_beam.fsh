#version 150

in vec2 texCoord;

out vec4 fragColor;

uniform float time;
uniform vec3 coreColor;
uniform vec3 outlineColor;
uniform vec3 effectColor;

// ==========================================
// ADVANCED NOISE AND MATH FUNCTIONS
// ==========================================

// High-frequency hash for cellular noise and particles
vec2 hash2(vec2 p) {
    vec3 p3 = fract(vec3(p.xyx) * vec3(.1031, .1030, .0973));
    p3 += dot(p3, p3.yzx + 33.33);
    return fract((p3.xx+p3.yz)*p3.zy) * 2.0 - 1.0;
}

// Simplex noise for smooth, organic movement
float snoise(vec2 p) {
    const float K1 = 0.366025404; // (sqrt(3)-1)/2
    const float K2 = 0.211324865; // (3-sqrt(3))/6
    
    vec2 i = floor(p + (p.x+p.y)*K1);
    vec2 a = p - i + (i.x+i.y)*K2;
    vec2 o = (a.x>a.y) ? vec2(1.0,0.0) : vec2(0.0,1.0);
    vec2 b = a - o + K2;
    vec2 c = a - 1.0 + 2.0*K2;
    
    vec3 h = max(0.5-vec3(dot(a,a), dot(b,b), dot(c,c)), 0.0);
    vec3 n = h*h*h*h*vec3( dot(a,hash2(i+0.0)), dot(b,hash2(i+o)), dot(c,hash2(i+1.0)));
    return dot(n, vec3(70.0));
}

// Fractal Brownian Motion for rich textures
float fbm(vec2 p) {
    float f = 0.0;
    float amp = 0.5;
    vec2 shift = vec2(100.0);
    for (int i = 0; i < 6; i++) {
        f += amp * snoise(p);
        p = p * 2.0 + shift;
        amp *= 0.5;
    }
    return f;
}

// Domain Warping for fluid, heavy, molten plasma
float warp(vec2 p, out vec2 q, out vec2 r) {
    q = vec2(fbm(p + vec2(0.0,0.0)), fbm(p + vec2(5.2,1.3)));
    r = vec2(fbm(p + 4.0*q + vec2(1.7,9.2)), fbm(p + 4.0*q + vec2(8.3,2.8)));
    return fbm(p + 4.0*r);
}

// Ridged noise specifically isolated for sharp lightning tendrils
float ridgedNoise(vec2 p) {
    float n = snoise(p);
    return 1.0 - abs(n);
}

// Generates chaotic lightning patterns traversing the beam
float lightning(vec2 p, float t) {
    float f = 0.0;
    float amp = 0.5;
    for (int i = 0; i < 4; i++) {
        f += amp * ridgedNoise(p - vec2(0.0, t * 2.0));
        p *= 2.5;
        amp *= 0.5;
        t *= 1.5;
    }
    return pow(f, 3.0); // Pow sharpens the tendrils making them look electric
}

// Simulated pseudo-3D cylindrical normals
vec3 getCylinderNormal(vec2 uv) {
    float nx = (uv.y - 0.5) * 2.0; // Assume vertical beam orientation via UVs
    float dist = abs(nx);
    if (dist > 1.0) return vec3(0.0, 0.0, 1.0); // Fallback outside boundary
    float nz = sqrt(1.0 - nx*nx);
    return normalize(vec3(nx, 0.0, nz));
}

// Procedural integrated particles moving dynamically down the beam
float particles(vec2 uv, float speed) {
    // Create a streaming cellular grid
    vec2 gridUv = uv * vec2(10.0, 40.0);
    gridUv.x -= time * speed; // Match the flow direction
    
    vec2 id = floor(gridUv);
    vec2 f = fract(gridUv);
    
    float minDist = 1.0;
    // Standard Voronoi 3x3 search
    for (int y = -1; y <= 1; y++) {
        for (int x = -1; x <= 1; x++) {
            vec2 neighbor = vec2(float(x), float(y));
            vec2 p = hash2(id + neighbor);
            p = 0.5 + 0.5 * sin(time * 5.0 + 6.2831 * p); // Animated particle wobble
            
            vec2 diff = neighbor + p - f;
            float dist = length(diff);
            minDist = min(minDist, dist);
        }
    }
    
    // Isolate sharp luminous points and smooth them slightly
    return smoothstep(0.12, 0.03, minDist);
}

void main() {
    // Map coordinate system based on typical laser beam quad (Y=dist from center)
    // Adjust mapping if needed, x is usually length, y is width for lasers.
    vec2 uv = texCoord;
    float distFromCenter = abs(uv.y - 0.5) * 2.0; 
    
    // ==========================================
    // 1. LAYER 1: Core Geometry and Plasma Texture
    // ==========================================
    vec3 normal = getCylinderNormal(uv);
    float coreMask = 1.0 - smoothstep(0.2, 0.45, distFromCenter);
    
    // Intense, heavy, non-plasticy plasma surface using domain warping
    vec2 q, r;
    float plasma = warp(vec2(uv.x * 12.0 - time * 6.0, uv.y * 6.0), q, r);
    float coreWeight = clamp((plasma * 1.5) + (normal.z * 0.5), 0.0, 1.0);
    
    // Mix dynamic coloring
    vec3 activeCoreColor = mix(coreColor, vec3(1.0, 0.9, 0.9), coreWeight * 0.5);
    vec3 coreFrag = activeCoreColor * coreWeight * coreMask * 2.5;

    // ==========================================
    // 2. LAYER 2: Outline & Multi-layered Glow
    // ==========================================
    float outlineMask = smoothstep(0.25, 0.35, distFromCenter) - smoothstep(0.4, 0.7, distFromCenter);
    // Fresnel rim lighting explicitly for 3D outline shape pop
    float fresnel = pow(1.0 - max(dot(normal, vec3(0.0, 0.0, 1.0)), 0.0), 1.5);
    vec3 outlineLine = outlineColor * outlineMask * fresnel * 2.0;
    
    // Layer 2.2: Deep Multi-Step Volumetric Glow
    float innerGlow = smoothstep(0.65, 0.2, distFromCenter);
    float outerGlow = smoothstep(1.0, 0.3, distFromCenter);
    vec3 glowFrag = (outlineColor * innerGlow) + (outlineColor * 0.4 * outerGlow);

    // ==========================================
    // 3. LAYER 3: Complex Effects (Lightning, Helix, Particles)
    // ==========================================
    
    // 3.A: Rumbling Lightning striking across the surface into air
    float activeTime = time * 3.0;
    float lightningVal = lightning(vec2(uv.y * 5.0, uv.x * 2.5), activeTime);
    float lightningMask = smoothstep(0.9, 0.1, distFromCenter) * lightningVal;
    vec3 lightningFrag = effectColor * pow(lightningMask, 2.0) * 5.0;

    // 3.B: Double DNA Helix Spiral (orbiting simulation)
    // Front spiral (rendered strongly)
    float spiral1Offset = sin(uv.x * 20.0 - time * 12.0) * 0.4;
    float sDist1 = abs((uv.y - 0.5) - spiral1Offset);
    float sZ1 = cos(uv.x * 20.0 - time * 12.0); // Z-depth to fake 3D occlusion
    float sMask1 = smoothstep(0.04, 0.01, sDist1) * step(0.0, sZ1);
    
    // Back spiral (rendered faintly, intertwined)
    float spiral2Offset = sin(uv.x * 20.0 - time * 12.0 + 3.14159) * 0.4;
    float sDist2 = abs((uv.y - 0.5) - spiral2Offset);
    float sZ2 = cos(uv.x * 20.0 - time * 12.0 + 3.14159);
    float sMask2 = smoothstep(0.04, 0.01, sDist2) * step(0.0, sZ2);
    
    vec3 helixFrag = effectColor * (sMask1 * 1.5 + sMask2 * 0.4);

    // 3.C: Procedural Particles bounded by the outer glow mapping
    float streamParticles = particles(uv, 8.0) * smoothstep(0.8, 0.0, distFromCenter);
    vec3 particleFrag = vec3(1.0, 0.8, 1.0) * streamParticles * 2.0;

    // ==========================================
    // 4. OVERLAYS: Volumetric Bloom 
    // ==========================================
    float bloomNoise = fbm(vec2(distFromCenter * 3.0, uv.x * 3.0 - time));
    vec3 volumetricBloom = coreColor * outerGlow * bloomNoise * 1.2;

    // ==========================================
    // COMPOSITING & TONEMAPPING
    // ==========================================
    vec3 finalColor = coreFrag + outlineLine + glowFrag + lightningFrag + helixFrag + particleFrag + volumetricBloom;

    // Advanced AAA Filmic Tonemapping (ACES approximation)
    // Ensures blinding brights (laser core/sparks) roll off to brilliant white nicely
    finalColor = (finalColor * (2.51 * finalColor + 0.03)) / (finalColor * (2.43 * finalColor + 0.59) + 0.14);

    // Dynamic alpha mapped to luminosity and density
    float luminance = dot(finalColor, vec3(0.2126, 0.7152, 0.0722));
    float alpha = clamp(max(outerGlow, luminance), 0.0, 1.0);

    fragColor = vec4(finalColor, alpha);
}