#version 330 core
#extension GL_ARB_shader_storage_buffer_object : require

#moj_import <light.glsl>

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

uniform int boneCount;

struct BoneState {
    float posX;
    float posY;
    float posZ;
    float rotX;
    float rotY;
    float rotZ;
};

layout(std430, binding = 1) readonly restrict buffer animationLayout {
    BoneState[] boneStates;
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

    for (int i = 0; i < renderBoneMapStorage.renderBoneMap.length; i++) {
        int boneId = renderBoneMapStorage.renderBoneMap[i];
        float boneWeight = boneWeightStorage.boneWeights[boneId];
        BoneState boneState = animationStorage.boneStates[boneId];

        // Do burger math
        float sx = sin(boneState.rotX);
        float cx = cos(boneState.rotX);
        float sy = sin(boneState.rotY);
        float cy = cos(boneState.rotY);
        float sz = sin(boneState.rotZ);
        float cz = cos(boneState.rotZ);

        mat4 boneTransform = mat4(
        cy * cz, (sx * sy * cz) - (cx * sz), (cx * sy * cz) + (sx * sz), 1.0,
        cy * sz, (sx * sy * sz) + (cx * cz), (cx * sy * sz) - (sx * cz), 1.0,
        -sy, sx * cy, cx * cy, 1.0,
        boneState.posX, boneState.posY, boneState.posZ, 1.0);

        vec4 posePosition = boneTransform * vec4(Position, 1.0);
        totalLocalPos += posePosition * boneWeight; //When the weight is out of bounds, it returns 0. Meaning that the transformation wont matter

//        vec4 worldNormal = boneTransform * vec4(Normal, 1.0);
//        totalNormal += worldNormal * boneWeight;
    }

    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexDistance = length((ModelViewMat * vec4(Position, 1.0)).xyz);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * totalNormal;
}
