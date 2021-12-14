package me.marty.openpixelmon.client.model.smd.animation;

import dev.thecodewarrior.binarysmd.studiomdl.SkeletonBlock;

import java.util.ArrayList;
import java.util.List;

public class Keyframe {

    public int time;
    public List<BoneState> states = new ArrayList<>();

    public Keyframe(SkeletonBlock.Keyframe keyframe) {
        this.time = keyframe.time;
        for (SkeletonBlock.BoneState state : keyframe.states) {
            states.add(new BoneState(state));
        }
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
            this.rotX = (float) Math.toDegrees(state.rotX);
            this.rotY = (float) Math.toDegrees(-state.rotY);
            this.rotZ = (float) Math.toDegrees(-state.rotZ);
        }
    }
}
