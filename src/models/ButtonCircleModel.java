package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButtonCircleModel {
	//lista etykiet wierzcholkow, generowana losowo, opcja recznego podania
	private List<Integer> nodeLabels;
	//lista utworzonych wierzcholkow-przyciskow, posiadaja wspolrzedne
	private List<ButtonModel> nodes;
	//kolejny przycisk do zaznaczenia
	private int nextButtonId;
	private long startTime;
	
	public ButtonCircleModel() {
		nodeLabels = new ArrayList<Integer>();
		nodes = new ArrayList<ButtonModel>();
		nextButtonId = 0;
	}
	
	public void generateNodes(int size) {
		nodeLabels = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			nodeLabels.add(i);
		} 
	}
	
	public void shuffleNodes() {
		Collections.shuffle(nodeLabels);
	}
	
	public void generateRandomNodes(int size) {
		nodeLabels = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			nodeLabels.add(i);
		}
		shuffleNodes();
	}

	public List<Integer> getNodeLabels() {
		return nodeLabels;
	}

	public void setNodeLabels(List<Integer> nodeLabels) {
		this.nodeLabels = nodeLabels;
	}
	
	public List<ButtonModel> getNodes() {
		return nodes;
	}

	public void setNodes(List<ButtonModel> nodes) {
		this.nodes = nodes;
	}

	public int getNextButtonId() {
		return nextButtonId;
	}

	public void setNextButtonId() {
		this.nextButtonId += 1;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long l) {
		this.startTime = l;
	}
}
