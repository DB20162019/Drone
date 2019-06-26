package drone_simulator_G2;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

/* 
 * This class serve as a controller of buiding and package on the city
   He register all the building and package created on the scene, and randomly 
 	attach each package per building
 */

public class TaskController 
{
	protected ContinuousSpace<Object> space;
	protected Grid<Object> grid;
	ArrayList<Package> lisOfPackage;
	ArrayList<Building> lisOfBuilding;
	ArrayList<Intruder> lisOfIntruder;
	
	private Context<Object> context;
	
	public TaskController(ContinuousSpace<Object> space, Grid<Object> grid,Context<Object> context) {
		this.space = space;
		this.grid = grid;		
		this.context = context;
		lisOfPackage = new ArrayList<Package>();
		lisOfBuilding = new ArrayList<Building>();
		
	}

	public Context<Object> getContext() {
		return context;
	}

	public void setContext(Context<Object> context) {
		this.context = context;
	}
	
	public void registerTask()
	{
		// Create lists 
		for(Object obj : this.context)
		{
			// Create lists of package on the scene
			if(obj instanceof Package )
			{
				lisOfPackage.add((Package)obj);
			}
			// Create lists of Buildings on the scene
			if(obj instanceof Building )
			{
				lisOfBuilding.add((Building)obj);
			}

		}
		
		// Randomly ordered of building and package, to no be all the same task at the same building
		SimUtilities.shuffle(lisOfPackage, RandomHelper.getUniform());
		SimUtilities.shuffle(lisOfBuilding, RandomHelper.getUniform());

		
		
		// Package Recuperate  the location of buildings
		
		int countBuinding = 0;
		for(int i=0 ; i< lisOfPackage.size(); i++)
		{	
			// if whe have give each package to one building, and still have package without location to deliver
			// then we reinitiate the distributition of the left's package, this mean there will be a building with one or more 
			// package to be delivered
			if( countBuinding == lisOfBuilding.size())
			{
				countBuinding = 0;
			}
			
			// Get the location of a building
			GridPoint buildingLocation = grid.getLocation(lisOfBuilding.get(countBuinding));
			// Give the the destination of the package, using the building's location
			lisOfPackage.get(i).setDestinationCoord(buildingLocation);
			countBuinding++;
		}
		// Give to All DeliverDrone on the scene the list of Package available,			
		for(Object obj : context)
		{
			if(obj instanceof DeliverDrone )
			{
				((DeliverDrone) obj).setTasks(lisOfPackage);
			}
		}


	}
	
	// This section is the getters and setters of the private field of the class
	public ArrayList<Package> getLisOfPackage() {
		return lisOfPackage;
	}

	public void setLisOfPackage(ArrayList<Package> lisOfPackage) {
		this.lisOfPackage = lisOfPackage;
	}

	public ArrayList<Building> getLisOfBuilding() {
		return lisOfBuilding;
	}

	public void setLisOfBuilding(ArrayList<Building> lisOfBuilding) {
		this.lisOfBuilding = lisOfBuilding;
	}

	

	
}
