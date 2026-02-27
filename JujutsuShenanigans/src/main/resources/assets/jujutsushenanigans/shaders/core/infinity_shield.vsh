#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float GameTime;

out vec4 vertexColor;
out vec2 texCoord0;
out vec3 vNormal;
out vec3 vPos;

void main() {
    // Expand the model slightly along the normal to make it an outline/shield
    vec3 expandedPos = Position + Normal * 0.2; // Slight expansion
    gl_Position = ProjMat * ModelViewMat * vec4(expandedPos, 1.0);

    vertexColor = Color;
    texCoord0 = UV0;
    vNormal = Normal;
    vPos = expandedPos;
}