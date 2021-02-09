package me.marty.openpixelmon.client.model.studiomdl.animation;

import java.util.List;
import java.util.Map;

public class AnimationData {
	public List<Bone> bones;
	public Map<String, Integer> boneMap;
	public List<Keyframe> keyframes;

	public void tick(float tickDelta) {
		Keyframe keyframe = keyframes.get(0);
		for (Keyframe.BoneState boneState : keyframe.states) {
			Bone bone = bones.get(boneState.bone);
			bone.x = boneState.posX;
			bone.y = boneState.posY;
			bone.z = boneState.posZ;
			bone.rotX = boneState.rotX;
			bone.rotY = boneState.rotY;
			bone.rotZ = boneState.rotZ;
		}
	}
}
