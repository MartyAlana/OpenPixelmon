#version 330 core
#extension GL_ARB_shader_storage_buffer_object : require

#moj_import <light.glsl>
const int MAX_WEIGHTS = 16;
const int MAX_BONES = 128;

in vec3 Position;
in vec2 UV0;
in vec4 Color;
in vec3 Normal;

uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

uniform float GameTime;

layout(std430, binding = 1) readonly restrict buffer animationLayout {
    mat4[MAX_BONES] boneTransforms;
} animationStorage;

layout(std430, binding = 2) readonly restrict buffer boneWeightLayout {
    float[] boneWeights;
} boneWeightStorage;

layout(std430, binding = 3) readonly restrict buffer renderBoneMapLayout {
    int[] renderBoneMap;
} renderBoneMapStorage;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec4 normal;

void main() {
    vec4 totalLocalPos = vec4(0.0);
    vec4 totalNormal = vec4(0.0);

    int weightOffset = gl_VertexID * MAX_WEIGHTS;
    for (int i = 0; i < MAX_WEIGHTS; i++) {
        int boneId = renderBoneMapStorage.renderBoneMap[i];
        float boneWeight = boneWeightStorage.boneWeights[weightOffset + i];
        mat4 boneTransform = animationStorage.boneTransforms[boneId];

        vec4 posePosition = boneTransform * vec4(Position, 1.0);
        totalLocalPos += posePosition * boneWeight;
    }

    gl_Position = ProjMat * ModelViewMat * totalLocalPos;
    vertexDistance = length((ModelViewMat * vec4(Position, 1.0)).xyz);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * totalNormal;
}
