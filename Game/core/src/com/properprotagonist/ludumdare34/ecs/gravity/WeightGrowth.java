package com.properprotagonist.ludumdare34.ecs.gravity;

import com.properprotagonist.ludumdare34.ecs.Component;

public class WeightGrowth implements Component {
	private GrowthDetector detector;
	private float passiveGrowth;
	private float activeGrowth;
	public float maxWeight;
	public float minWeight;
	public WeightGrowth(GrowthDetector detector) {
		this.detector = detector;
		activeGrowth = 1;
		passiveGrowth = -0.25f;
		maxWeight = 5;
		minWeight = 1;
	}
	public float getCurrentGrowth() {
		float growthFactor = detector.getCurrentGrowthFactor();
		return passiveGrowth + (activeGrowth - passiveGrowth) * growthFactor;
	}
	public float clampWeight(float weight) {
		if (weight > maxWeight)
			weight = maxWeight;
		if (weight < minWeight)
			weight = minWeight;
		return weight;
	}
}
