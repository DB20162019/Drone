package drone_simulator_G2;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;



/*
 * 
 * This class represent the package or the product to be deliver in one exact point, which
 * can be a building
 */
public class Package {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private GridPoint destinationCoord; // destination to deliver the package
	private boolean isTaken;
	public Package(ContinuousSpace<Object> space, Grid<Object> grid) {
		this.space = space;
		this.grid = grid;
		this.isTaken = false;
	}
	// this function is used to help the package to be deliver following   the drone
	// and it receives the drone charged to carry the package, so that can it get the drone's position and follow him.
	public void move(DeliverDrone drone)
	{
		GridPoint positonDrone = grid.getLocation(drone);
		if(!positonDrone.equals(grid.getLocation(this)))
		{
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint( positonDrone.getX(), positonDrone.getY());
			double angle = SpatialMath.calcAngleFor2DMovement (space,myPoint , otherPoint );
			space.moveByVector(this, 1, angle,0);
			myPoint = space.getLocation(this);
			grid.moveTo ( this ,( int )myPoint.getX (), ( int )myPoint.getY ());	
		}
		
	}
	// getters and setters of the private fields
	public boolean isTaken() {
		return isTaken;
	}
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}
	public ContinuousSpace<Object> getSpace() {
		return space;
	}

	public void setSpace(ContinuousSpace<Object> space) {
		this.space = space;
	}

	public Grid<Object> getGrid() {
		return grid;
	}

	public void setGrid(Grid<Object> grid) {
		this.grid = grid;
	}
	public GridPoint getDestinationCoord() {
		return destinationCoord;
	}

	public void setDestinationCoord(GridPoint destinationCoord) {
		this.destinationCoord = destinationCoord;
	}
	
	
}
