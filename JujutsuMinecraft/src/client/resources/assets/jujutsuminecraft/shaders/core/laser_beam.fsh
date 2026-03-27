#version 150

in vec2 texCoord;

out vec4 fragColor;

uniform float time;
uniform vec3 coreColor;
uniform vec3 outlineColor;
uniform vec3 effectColor;

float snoise(vec2 v){
  return fract(sin(dot(v.xy, vec2(12.9898,78.233))) * 43758.5453123);
}

void main() {
    float dist = abs(0.5 - texCoord.y) * 2.0;

    float coreDensity = smoothstep(0.4, 0.0, dist);
    vec4 coreLayer = vec4(coreColor * 1.5, coreDensity);

    float outlineDensity = smoothstep(0.7, 0.3, dist) - coreDensity;
    vec4 outlineLayer = vec4(outlineColor, outlineDensity * 0.8);

    float noiseVal = snoise(vec2(texCoord.x * 10.0 + time * 5.0, texCoord.y * 10.0));
    float lightningDensity = step(0.8, noiseVal) * smoothstep(1.0, 0.0, dist);
    vec4 effectLayer = vec4(effectColor, lightningDensity);

    float bloomDensity = smoothstep(1.0, 0.0, dist) * 0.3;
    vec4 bloomLayer = vec4(coreColor * 0.5 + outlineColor * 0.5, bloomDensity);

    fragColor = coreLayer + outlineLayer + effectLayer + bloomLayer;
}