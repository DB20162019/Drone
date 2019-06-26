package drone_simulator_G2;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;

public class DroneBuilder implements ContextBuilder<Object> {

	@Override
	public Context build(Context<Object> context) {
		context.setId("drone_simulator_G2");
		
		//-------------------------------Creation and limitation of the Screen(Scene) space(Infinite coordinates system) -----------------------------------------------\\
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,
				new RandomCartesianAdder<Object>(), new repast.simphony.space.continuous.WrapAroundBorders(), 150, 100);
		
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Object> grid = gridFactory.createGrid("grid", context, new GridBuilderParameters<Object>(
				new WrapAroundBorders(), new SimpleGridAdder<Object>(), true, 10, 10));
		
		//-------------------------------Creation of the Agents setup on the Screen-----------------------------------------------\\
		Parameters params = RunEnvironment.getInstance().getParameters();
		int nombreDrone = (Integer) params.getValue("deliver_drone");
		int charge = (Integer) params.getValue("charge");

		/*Define the number of DeliverDrone */
		for(int i = 0; i < nombreDrone; i++){
			context.add(new DeliverDrone(space, grid,charge));
		} 
		// package
		// creation of the packages 
		int nombrepack = (Integer) params.getValue("package");
		/*Define the number of Package*/
		for (int i = 0; i < nombrepack ; i++) {
			context.add(new Package(space, grid));
		}
	
		// creation of the City/Street 
		context = setUpTheCity(context, grid, space);
		
		// creation of Surveillance drone : oversight drone
		int surveillanceDrone = 1;
		/*Define the number of SurveillanceDrone (OversightDrone) */
		for (int i = 0; i < surveillanceDrone; i++) {
			context.add(new SurveillanceDrone(space, grid,charge));
		} 
		//creation of intruder
		int intruder = (Integer) params.getValue("intruder");
		/*Define the number of intruder */
		for (int i = 0; i < intruder; i++) {
			context.add(new Intruder(space, grid));
		}  
		
		//-------------------------------- Positioning of the Agents on the grid system ( Finite system )---------------\\
		// counter to allow the package/drone to take the location of just one building at time
		//int nbBuildingToDeliver = 0; 
		for( Object obj : context )
		{
			NdPoint pt = space.getLocation ( obj );
			grid.moveTo(obj,(int)pt.getX(),(int)pt.getY());

		 }
		// the TaskDeliverController register all the building and package created on the scene, and randomly 
		// attach each package per building
		TaskController taskController = new TaskController(space, grid,context);
		// put the taskController on the scene
		context.add(taskController);
		// Create a list of building and package on the scene and share between the drones.
		taskController.registerTask();
		context = taskController.getContext();
		
		NegociatorController negociator = new NegociatorController(space, grid,context);
		negociator.registerDockStations();
		context = negociator.getContext();
		return context;
	}
	
	private Context<Object> setUpTheCity( Context<Object> context, Grid<Object> grid, ContinuousSpace<Object> space)
	{
		// creation of the Buildings, DockSations and Trees
				CoordinatedInitialisationUtils coordinatedInitialisationUtils = new CoordinatedInitialisationUtils();
				
				// 17 Buildings
				for (int i = 0; i < 17; i++) {
					Building b = new Building(space, grid);
					context.add(b);
					NdPoint coordinated = coordinatedInitialisationUtils.getBuildingCoordinatedAt(i);
					space.moveTo(b, coordinated.getX(), coordinated.getY());
				}
				
				// 27 Trees
				for (int i = 0; i < 27; i++) {
					Tree t = new Tree(space, grid);
					context.add(t);
					NdPoint coordinated = coordinatedInitialisationUtils.getTreeCoordinatedAt(i);
					space.moveTo(t, coordinated.getX(), coordinated.getY());
				}
				Parameters params = RunEnvironment.getInstance().getParameters();
				int dockstation = (Integer) params.getValue("dockstation");
				if(dockstation > 8) {
					dockstation = 8;
				}
				// 8 Dockstations
				for (int i = 0; i < dockstation; i++) {
					DockStation d = new DockStation(space, grid);
					context.add(d);
					NdPoint coordinated = coordinatedInitialisationUtils.getDockstationCoordinatedAt(i);
					space.moveTo(d, coordinated.getX(), coordinated.getY());
				}
				
				return context;
				
	}
}
