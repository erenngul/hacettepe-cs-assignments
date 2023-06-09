import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.util.List;

public class IMECEPathFinder{
	  public int[][] grid;
	  public int[][] grayscaleGrid;
	  public Point[][] points;
	  public int height, width;
	  public int maxFlyingHeight;
	  public int minElevation, maxElevation;
	  public double fuelCostPerUnit, climbingCostPerUnit;

	  public IMECEPathFinder(String filename, int rows, int cols, int maxFlyingHeight, double fuelCostPerUnit, double climbingCostPerUnit){

		  grid = new int[rows][cols];
		  grayscaleGrid = new int[rows][cols];
		  points = new Point[rows][cols];
		  this.height = rows;
		  this.width = cols;
		  this.maxFlyingHeight = maxFlyingHeight;
		  this.minElevation = -1;
		  this.maxElevation = -1;
		  this.fuelCostPerUnit = fuelCostPerUnit;
		  this.climbingCostPerUnit = climbingCostPerUnit;

		  Scanner sc;
		  try {
			  sc = new Scanner(new File(filename));
			  for (int i = 0; i < height; i++) {
				  for (int j = 0; j < width; j++) {
					  if (sc.hasNextInt()) {
						  grid[i][j] = sc.nextInt();
						  if (minElevation == -1 && maxElevation == -1) {
							  minElevation = grid[i][j];
							  maxElevation = grid[i][j];
						  }
						  else if (grid[i][j] < minElevation)
							  minElevation = grid[i][j];
						  else if (grid[i][j] > maxElevation)
							  maxElevation = grid[i][j];
					  }
				  }
			  }
		  } catch (FileNotFoundException e) {
			  e.printStackTrace();
		  }

		  for (int i = 0; i < height; i++) {
			  for (int j = 0; j < width; j++) {
				  grayscaleGrid[i][j] = (int) (( (double) (grid[i][j] - minElevation) / (maxElevation - minElevation)) * 255);
			  }
		  }

		  File outputFile = new File("grayscaleMap.dat");
		  try {
			  FileWriter grayscaleMapFile = new FileWriter(outputFile);
			  for (int i = 0; i < height; i++) {
				  for (int j = 0; j < width; j++) {
						grayscaleMapFile.write(grayscaleGrid[i][j] + " ");
				  }
				  grayscaleMapFile.write("\n");
			  }
			  grayscaleMapFile.close();
		  } catch (IOException e) {
			  throw new RuntimeException(e);
		  }

		  for (int i = 0; i < height; i++) {
			  for (int j = 0; j < width; j++) {
				  points[i][j] = new Point(j, i);
			  }
		  }

		  for (int i = 0; i < height; i++) {
			  for (int j = 0; j < width; j++) {
				  Point currentPoint = points[i][j];

				  for (int dx = -1; dx <= 1; ++dx) {
					  for (int dy = -1; dy <= 1; ++dy) {
						  if (dx != 0 || dy != 0) {
							  try {

								  double point1Height = grid[currentPoint.getY()][currentPoint.getX()];

								  Point point2 = points[i + dy][j + dx];
								  double point2Height = grid[point2.getY()][point2.getX()];

								  if (point2Height > maxFlyingHeight)
									  continue;

								  double distance = Math.sqrt(Math.pow(currentPoint.getX() - point2.getX(), 2) + Math.pow(currentPoint.getY() - point2.getY(), 2));
								  double heightImpact = point1Height >= point2Height ? 0 : point2Height - point1Height;
								  double cost = (distance * fuelCostPerUnit) + (climbingCostPerUnit * heightImpact);

								  DirectedEdge edge = new DirectedEdge(currentPoint, points[i + dy][j + dx], cost);
								  currentPoint.adjacentEdges.add(edge);

							  } catch (Exception ignored) {}
						  }
					  }
				  }

			  }
		  }

	  }

	  /**
	   * Draws the grid using the given Graphics object.
	   * Colors should be grayscale values 0-255, scaled based on min/max elevation values in the grid
	   */
	  public void drawGrayscaleMap(Graphics g){
		  for (int i = 0; i < grayscaleGrid.length; i++)
		  {
			  for (int j = 0; j < grayscaleGrid[0].length; j++) {
				  int value = grayscaleGrid[i][j];
				  g.setColor(new Color(value, value, value));
				  g.fillRect(j, i, 1, 1);
			  }
		  }
	  }

	/**
	 * Get the most cost-efficient path from the source Point start to the destination Point end
	 * using Dijkstra's algorithm on pixels.
	 * @return the List of Points on the most cost-efficient path from start to end
	 */
	public List<Point> getMostEfficientPath(Point start, Point end) {

		List<Point> path = new ArrayList<>();

		DirectedEdge[][] edgeTo = new DirectedEdge[height][width];
		PriorityQueue<Point> priorityQueue = new PriorityQueue<>();

		start = points[start.getY()][start.getX()];
		end = points[end.getY()][end.getX()];

		start.distanceTo = 0;
		priorityQueue.add(start);

		while (!priorityQueue.isEmpty()) {
			Point poppedPoint = priorityQueue.poll();
			for (DirectedEdge edge : poppedPoint.adjacentEdges) {
				Point v = edge.from;
				Point w = edge.to;
				if (w.distanceTo > v.distanceTo + edge.cost) {
					w.distanceTo = v.distanceTo + edge.cost;
					edgeTo[w.getY()][w.getX()] = edge;
					if (priorityQueue.contains(w))
						priorityQueue.remove(w);
					priorityQueue.add(w);
				}
			}
		}

		for (DirectedEdge edge = edgeTo[end.getY()][end.getX()]; edge != null; edge = edgeTo[edge.from.getY()][edge.from.getX()]) {
			path.add(edge.to);
			edge.to.cost = edge.cost;
		}

		path.add(start);

		return path;
	}

	/**
	 * Calculate the most cost-efficient path from source to destination.
	 * @return the total cost of this most cost-efficient path when traveling from source to destination
	 */
	public double getMostEfficientPathCost(List<Point> path){
		double totalCost = 0.0;

		for (Point point : path)
			totalCost += point.cost;

		return totalCost;
	}


	/**
	 * Draw the most cost-efficient path on top of the grayscale map from source to destination.
	 */
	public void drawMostEfficientPath(Graphics g, List<Point> path){
		g.setColor(new Color(0, 255, 0));
		for (Point point : path) {
			g.fillRect(point.getX(), point.getY(), 1, 1);
		}
	}

	/**
	 * Find an escape path from source towards East such that it has the lowest elevation change.
	 * Choose a forward step out of 3 possible forward locations, using greedy method described in the assignment instructions.
	 * @return the list of Points on the path
	 */
	public List<Point> getLowestElevationEscapePath(Point start){
		List<Point> pathPointsList = new ArrayList<>();

		int upperDifference = Integer.MAX_VALUE;
		int middleDifference = Integer.MAX_VALUE;
		int lowerDifference = Integer.MAX_VALUE;

		pathPointsList.add(start);

		while (start.getX() != width - 1) {
			if (start.getY() - 1 >= 0)
				upperDifference = Math.abs(grid[start.getY()][start.getX()] - grid[start.getY() - 1][start.getX() + 1]);
			if (start.getY() + 1 <= height - 1)
				lowerDifference = Math.abs(grid[start.getY()][start.getX()] - grid[start.getY() + 1][start.getX() + 1]);
			middleDifference = Math.abs(grid[start.getY()][start.getX()] - grid[start.getY()][start.getX() + 1]);

			int minDifference = Math.min(Math.min(upperDifference, lowerDifference), middleDifference);

			if (minDifference == middleDifference)
				start = points[start.getY()][start.getX() + 1];
			else if (minDifference == upperDifference)
				start = points[start.getY() - 1][start.getX() + 1];
			else
				start = points[start.getY() + 1][start.getX() + 1];

			pathPointsList.add(start);
		}

		return pathPointsList;
	}


	/**
	 * Calculate the escape path from source towards East such that it has the lowest elevation change.
	 * @return the total change in elevation for the entire path
	 */
	public int getLowestElevationEscapePathCost(List<Point> pathPointsList){
		int totalChange = 0;

		for (int i = 0; i < pathPointsList.size() - 1; i++) {
			Point point1 = pathPointsList.get(i);
			Point point2 = pathPointsList.get(i + 1);
			totalChange += Math.abs(grid[point1.getY()][point1.getX()] - grid[point2.getY()][point2.getX()]);
		}

		return totalChange;
	}


	/**
	 * Draw the escape path from source towards East on top of the grayscale map such that it has the lowest elevation change.
	 */
	public void drawLowestElevationEscapePath(Graphics g, List<Point> pathPointsList){
		g.setColor(new Color(255, 255, 0));
		for (Point point : pathPointsList) {
			g.fillRect(point.getX(), point.getY(), 1, 1);
		}
	}


}
