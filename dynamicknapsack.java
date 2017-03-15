public class dynamicknapsack {
	
static int[][] K;

public static int knap(int n , int[] W , int[] V , int maxW) {

for (int i = 0; i < n + 1; i++)
	for (int j = 0; j < maxW + 1; j++)
		{
		if (i == 0 || j == 0)
			K[i][j] = 0;
		else if (j - W[i - 1] < 0)
			K[i][j] = K[i - 1][j];
		else
			K[i][j] = Math.max(V[i - 1] + K[i - 1][j - W[i - 1]] , K[i - 1][j]);
		}

return K[n][maxW];
}

public static void main(String[] args) {

/*
int n = 8;
int[] W = {1 , 3 , 4 , 3 , 3  , 1 , 5 , 10};
int[] V = {2 , 9 , 3 , 8 , 10 , 6 , 4 , 10};
int maxW = 15;
K = new int[n + 1][maxW + 1];

long start = System.currentTimeMillis();
System.out.println(knap(n , W , V , maxW));
long end = System.currentTimeMillis();

System.out.println((end - start) / 1000.0 + " seconds");
*/
	
/*
int n = 30;
int[] W = {1 , 3 , 4 , 3 , 3  , 1 , 5 , 10 , 1 , 3 , 4 , 3 , 3  , 1 , 5 , 10 , 1 , 3 , 4 , 3 , 3  , 1 , 5 , 10 , 1 , 3 , 4 , 3 , 3  , 1};
int[] V = {2 , 9 , 3 , 8 , 10 , 6 , 4 , 10 , 2 , 9 , 3 , 8 , 10 , 6 , 4 , 10 , 2 , 9 , 3 , 8 , 10 , 6 , 4 , 10 , 1 , 3 , 9 , 3 , 8  , 6};
int maxW = 85;
K = new int[n + 1][maxW + 1];

long start = System.currentTimeMillis();
System.out.println(knap(n , W , V , maxW));
long end = System.currentTimeMillis();

System.out.println((end - start) / 1000.0 + " seconds");
*/

	}
}