#version 150

#moj_import <light.glsl>

const int MAX_BONES = 64;
const int MAX_WEIGHTS = 4;

in vec3 Position;
in vec2 UV0;
in vec4 Color;
in vec3 Normal;
in int BoneIds[MAX_WEIGHTS];
in float BoneWeights[MAX_WEIGHTS];

uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
uniform mat4 BoneTransformations[MAX_BONES];

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec4 normal;

void main() {
    vec4 totalLocalPos = vec4(0.0);
    vec4 totalNormal = vec4(0.0);

    for (int i = 0; i < MAX_WEIGHTS; i++) {
        mat4 boneTransform = BoneTransformations[BoneIds[i]];
        vec4 posePosition = boneTransform * vec4(Position, 1.0);
        totalLocalPos += posePosition * BoneWeights[i];// This is actually quite smart. when the weight is out of bounds, it returns 0. Meaning that the transformation wont matter

        vec4 worldNormal = boneTransform * vec4(Normal, 1.0);
        totalNormal += worldNormal * BoneWeights[i];
    }

    gl_Position = ProjMat * totalLocalPos;

    vertexDistance = length((ModelViewMat * vec4(Position, 1.0)).xyz);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * totalNormal;
}
