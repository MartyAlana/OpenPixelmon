package me.marty.openpixelmon.client.model.smd.animation;

import dev.thecodewarrior.binarysmd.studiomdl.NodesBlock;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFile;
import dev.thecodewarrior.binarysmd.studiomdl.SMDFileBlock;
import dev.thecodewarrior.binarysmd.studiomdl.SkeletonBlock;

import java.util.List;

public class Animator {

    /**
     * Loads the animation data of a model
     * @param animation the model to load animations from
     * @return parsed animation data
     */
    public static AnimationData getAnimData(SMDFile animation) {
        AnimationData data = new AnimationData();
        for (SMDFileBlock block : animation.blocks) {
            if (block instanceof NodesBlock) {
                NodesBlock nodeBlock = (NodesBlock) block;
                for (NodesBlock.Bone bone : nodeBlock.bones) {
                    Bone b = new Bone(bone);
                    if(bone.parent == -1){
                        data.root = b;
                    }
                    data.boneMap.put(bone.name, bone.id);
                    prepareIndex(data.bones, bone.id);
                    data.bones.set(bone.id, b);
                }
            }
            if (block instanceof SkeletonBlock) {
                SkeletonBlock nodeBlock = (SkeletonBlock) block;
                for (SkeletonBlock.Keyframe keyframe : nodeBlock.keyframes) {
                    data.keyframes.add(new Keyframe(keyframe));
                }
            }
        }
        return data;
    }

    private static void prepareIndex(List<Bone> bones, int i) {
        while (bones.size() <= i) {
            bones.add(null);
        }
    }
}
