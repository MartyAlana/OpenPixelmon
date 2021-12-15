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
            if (block instanceof NodesBlock nodeBlock) {
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
            if (block instanceof SkeletonBlock skeletonBlock) {
                for (SkeletonBlock.Keyframe keyframe : skeletonBlock.keyframes) {
                    data.keyframes.add(new Keyframe(keyframe, data.bones));
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
