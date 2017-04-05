import java.util.*;
import java.io.*;

/*
Pedro Contipelli Grade 10
Doral Academy #7098 SENIOR-5 Division
2016-2017 American Computer Science League
Contest #4 “ACSL Skyscraper” DUE DATE 03/30/17
PROJECT NAME: “ACSL Skyscraper Project”
CLASS NAME: “Skyscraper”
INPUT FILENAME: “SKYSCRAPER.IN”
On my honor I have neither given nor received help,
nor will I give help on this program
*/

public class Skyscraper {

public static void main(String[] args) throws IOException {
	
File inFile = new File("SKYSCRAPER.IN");
Scanner scan = new Scanner(inFile);

for (int i = 1; i <= 4; i++)
	fours.put(String.valueOf(i) , new HashSet<int[]>());

for (int i = 1; i <= 5; i++)
	fives.put(String.valueOf(i) , new HashSet<int[]>());

int[] nums4 = {1 , 2 , 3 , 4};
int[] nums5 = {1 , 2 , 3 , 4 , 5};

permute(nums4 , 0);
permute(nums5 , 0);

for (int lines = 1; lines <= 5; lines++)
	{
	HashSet<Integer>[][] map4 = new HashSet[4][4];
	HashSet<Integer>[][] map5 = new HashSet[5][5];
	
	for (int i = 0; i < 5; i++)
		for (int j = 0; j < 5; j++)
			{
			if (i < 4 && j < 4)
				{
				map4[i][j] = new HashSet<>();
				
				for (int k = 1; k <= 4; k++)
					map4[i][j].add(k);
				}
			
			map5[i][j] = new HashSet<>();
			
			for (int k = 1; k <= 5; k++)
				map5[i][j].add(k);
			}
	
	HashSet<Integer>[][] map = lines <= 3 ? map4 : map5;
	int N = lines <= 3 ? 4 : 5;
	
	String[] info = info(scan.nextLine() , N);
	
	for (int side = 0; side < 4; side++)
		clues(map , info , side);
	
	int[][] nums = new int[N][N];
	fillCandidates(map , nums , info);
	
	print(nums);
	}

scan.close();
}
	
static HashMap<String , HashSet<int[]>> fours = new HashMap<>();
static HashMap<String , HashSet<int[]>> fives = new HashMap<>();

public static String visibleSkyscrapers(int[] nums) {

	int biggest = 0;
	int count = 0;
	
	for (int x : nums)
		if (x > biggest)
			{
			count++;
			biggest = x;
			}
	
	return String.valueOf(count);
}

public static void permute(int[] nums , int index) {

if (index == nums.length)
	{
	if (nums.length == 4)
		fours.get(visibleSkyscrapers(nums)).add(Arrays.copyOf(nums , nums.length));
	else if (nums.length == 5)
		fives.get(visibleSkyscrapers(nums)).add(Arrays.copyOf(nums , nums.length));
	
	return;
	}

for (int i = index; i < nums.length; i++)
	{
	int temp = nums[i];
	nums[i] = nums[index];
	nums[index] = temp;
	
	permute(nums , index + 1);
	
	temp = nums[i];
	nums[i] = nums[index];
	nums[index] = temp;
	}
}

public static void clues(HashSet<Integer>[][] map , String[] info , int side) {
	for (int i = 0; i < map.length; i++)
		{
		HashSet<Integer>[] candidates = new HashSet[map.length];
		
		for (int z = 0; z < map.length; z++)
			candidates[z] = new HashSet<>();
		
		for (int[] cols : map.length == 4 ? fours.get(info[side].substring(i , i + 1)) : fives.get(info[side].substring(i , i + 1)))
			for (int j = 0; j < map.length; j++)
				candidates[j].add(cols[j]);
		
		for (int j = 0; j < map.length; j++)
			map[side % 3 == 0 ? j : i][side % 3 == 0 ? i : j].retainAll(candidates[side <= 1 ? j : map.length - j - 1]);
		}
}

public static void fillCandidates(HashSet<Integer>[][] map , int[][] nums , String[] info) {

	while (!isFilled(nums))
		{
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map.length; j++)
				if (nums[i][j] == 0 && map[i][j].size() == 1)
					for (int single : map[i][j])
						nums[i][j] = single;
				else if (nums[i][j] == 0)
					map[i][j].retainAll(rowColumnNumbers(nums , i , j));
				else if (map[i][j].size() > 1)
					{
					map[i][j].clear();
					map[i][j].add(nums[i][j]);
					}
		
		String finalClues = info[0] + info[1] + info[2] + info[3];
		
		for (int i = 0; i < finalClues.length(); i++)
			{
			String clue = finalClues.substring(i , i + 1);
			int x = countClueFits(clue , getRowColumn(i , nums) , getRowColumn(i , map));
			
			if (x == 1)
				fillRowColumn(i , clue , nums , map);
			}
		
		sudokuTrickRepeat(map);
		}
}

public static HashSet<Integer> rowColumnNumbers(int[][] nums , int j , int k) {
	
	HashSet<Integer> all = new HashSet<>();
	
	for (int i = 1; i <= nums.length; i++)
		all.add(i);
	
	for (int i = 0; i < nums.length; i++)
		{
		all.remove(nums[j][i]);
		all.remove(nums[i][k]);
		}
	
	return all;
}

public static boolean isFilled(int[][] nums) {
	
	for (int i = 0; i < nums.length; i++)
		for (int j = 0; j < nums.length; j++)
			if (nums[i][j] == 0)
				return false;
	
	return true;
}

public static boolean clueFit(int[] clue , int[] nums , HashSet<Integer>[] candidates) {

	for (int i = 0; i < nums.length; i++)
		if ((clue[i] != nums[i] && nums[i] != 0) || (nums[i] == 0 && !candidates[i].contains(clue[i])))
			return false;
	
	return true;
}

public static int countClueFits(String clue , int[] nums , HashSet<Integer>[] candidates) {

	int count = 0;
	
	for (int[] x : nums.length == 4 ? fours.get(clue) : fives.get(clue))
		if (clueFit(x , nums , candidates))
			count++;
	
	return count;
}

public static int[] getRowColumn(int rc , int[][] nums) {

	int[] rowCol = new int[nums.length];
	
	if (rc <= nums.length - 1)
		for (int i = 0; i < rowCol.length; i++)
			rowCol[i] = nums[i][rc];
	else if (rc <= 2*nums.length - 1)
		for (int i = 0; i < rowCol.length; i++)
			rowCol[i] = nums[rc % nums.length][i];
	else if (rc <= 3*nums.length - 1)
		for (int i = 0; i < rowCol.length; i++)
			rowCol[i] = nums[rc % (2*nums.length)][rowCol.length - i - 1];
	else
		for (int i = 0; i < rowCol.length; i++)
			rowCol[i] = nums[rowCol.length - i - 1][rc % (3*nums.length)];
	
	return rowCol;
}

public static HashSet<Integer>[] getRowColumn(int rc , HashSet<Integer>[][] nums) {

	HashSet<Integer>[] rowCol = new HashSet[nums.length];
	
	if (rc <= nums.length - 1)
		for (int i = 0; i < rowCol.length; i++)
			rowCol[i] = nums[i][rc];
	else if (rc <= 2*nums.length - 1)
		for (int i = 0; i < rowCol.length; i++)
			rowCol[i] = nums[rc % nums.length][i];
	else if (rc <= 3*nums.length - 1)
		for (int i = 0; i < rowCol.length; i++)
			rowCol[i] = nums[rc % (2*nums.length)][rowCol.length - i - 1];
	else
		for (int i = 0; i < rowCol.length; i++)
			rowCol[i] = nums[rowCol.length - i - 1][rc % (3*nums.length)];
	
	return rowCol;
}

public static void fillRowColumn(int rc , String clue , int[][] nums , HashSet<Integer>[][] map) {
	
	for (int[] x : nums.length == 4 ? fours.get(clue) : fives.get(clue))
		if (clueFit(x , getRowColumn(rc , nums) , getRowColumn(rc , map)))
			{
			if (rc <= nums.length - 1)
				for (int i = 0; i < x.length; i++)
					nums[i][rc] = x[i];
			else if (rc <= 2*nums.length - 1)
				for (int i = 0; i < x.length; i++)
					nums[rc % nums.length][i] = x[i];
			else if (rc <= 3*nums.length - 1)
				for (int i = 0; i < x.length; i++)
					nums[rc % (2*nums.length)][x.length - i - 1] = x[i];
			else
				for (int i = 0; i < x.length; i++)
					nums[x.length - i - 1][rc % (3*nums.length)] = x[i];
			}
}

public static void sudokuTrickRepeat(HashSet<Integer>[][] map) {

	for (int zax = 0; zax < 20; zax++)
		for (int i = 0; i < map.length; i++)
			{
			sudokuTrick(map[i]);
		
			HashSet<Integer>[] col = new HashSet[map.length];
			
			for (int j = 0; j < map.length; j++)
				col[j] = map[j][i];
			
			sudokuTrick(col);
			}
}

public static void sudokuTrick(HashSet<Integer>[] map) {

	List<HashSet<Integer>> nums = Arrays.asList(map);
	
	for (int i = 0; i < map.length; i++)
		if (Collections.frequency(nums , map[i]) == map[i].size())
			{
			for (int x = 0; x < map.length; x++)
				if (!map[x].equals(map[i]))
					map[x].removeAll(map[i]);
			}
}

public static String[] info(String data , int fivefour) {
	String[] info = new String[4];
	Arrays.fill(info , "");
	
	StringTokenizer token = new StringTokenizer(data , " ");
	
	info[0] = token.nextToken();
	
	String rows = "";
	
	for (int i = 0; i < fivefour; i++)
		rows += token.nextToken();
	
	for (int i = 0; i < rows.length(); i += 2)
		{
		info[1] += rows.substring(i , i + 1);
		info[2] += rows.substring(i + 1 , i + 2);
		}
	
	info[3] = token.nextToken();
	
	return info;
}

public static void print(int[][] nums) {
	
	for (int i = 0; i < nums.length; i++)
		{
		String line = Arrays.toString(nums[i]);
		System.out.print(line.substring(1 , line.length() - 1).replace("," , "").replace(" " , "") + (i != nums.length - 1 ? ", " : ""));
		}
	
	System.out.println();
}

}

/*
1234, 2413, 4321, 3142
3421, 4312, 2143, 1234
4321, 2413, 1234, 3142
12453, 31542, 54321, 25134, 43215
31254, 54321, 12543, 23415, 45132
*/