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

uniform float GameTime;

uniform int boneCount;

struct BoneState {
    int parentId;// index of bone in boneWeightStorage & animationStorage
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

mat4 getTransformationMatrix(BoneState boneState) {
    // Do burger math
    float sx = sin(boneState.rotX);
    float cx = cos(boneState.rotX);
    float sy = sin(boneState.rotY);
    float cy = cos(boneState.rotY);
    float sz = sin(boneState.rotZ);
    float cz = cos(boneState.rotZ);

//    return mat4(cy * cz, cy * sz, -sy, boneState.posX,
//    (sx * sy * cz) - (cx * sz), (sx * sy * sz) + (cx * cz), sx * cy, boneState.posY,
//    (cx * sy * cz) + (sx * sz), (cx * sy * sz) - (sx * cz), cx * cy, boneState.posZ,
//    1.0, 1.0, 1.0, 1.0);

        return mat4(
        cy * cz, (sx * sy * cz) - (cx * sz), (cx * sy * cz) + (sx * sz), 1.0,
        cy * sz, (sx * sy * sz) + (cx * cz), (cx * sy * sz) - (sx * cz), 1.0,
        -sy, sx * cy, cx * cy, 1.0,
        boneState.posX, boneState.posY, boneState.posZ, 1.0);
}

BoneState mergeBones(BoneState boneState, BoneState parentBone) {
    boneState.posX += parentBone.posX;
    boneState.posY += parentBone.posY;
    boneState.posZ += parentBone.posZ;
    boneState.rotX += parentBone.rotX;
    boneState.rotY += parentBone.rotY;
    boneState.rotZ += parentBone.rotZ;
    return boneState;
}

int MAX_WEIGHTS_PER_VERT = 24;

void main() {
    vec4 totalLocalPos = vec4(0.0);
    vec4 totalNormal = vec4(0.0);

    for (int i = 0; i < boneCount; i++) { //TODO: dont process bones which dont matter?
        int boneId = renderBoneMapStorage.renderBoneMap[i];
        float boneWeight = boneWeightStorage.boneWeights[(gl_VertexID * MAX_WEIGHTS_PER_VERT) + i];// Get the offset of that vertexes weight data then get the weight
        BoneState boneState = animationStorage.boneStates[boneId];

        //FIXME: ugly hack because unsized arrays are not liked by GLSL
        if (boneState.parentId != -1) {
            BoneState parentBone = animationStorage.boneStates[boneState.parentId];
            boneState = mergeBones(boneState, parentBone);
            if (parentBone.parentId != -1) {
                parentBone = animationStorage.boneStates[parentBone.parentId];
                boneState = mergeBones(boneState, parentBone);
                if (parentBone.parentId != -1) {
                    parentBone = animationStorage.boneStates[parentBone.parentId];
                    boneState = mergeBones(boneState, parentBone);
                    if (parentBone.parentId != -1) {
                        parentBone = animationStorage.boneStates[parentBone.parentId];
                        boneState = mergeBones(boneState, parentBone);
                        if (parentBone.parentId != -1) {
                            parentBone = animationStorage.boneStates[parentBone.parentId];
                            boneState = mergeBones(boneState, parentBone);
                            if (parentBone.parentId != -1) {
                                parentBone = animationStorage.boneStates[parentBone.parentId];
                                boneState = mergeBones(boneState, parentBone);
                                if (parentBone.parentId != -1) {
                                    parentBone = animationStorage.boneStates[parentBone.parentId];
                                    boneState = mergeBones(boneState, parentBone);
                                    if (parentBone.parentId != -1) {
                                        parentBone = animationStorage.boneStates[parentBone.parentId];
                                        boneState = mergeBones(boneState, parentBone);
                                        if (parentBone.parentId != -1) {
                                            parentBone = animationStorage.boneStates[parentBone.parentId];
                                            boneState = mergeBones(boneState, parentBone);
                                            if (parentBone.parentId != -1) {
                                                parentBone = animationStorage.boneStates[parentBone.parentId];
                                                boneState = mergeBones(boneState, parentBone);
                                                if (parentBone.parentId != -1) {
                                                    parentBone = animationStorage.boneStates[parentBone.parentId];
                                                    boneState = mergeBones(boneState, parentBone);
                                                    if (parentBone.parentId != -1) {
                                                        parentBone = animationStorage.boneStates[parentBone.parentId];
                                                        boneState = mergeBones(boneState, parentBone);
                                                        if (parentBone.parentId != -1) {
                                                            parentBone = animationStorage.boneStates[parentBone.parentId];
                                                            boneState = mergeBones(boneState, parentBone);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        mat4 boneTransform = getTransformationMatrix(boneState);

        vec4 posePosition = boneTransform * vec4(Position, 1.0);
        totalLocalPos += posePosition * boneWeight;// When the weight is out of bounds, it returns 0. Meaning that the transformation wont matter

        vec4 worldNormal = boneTransform * vec4(Normal, 1.0);
        totalNormal += worldNormal * boneWeight;
    }

    gl_Position = ProjMat * ModelViewMat * totalLocalPos;
    vertexDistance = length((ModelViewMat * vec4(Position, 1.0)).xyz);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * totalNormal;
}
