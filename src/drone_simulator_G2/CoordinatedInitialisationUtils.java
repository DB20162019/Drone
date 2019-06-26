package drone_simulator_G2;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import repast.simphony.space.continuous.NdPoint;

/**
 * 
 *
 */
public class CoordinatedInitialisationUtils {
	
	//create point lists for the buildings
	private List<NdPoint> buildingPoints;
	
	//create point lists for the trees
	private List<NdPoint> treePoints;
	
	//create point lists for the dockstation
	private List<NdPoint> dockstationPoints;
	
	/**
	 * initialize the point lists
	 */
	public CoordinatedInitialisationUtils() {
		intializeBuildingPoints();
		intializeTreePoints();
		intializeDockstationPoints();
	}
	
	// points for trees
	private void intializeTreePoints() {
		treePoints = new ArrayList<>();
		treePoints.add(new NdPoint(49,12));
		treePoints.add(new NdPoint(42,10));
		treePoints.add(new NdPoint(45,18));
		treePoints.add(new NdPoint(50,88));
		treePoints.add(new NdPoint(55,83));
		treePoints.add(new NdPoint(10,60));
		treePoints.add(new NdPoint(13,50));
		treePoints.add(new NdPoint(17,56));
		treePoints.add(new NdPoint(71,46));
		treePoints.add(new NdPoint(71,54));
		treePoints.add(new NdPoint(79,46));
		treePoints.add(new NdPoint(79,54));
		treePoints.add(new NdPoint(125,33));
		treePoints.add(new NdPoint(118,34));
		treePoints.add(new NdPoint(111,33));
		treePoints.add(new NdPoint(104,34));
		treePoints.add(new NdPoint(127,18));
		treePoints.add(new NdPoint(122,18));
		treePoints.add(new NdPoint(115,18));
		treePoints.add(new NdPoint(108,19));
		treePoints.add(new NdPoint(101,18));
		treePoints.add(new NdPoint(134,85));
		treePoints.add(new NdPoint(138,85));
		treePoints.add(new NdPoint(142,87));
		treePoints.add(new NdPoint(110,90));
		treePoints.add(new NdPoint(115,87));
		treePoints.add(new NdPoint(120,92));	
		
	}
	
	// points for buildings
	private void intializeBuildingPoints() {
		buildingPoints = new ArrayList<>();
		buildingPoints.add(new NdPoint(25,12));
		buildingPoints.add(new NdPoint(32,25));
		buildingPoints.add(new NdPoint(45,35));
		buildingPoints.add(new NdPoint(15,30));
		buildingPoints.add(new NdPoint(15,75));
		buildingPoints.add(new NdPoint(30,68));
		buildingPoints.add(new NdPoint(45,77));
		buildingPoints.add(new NdPoint(37,88));
		buildingPoints.add(new NdPoint(22,88));
		buildingPoints.add(new NdPoint(135,15));
		buildingPoints.add(new NdPoint(135,25));
		buildingPoints.add(new NdPoint(135,35));
		buildingPoints.add(new NdPoint(139,80));
		buildingPoints.add(new NdPoint(120,75));
		buildingPoints.add(new NdPoint(105,85));
		buildingPoints.add(new NdPoint(105,70));
		buildingPoints.add(new NdPoint(136,95));
		
	}
	//points for dockstation
	private void intializeDockstationPoints() {
		dockstationPoints = new ArrayList<>();
		dockstationPoints.add(new NdPoint(25,12));
		dockstationPoints.add(new NdPoint(45,35));
		dockstationPoints.add(new NdPoint(45,77));
		dockstationPoints.add(new NdPoint(135,35));
		dockstationPoints.add(new NdPoint(120,75));
		dockstationPoints.add(new NdPoint(15,75));
		dockstationPoints.add(new NdPoint(136,95));
		dockstationPoints.add(new NdPoint(37,88));
		
	}
	
	// get the point of building
	public NdPoint getBuildingCoordinatedAt(int i) {
		if(i>=0 && i<buildingPoints.size()) {
			return buildingPoints.get(i);
		} else {
			return null;
		}
	}
	
	// get the point of tree
	public NdPoint getTreeCoordinatedAt(int i) {
		if(i>=0 && i<treePoints.size()) {
			return treePoints.get(i);
		} else {
			return null;
		}
	}
	
	
	// get the point of dockstation
	public NdPoint getDockstationCoordinatedAt(int i) {
		if(i>=0 && i<dockstationPoints.size()) {
			return dockstationPoints.get(i);
		} else {
			return null;
		}
	}
	
}
