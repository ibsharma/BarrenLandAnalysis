import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

public class BarrenLand {
    private List<Integer[]> barrenRectangles = new ArrayList();
    private static final int xLimit = 400;
    private static final int yLimit = 600;
    private int[][] grid = new int[400][600];
    private Queue<Integer[]> queue = new LinkedList();
    private Map<Integer, Integer> areaMap = new HashMap();

    BarrenLand() {
        for(int i = 0; i < 400; ++i) {
            for(int j = 0; j < 600; ++j) {
                this.grid[i][j] = 0;
            }
        }

    }

    void barrenLandAnalysis(String input) {
        String[] inputString = input.split(",");
        String[] iterator = inputString;
        int rectangle = inputString.length;

        int i;
        for(i = 0; i < rectangle; ++i) {
            String j = iterator[i];
            j = j.replace("\"", "");
            j = j.replaceAll("“|”", "");
            j = j.replaceAll("\\{|\\}", "");
            j = j.replaceAll("^ ", "");
            String[] coordinates = j.split(" ");
            Integer[] rectangle1 = new Integer[]{Integer.valueOf(Integer.parseInt(coordinates[0])), Integer.valueOf(Integer.parseInt(coordinates[1])), Integer.valueOf(Integer.parseInt(coordinates[2])), Integer.valueOf(Integer.parseInt(coordinates[3]))};
            this.barrenRectangles.add(rectangle1);
        }

        ListIterator var9 = this.barrenRectangles.listIterator();

        while(var9.hasNext()) {
            Integer[] var10 = (Integer[])var9.next();

            for(i = var10[0].intValue(); i <= var10[2].intValue(); ++i) {
                for(int var11 = var10[1].intValue(); var11 <= var10[3].intValue(); ++var11) {
                    this.grid[i][var11] = 1;
                }
            }
        }

    }

    void populateUnvisitedPoints(int x, int y) {
        if(this.grid[x][y] == 0) {
            this.queue.offer(new Integer[]{Integer.valueOf(x), Integer.valueOf(y)});
        }

    }

    void calculateFertileArea() {
        int xCoordinate = 0;
        int yCoordinate = 0;
        int currentSection = 1;

        while(xCoordinate < 400 && yCoordinate < 600) {
            Integer[] currentPoint;
            if(this.queue.isEmpty()) {
                currentPoint = new Integer[]{Integer.valueOf(xCoordinate), Integer.valueOf(yCoordinate)};
                if(this.grid[xCoordinate][yCoordinate] == 0) {
                    ++currentSection;
                    this.queue.add(currentPoint);
                    this.areaMap.put(Integer.valueOf(currentSection), Integer.valueOf(0));
                }

                if(xCoordinate == 399) {
                    ++yCoordinate;
                    xCoordinate = 0;
                } else {
                    ++xCoordinate;
                }
            }

            if(!this.queue.isEmpty()) {
                currentPoint = (Integer[])this.queue.poll();
                int n1 = currentPoint[0].intValue();
                int n2 = currentPoint[1].intValue();
                if(this.grid[n1][n2] == 0) {
                    if(n1 > 0) {
                        this.populateUnvisitedPoints(n1 - 1, n2);
                    }

                    if(n2 > 0) {
                        this.populateUnvisitedPoints(n1, n2 - 1);
                    }

                    if(n1 < 399) {
                        this.populateUnvisitedPoints(n1 + 1, n2);
                    }

                    if(n2 < 599) {
                        this.populateUnvisitedPoints(n1, n2 + 1);
                    }

                    this.grid[n1][n2] = currentSection;
                    this.areaMap.put(Integer.valueOf(currentSection), Integer.valueOf(((Integer)this.areaMap.get(Integer.valueOf(currentSection))).intValue() + 1));
                }
            }
        }

    }

    void readfromSTDIN() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.barrenLandAnalysis(bufferedReader.readLine());
    }

    String printtoSTDOUT() {
        int[] resultantAreas = new int[this.areaMap.values().size()];
        int count = 0;

        for(Iterator var3 = this.areaMap.entrySet().iterator(); var3.hasNext(); ++count) {
            Entry entry = (Entry)var3.next();
            resultantAreas[count] = ((Integer)entry.getValue()).intValue();
        }

        Arrays.sort(resultantAreas);
        return Arrays.toString(resultantAreas).replaceAll("\\[|\\]|,", "");
    }
}