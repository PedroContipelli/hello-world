import java.util.*;
import java.io.*;
public class Skyscraper2 {
	
public static HashSet<Integer>[][] generateCandidates(int n) {

	HashSet<Integer>[][] candidates = new HashSet[n][n];
	
	for (int i = 0; i < n; i++)
		for (int j = 0; j < n; j++)
			{
			candidates[i][j] = new HashSet<Integer>();
			
			for (int x = 1; x <= n; x++)
				candidates[i][j].add(x);
			}
	
	return candidates;
}

public static HashMap<String , HashSet<int[]>> generateClueMap(int n) {

	HashMap<String , HashSet<int[]>> clueMap = new HashMap<>();
	
	int[] nums = new int[n];
	
	for (int i = 1; i <= nums.length; i++)
		{
		nums[i - 1] = i;
		clueMap.put(String.valueOf(i) , new HashSet<int[]>());
		}
	
	permute(nums , 0 , clueMap);
	
	return clueMap;
}

public static void permute(int[] nums , int index , HashMap<String , HashSet<int[]>> clueMap) {

	if (index == nums.length)
		clueMap.get(visibleSkyscrapers(nums)).add(Arrays.copyOf(nums , nums.length));
	
	for (int i = index; i < nums.length; i++)
		{
		int temp = nums[i];
		nums[i] = nums[index];
		nums[index] = temp;
		
		permute(nums , index + 1 , clueMap);
		
		temp = nums[i];
		nums[i] = nums[index];
		nums[index] = temp;
		}
}

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


public static void removeCandidates(HashSet<Integer>[][] candidates , HashMap<String , HashSet<int[]>> clueMap , String[] info) {
	
	for (int side = 0; side < 4; side++)
		for (int i = 0; i < candidates.length; i++)
			removeSectionCandidates(getRowColCandidates(candidates , side , i) , clueMap.get(info[side].substring(i , i + 1)));
}

public static HashSet<Integer>[] getRowColCandidates(HashSet<Integer>[][] candidates , int side , int i) {

	int n = candidates.length;
	
	HashSet<Integer>[] section = new HashSet[n];
	
	if (side == 1 || side == 2)
		for (int j = 0; j < n; j++)
			section[j] = candidates[i][side == 1 ? j : n - 1 - j];
	else	
		for (int j = 0; j < n; j++)
			section[j] = candidates[side == 0 ? j : n - 1 - j][i];
	
	return section;
}

public static void removeSectionCandidates(HashSet<Integer>[] section , HashSet<int[]> clue) {
	
	int n = section.length;
	
	HashSet<Integer>[] allFits = new HashSet[n];
	
	for (int i = 0; i < allFits.length; i++)
		allFits[i] = new HashSet<Integer>();
	
	for (int[] permutation : clue)
		if (fit(section , permutation))
			add(allFits , permutation);
	
	for (int i = 0; i < section.length; i++)
		section[i].retainAll(allFits[i]);
}

public static boolean fit(HashSet<Integer>[] section , int[] permutation) {

	for (int i = 0; i < section.length; i++)
		if (!section[i].contains(permutation[i]))
			return false;
	
	return true;
}

public static void add(HashSet<Integer>[] fits , int[] permutation) {
	
	for (int i = 0; i < fits.length; i++)
		fits[i].add(permutation[i]);
}

public static String[] info(String line , int n) {

	String[] info = new String[4];
	Arrays.fill(info , "");
	
	StringTokenizer token = new StringTokenizer(line , " ");
	
	info[0] = token.nextToken();
	
	for (int i = 0; i < n; i++)
		{
		String row = token.nextToken();
		
		info[1] += row.substring(0 , 1);
		info[2] += row.substring(1 , 2);
		}
	
	info[3] = token.nextToken();
	
	return info;
}

public static boolean done(HashSet<Integer>[][] board) {
	
	for (int i = 0; i < board.length; i++)
		for (int j = 0; j < board.length; j++)
			if (board[i][j].size() > 1)
				return false;
	
	return true;
}

public static void print(HashSet<Integer>[][] nums) {
	
	for (int i = 0; i < nums.length; i++)
		System.out.print(Arrays.toString(nums[i]).replaceAll("[^0-9]", "") + (i != nums.length - 1 ? ", " : ""));
	
	System.out.println("\n\n------------------------------\n");
}

public static void debug(HashSet<Integer>[][] nums) {
	
	for (int i = 0; i < nums.length; i++)
		System.out.println(Arrays.toString(nums[i]));
	
	System.out.println();
}

public static void main(String[] args) throws IOException , InterruptedException {
File inFile = new File("SKYSCRAPER.IN");
Scanner scan = new Scanner(inFile);

for (int lines = 1; lines <= 5; lines++)
	{
	System.out.println("Line #" + lines + "\n");
	int n = lines <= 3 ? 4 : 5;
	
	HashMap<String , HashSet<int[]>> clueMap = generateClueMap(n);
	HashSet<Integer>[][] candidates = generateCandidates(n);
	String[] info = info(scan.nextLine() , n);
	
	debug(candidates);
	
	while (!done(candidates))
		{
		removeCandidates(candidates , clueMap , info);
		debug(candidates);
		}
	
	print(candidates);
	}

scan.close();
	}
}