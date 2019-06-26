package drone_simulator_G2;

import java.util.ArrayList;

import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.SimUtilities;

public class NegociatorController {
	
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	ArrayList<DockStation> lisOfDockStation;
	
	
	private Context<Object> context;
	
	NegociatorController(ContinuousSpace<Object> space, Grid<Object> grid,Context<Object> context)
	{
		this.space = space;
		this.grid = grid;		
		this.context = context;
		lisOfDockStation = new ArrayList<DockStation>();
	}
	
	public void registerDockStations()
	{
		for(Object obj : this.context)
		{
			if(obj instanceof DockStation )
			{
				lisOfDockStation.add((DockStation)obj);
			}
			
			
		}
		
		// Randomly ordered of building and package, to no be all the same task at the same building
		//SimUtilities.shuffle(lisOfPackage, RandomHelper.getUniform());
		
		// Package Recuperate  the location of buildings
		
		// Give each DeliverDrone on the scene the list of Package on the screen,			
		for(Object obj : context)
		{
			if(obj instanceof Drone )
			{
				((Drone) obj).setDocks(lisOfDockStation);
			}
		}

	}
	
	public Context<Object> getContext() {
		return context;
	}

	public void setContext(Context<Object> context) {
		this.context = context;
	}
}
