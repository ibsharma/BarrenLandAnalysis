import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;

public class BarrenLand {
    private List<Integer[]> barrenRectangles = new ArrayList<>();
    private static final int xLimit = 400;
    private static final int yLimit = 600;
    private int[][] grid = new int[400][600];
    private Queue<Integer[]> queue = new LinkedList<>();
    private Map<Integer, Integer> areaMap = new HashMap<>();

    BarrenLand() {
        for (int i = 0; i < xLimit; ++i) {
            for (int j = 0; j < yLimit; ++j) {
                grid[i][j] = 0;
            }
        }
    }

    void barrenLandAnalysis(String input) {
        String[] inputString = input.split(",");
        for (String part : inputString) {
            part = part.replace("\"", "");
            part = part.replaceAll("“|”", "");
            part = part.replaceAll("\\{|\\}", "");
            part = part.replaceAll("^ ", "");

            if (!part.isEmpty()) {
                String[] coordinates = part.split(" ");
                Integer[] rectangle = new Integer[]{Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]),
                        Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3])};
                barrenRectangles.add(rectangle);
            }
        }

        ListIterator iterator = barrenRectangles.listIterator();

        while (iterator.hasNext()) {
            Integer[] rectangle = (Integer[]) iterator.next();

            for (int i = rectangle[0]; i <= rectangle[2]; ++i) {
                for (int j = rectangle[1]; j <= rectangle[3]; ++j) {
                    grid[i][j] = 1;
                }
            }
        }
    }

    void populateUnvisitedPoints(int x, int y) {
        if (grid[x][y] == 0) {
            queue.offer(new Integer[]{x, y});
        }
    }

    void calculateFertileArea() {
        int xCoordinate = 0;
        int yCoordinate = 0;
        int currentSection = 1;

        while (xCoordinate < xLimit && yCoordinate < yLimit) {
            Integer[] currentPoint;
            if (queue.isEmpty()) {
                currentPoint = new Integer[]{xCoordinate, yCoordinate};
                if (grid[xCoordinate][yCoordinate] == 0) {
                    ++currentSection;
                    queue.add(currentPoint);
                    areaMap.put(currentSection, 0);
                }

                if (xCoordinate == 399) {
                    ++yCoordinate;
                    xCoordinate = 0;
                } else {
                    ++xCoordinate;
                }
            }

            if (!queue.isEmpty()) {
                currentPoint = queue.poll();
                int x = currentPoint[0];
                int y = currentPoint[1];
                if (grid[x][y] == 0) {
                    if (x > 0) {
                        populateUnvisitedPoints(x - 1, y);
                    }

                    if (y > 0) {
                        populateUnvisitedPoints(x, y - 1);
                    }

                    if (x < 399) {
                        populateUnvisitedPoints(x + 1, y);
                    }

                    if (y < 599) {
                        populateUnvisitedPoints(x, y + 1);
                    }

                    grid[x][y] = currentSection;
                    areaMap.put(currentSection, areaMap.get(currentSection) + 1);
                }
            }
        }

    }

    void readfromSTDIN() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        barrenLandAnalysis(bufferedReader.readLine());
    }

    String printToSTDOUT() {
        int[] resultantAreas = new int[areaMap.values().size()];
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : areaMap.entrySet()) {
            resultantAreas[count++] = entry.getValue();
        }

        Arrays.sort(resultantAreas);
        return Arrays.toString(resultantAreas).replaceAll("\\[|\\]|,", "");
    }
}