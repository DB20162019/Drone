
package drone_simulator_G2;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;


/*
 * the DeliverTask object need a package to function correctly
 * this why there is a field of type Package ( the product to be deliver) 
 * 
 */
public class DeliverTask extends Task {
	private int id;
	private Package mPackage;
	private static int countID = 0;
	public DeliverTask(ContinuousSpace<Object> space, Grid<Object> grid) {
		super(space, grid);
		
		countID++;
		// set a unique ID for each task
		this.setId(countID);
	}
	
	public Package getmPackage() {
		return mPackage;
	}

	public void setmPackage(Package mPackage) {
		this.mPackage = mPackage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
}
