package xyz.hes.war;

import java.util.LinkedList;
import java.util.List;

public class AttackQueue {
	
	private List<Attack> queue;
	private final int NEXT = 100;
	private int timeToNext = NEXT;
	
	public AttackQueue() {
		queue = new LinkedList<Attack>();
	}
	
	public void Insert(Attack attk) {
		queue.add(attk);
	}
	
	private Attack Extract() {
		Attack a = queue.get(queue.size()-1);
		queue.remove(a);
		return a;
	}
	
	public void Update() {
		timeToNext--;
		if(timeToNext <= 0) {
			if(Extract().Execute()) {
				
			}
		}
	}
	
}
