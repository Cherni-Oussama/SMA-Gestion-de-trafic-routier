import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoadPoint {

	class Point {
		double X;
		double Y;

		public Point( double x, double y) {
			this.X = x - 300;
			this.Y = y - 300;
		}

		public boolean equal(Point point) {
			if (point.X == this.X && point.Y == this.Y) {
				return true;
			} else {
				return false;
			}
		}
	}

	public class SignalLoc {
		Point startLoc;
		Point endLoc;
		double group = 0.0;

		public SignalLoc(Point startLoc, Point endLoc, double group) {
			this.startLoc = startLoc;
			this.endLoc = endLoc;
			this.group = group;
		}
	}
	
	public class TrafficCenter {
		Point center;
		double group = 0.0;
		
		public TrafficCenter(Point center, double group) {
			this.center = center;
			this.group = group;
		}
	}
	
	private List<List<Point>> locInfoList;

	private List<Point> startLocInfoList = new ArrayList<Point>();

	public List<SignalLoc> getSignalLocList() {
		return SignalLocList;
	}

	public void setSignalLocList(List<SignalLoc> signalLocList) {
		SignalLocList = signalLocList;
	}

	private List<SignalLoc> SignalLocList = new ArrayList<SignalLoc>();
	
	public List<TrafficCenter> TrafficCenterList = new ArrayList<TrafficCenter>();
	
	public RoadPoint() {

		TrafficCenterList = Arrays.asList(
				new TrafficCenter(new Point(282, 451), 1),
				new TrafficCenter(new Point(282, 345), 2),
				new TrafficCenter(new Point(282, 181), 3)
				);
		
		//localisation of TrafficRoad
		SignalLocList = Arrays.asList(new SignalLoc(new Point(305, 600),new Point(305, 518), 1),
				new SignalLoc(new Point(219, 475),new Point(219, 475), 1),
				new SignalLoc(new Point(260, 220),new Point(260, 388), 1),
				
				new SignalLoc(new Point(260, 0),new Point(260, 116), 3),
				new SignalLoc(new Point(600, 158),new Point(349, 158), 3),
				new SignalLoc(new Point(305, 400),new Point(305, 246), 3),
				
				new SignalLoc(new Point(260, 203),new Point(260, 290), 2),
				new SignalLoc(new Point(600, 319),new Point(349, 319), 2),
				new SignalLoc(new Point(305, 416),new Point(305, 380), 2)
				
				);
		
		
		startLocInfoList = Arrays.asList(new Point(260, 0), new Point(0, 475), new Point(305, 600), new Point(600, 158), new Point(600,319));
		
		locInfoList = Arrays.asList(
				Arrays.asList(new Point(305, 600), new Point(305, 475)),
				Arrays.asList(new Point(305, 475), new Point(313, 451)),
				Arrays.asList(new Point(313, 451),new Point(305, 430)),
				Arrays.asList(new Point(305, 430), new Point(305,203), new Point(282,418)),
				Arrays.asList(new Point(305,203), new Point(600, 203),new Point(313, 181)),
				Arrays.asList(new Point(600, 203)),
				Arrays.asList(new Point(313, 181), new Point(305, 158)),
				Arrays.asList(new Point(305, 158), new Point(305,0), new Point(282, 148)),
				Arrays.asList(new Point(305,0)),
				Arrays.asList(new Point(282, 148),new Point(260,158)),

				Arrays.asList(new Point(260,158), new Point(249, 181)),
				Arrays.asList(new Point(249, 181), new Point(260, 203)),
				Arrays.asList(new Point(260, 203), new Point(282, 214), new Point(260, 430)),
				Arrays.asList(new Point(282, 214), new Point(305, 203)),
				Arrays.asList(new Point(260, 430),new Point(0, 430),new Point(248, 451)),
				Arrays.asList(new Point(0, 430)),
				Arrays.asList(new Point(248, 451), new Point(260, 475)),
				Arrays.asList(new Point(260, 475),new Point(260, 600), new Point(282, 487)),
				Arrays.asList(new Point(260, 600)),
				Arrays.asList(new Point(282, 418), new Point(260, 430)),
				
				Arrays.asList(new Point(282, 487), new Point(305, 475)),
				Arrays.asList(new Point(0, 475), new Point(260, 475)),
				Arrays.asList(new Point(600, 158), new Point(305, 158)),
				Arrays.asList(new Point(260, 0), new Point(260, 158)),
				
				Arrays.asList(new Point(600, 319), new Point(305, 319)),
				Arrays.asList(new Point(305, 319), new Point(282, 310), new Point(305,0))
				
								
	
		);

	}

	public Point getNextPointInfo(Point loctInfo) {

		for (int i = 0; i < locInfoList.size(); i++) {
			if (locInfoList.get(i).get(0).equal(loctInfo)) {
				int size = locInfoList.get(i).size();
				if (size > 1) {
					int rondomChoice = 1 + (int) (Math.random() * (((size - 1) - 1) + 1));
					return locInfoList.get(i).get(rondomChoice);
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public Point getStartPointInfo() {
		int size = startLocInfoList.size();
		if (size > 0) {
			int rondomChoice = 0 + (int) (Math.random() * (((size - 1) - 0) + 1));
			return startLocInfoList.get(rondomChoice);
		} else {
			return null;
		}
	}
}
