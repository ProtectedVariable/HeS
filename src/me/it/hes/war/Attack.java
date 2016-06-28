package me.it.hes.war;

public class Attack {

    private AttackType type;

    public boolean Execute() {
	return Math.random() * type.riskRewardRatio > 0.1f;
    }
}
