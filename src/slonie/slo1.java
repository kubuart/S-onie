package slonie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class slo1 {

    private static final int INF = 100000000;
    private static final int BIG_N = 10000000;
    private static final int[] WAGES = new int[BIG_N];
    private static final int[] START_POSITIONS = new int[BIG_N];
    private static final int[] END_POSITIONS =new int[BIG_N];
    private static final int[] PERMUTATIONS =new int[BIG_N];
    private  static int MINIMUM_WAGE = INF;

    public static void main(String[] args)
    {
        int N = 0;
        try {
            BufferedReader rd = new BufferedReader(new FileReader(args[0]));
            StringTokenizer line1 = new StringTokenizer(rd.readLine());
            StringTokenizer line2 = new StringTokenizer(rd.readLine());
            StringTokenizer line3 = new StringTokenizer(rd.readLine());
            StringTokenizer line4 = new StringTokenizer(rd.readLine());
            N = Integer.parseInt(line1.nextToken());

            for (int i = 0; i < N; i++) {
                WAGES[i] = Integer.parseInt(line2.nextToken());
                START_POSITIONS[i] = Integer.parseInt(line3.nextToken()) - 1;
                MINIMUM_WAGE = Math.min(MINIMUM_WAGE,WAGES[i]);
                int val = Integer.parseInt(line4.nextToken());
                END_POSITIONS[i] = val - 1;
                PERMUTATIONS[val - 1] = START_POSITIONS[i];
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        List<List<Integer>> cycles= getCycles(START_POSITIONS,END_POSITIONS,PERMUTATIONS,N);
        System.out.println(getScore(cycles));
    }

    public static List<List<Integer>> getCycles(int[] start, int[] end, int[] permutations,int n){
        List<List<Integer>> cycles= new ArrayList<>();
        List<Integer> inCycles= new ArrayList<>();
        for(int i =0 ;i<n;i++) {
            if (!inCycles.contains(start[i])) {
                if (start[i] == end[i]) {
                    inCycles.add(i);
                }
                else {
                    List<Integer> cycle = new ArrayList<>();
                    int node = start[i];
                    do{
                        node = permutations[node];
                        cycle.add(node);
                        inCycles.add(node);
                    }
                    while (!(node == start[i]));
                    cycles.add(cycle);
                }
            }
        }
        return  cycles;
    }

    public static int getScore(List<List<Integer>> cycles){
        int score = 0;
        for (List<Integer> cycle : cycles){
            Integer firstNode=cycle.get(0);
            int minimumCycleWage = INF;
            long sumOfWages = 0;
            int nestNode = firstNode;
            int cycleLength = cycle.size();
            do{
                minimumCycleWage = Math.min(minimumCycleWage, WAGES[nestNode]);
                sumOfWages += WAGES[nestNode];
                nestNode = PERMUTATIONS[nestNode];
            }
            while(nestNode!=firstNode);
            score += Math.min(sumOfWages+(cycleLength-2)*(long)minimumCycleWage, sumOfWages+minimumCycleWage+(cycleLength+1)*(long)MINIMUM_WAGE);
        }
        return  score;
    }
}
