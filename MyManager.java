package student;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import game.*;

public class MyManager extends game.Manager{

	public void run(){
		HashSet<Parcel> parcelsCopy = new HashSet<Parcel>(getGame().getBoard().getParcels());
		Game game= getGame();
		Board board= game.getBoard();
		Node truckDepot= board.getTruckDepot();

		for(Truck truck: board.getTrucks()){
			if(truck.getLocation() == truckDepot || game.isFinished() == true){
				game.kill();
			}
		}
		for(Map.Entry<Node, Integer> n: truckDepot.getNeighbors().entrySet())
			Dijkstra(truckDepot,n.getKey());
		board.getParcels();
	}


	public void truckNotification(Truck t, Notification message){
		t.getManager().getNodes();
		if(t.getLocation().isParcelHere()){
			t.getLoad();
			t.getTravelingAlong().getFirstExit();
		}

	}

	private class NodeInfo{
		private Node prev = null;
		private Integer dist = null;
		public NodeInfo(int dist_new, Node current) {
			prev = current;
			dist = dist_new;
		};
	}
	public LinkedList<Node> Dijkstra(Node start, Node end){
		HashMap<Node,NodeInfo> info = new HashMap<Node,NodeInfo>();
		MyHeap<Node> frontier = new MyHeap<Node>();

		frontier.add(start,0);
		info.put(start, null);

		while (end != frontier.peek()){
			Node current = frontier.poll();

			for (Map.Entry<Node, Integer> n: current.getNeighbors().entrySet()){
				int dist_old;

				if(!info.containsKey(n.getKey())){
					dist_old = Integer.MAX_VALUE;
				}else{
					dist_old = info.get(n.getKey()).dist;
				}

				int dist_new = info.get(current).dist + n.getValue();

				if(dist_new < dist_old){
					info.put(n.getKey(), new NodeInfo(dist_new,current));
					try{
						frontier.add(n.getKey(),dist_new);
					}catch(IllegalArgumentException e){
						frontier.updatePriority(n.getKey(), dist_new);
					}
				}
			}//end-for
		}//end-while
		LinkedList<Node> path = new LinkedList<Node>();
		Node current = end;
		while(current != null){
			path.push(current);
			current = info.get(current).prev;
		}
		return path;
	}
}
