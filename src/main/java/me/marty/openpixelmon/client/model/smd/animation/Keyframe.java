package me.marty.openpixelmon.client.model.smd.animation;

import dev.thecodewarrior.binarysmd.studiomdl.SkeletonBlock;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

public class Keyframe {

    public static final int MAX_BONE_TRANSFORMATIONS = 128;
    public final int time;
    public final List<BoneState> states = new ArrayList<>();
    public final Matrix4f[] boneTransformations = new Matrix4f[MAX_BONE_TRANSFORMATIONS];

    public Keyframe(SkeletonBlock.Keyframe keyframe, List<Bone> bones) {
        this.time = keyframe.time;
        for (SkeletonBlock.BoneState state : keyframe.states) {
            states.add(new BoneState(state));
        }

        compileTransformations(bones);
    }

    private void compileTransformations(List<Bone> bones) {
        for (BoneState state : states) {
            boneTransformations[state.bone] = compileBoneTransform(bones, states, state);
        }
    }

    private Matrix4f compileBoneTransform(List<Bone> bones, List<BoneState> boneStates, BoneState boneState) {
        Matrix4f transformMatrix = getTransform(boneState);
        Bone bone = bones.get(boneState.bone);
        if (bone.parent != -1) {
            for (BoneState childBoneState : boneStates) {
                if (childBoneState.bone == bone.parent) {
                    transformMatrix = transformMatrix.mul(compileBoneTransform(bones, boneStates, childBoneState));
                    break;
                }
            }
        }

        return transformMatrix;
    }

    private Matrix4f getTransform(BoneState state) {
        Matrix4f matrix = new Matrix4f().translate(state.posX, state.posY, state.posZ);
        matrix.rotateXYZ(state.rotX, state.rotY, state.rotZ); //TODO: might need degrees?
        return matrix;
    }

    public static class BoneState {
        public int bone;
        public float posX, posY, posZ;
        public float rotX, rotY, rotZ;

        public BoneState(SkeletonBlock.BoneState state) {
            this.bone = state.bone;
            this.posX = state.posX;
            this.posY = -state.posY;
            this.posZ = -state.posZ;
            this.rotX = state.rotX;
            this.rotY = -state.rotY;
            this.rotZ = -state.rotZ;
        }
    }
}
