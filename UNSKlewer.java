package MapGraph;

import java.util.*;

class Graph {
    private int totalVertex;
    private ArrayList<Integer>[] adjList;
    private int[][] adjMatrix;
    private ArrayList<Path> path;

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    public Graph(int vertices){
        this.totalVertex = vertices;
        init();
    }

    private void init(){
        adjList = new ArrayList[totalVertex];
        for(int i = 0; i < totalVertex; i++) {
            adjList[i] = new ArrayList<>();
        }
        adjMatrix = new int[totalVertex][totalVertex];
        path = new ArrayList<>();
    }

    public void addEdge(int u, int v, int cost){
        adjList[u].add(v);
        adjMatrix[u][v] = cost;
    }

    public void printShortestPath(){
        if (path.size() != 0) {
            path.sort(Comparator.comparingInt(Path::getCost));
            printPath(path.get(0).getPath());
            System.out.println("total cost = " + path.get(0).getCost());
        } else {
            System.out.println("Cetak DFS");
        }
    }

    public void printAllPathsDFS(int s, int d){
        boolean[] isVisited = new boolean[totalVertex];
        ArrayList<Integer> pathList = new ArrayList<>();

        pathList.add(s);
        path.clear();
        printAllPathsUtil(s, d, isVisited, pathList, 0, path);
    }

    private void printAllPathsUtil(Integer u, Integer d, boolean[] isVisited,
                                   ArrayList<Integer> localPathList, int cost, ArrayList<Path> path){
        isVisited[u] = true;

        if (u.equals(d)){
            printPath(localPathList);
            path.add(new Path((ArrayList<Integer>) localPathList.clone(), cost));

            isVisited[u]= false;
            return;
        }
        
        for (Integer i : adjList[u]){
            cost += adjMatrix[u][i];
            if (!isVisited[i]){
                localPathList.add(i);
                printAllPathsUtil(i, d, isVisited, localPathList, cost, path);
                cost -= adjMatrix[u][i];
                localPathList.remove(i);
            }
        }
        isVisited[u] = false;
    }

    public void printAllPathsBFS(int s, int d){
        Queue<ArrayList<Integer>> qPaths = new ArrayDeque<>();
        ArrayList<Integer> path = new ArrayList<>();
        path.add(s);
        qPaths.add(path);
        while (!qPaths.isEmpty()){
            path = qPaths.peek();
            qPaths.remove();
            int last = path.get(path.size()-1);
            if (last == d){
                printPath(path);
            }

            for (int i = 0; i < adjList[last].size();i++){
                if (isNotVisited(adjList[last].get(i),path)){
                    ArrayList<Integer> newPath = (ArrayList<Integer>) path.clone();
                    newPath.add(adjList[last].get(i));
                    qPaths.add(newPath);
                }
            }
        }
    }

    private boolean isNotVisited(int x, ArrayList<Integer> path){
        for (Integer a : path) {
            if (a == x)
                return false;
        }
        return true;
    }

    private void printPath(ArrayList<Integer> path){
        String[] node = {
                "Jl Utama UNS-Ki Hajar",
                "Jl Ir Sutami-Kol Sutarto",
                "Jl Kol Sutarto-Urip Sumoharjo",
                "Jl Surya-Urip Sumoharjo",
                "Jl Urip Sumoharjo-Jensud",
                "Jl Mayor Kusmanto-Jensud",
                "Jl Pakubuwono-Alunlor",
                "",
                "",
                "Jl Alunlor-Dok Rajiman",
                "Jl Utama UNS",
                "Jl Utama UNS-Ir Sutami",
                "Jl Ir Sutami-Cokroaminoto",
                "Jl Cokroaminoto-Surya",
                "Jl Surya-Gotong Royong",
                "Jl Gotong Royong-RE Martadinata",
                "Jl RE Martadinata-Mulyadi",
                "Jl Mulyadi-Mayor Kusmanto"
                };
        StringBuilder rute = new StringBuilder();
        rute.append("Universitas Sebelas Maret -> ");
        for (Integer i : path){
            if (!node[i].equals(""))
                rute.append(node[i]).append(" -> ");
        }
        rute.append("Pasar Klewer");
        System.out.println(rute);
    }
}

class Path{
    private ArrayList<Integer> path;
    private int cost;

    public Path(ArrayList<Integer> path, int cost) {
        this.path = path;
        this.cost = cost;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public int getCost() {
        return cost;
    }
	
}

public class UNSKlewer {
		
	public static void main(String[] args) {
		Graph graph = new Graph(18);
		int u = 0, v = 0, cost = 0;
		
		System.out.println("Masukan hubungan dan cost antar node =");
		System.out.println("dengan format (nodeStart, nodeDest, costValue)");
		System.out.println("jika masukan sudah cukup, isi dengan '-1'");
		
		while (u != -1) {
			Scanner in = new Scanner(System.in);
			int num = in.nextInt(); u = num;
			if (u != -1) {
				num = in.nextInt(); v = num;
				num = in.nextInt(); cost = num;
				graph.addEdge(u, v, cost);
			}
		}
		
	    int s = 0; //startPoint = node[0] = Jl Utama UNS-Ki Hajar
	    int d = 9; //destPoint = node[9] = Jl Alunlor-Dok Rajiman
		
	    System.out.println("Hasil Rute melalui Breadth First Search = ");
	    graph.printAllPathsBFS(s, d);
	    System.out.println();
		
	    System.out.println("Hasil Rute melalui Depth First Search = ");
	    graph.printAllPathsDFS(s, d);
	    System.out.println();
		
	    System.out.println("Hasil Shortest Path = ");
	    graph.printShortestPath();
	}
		
}

