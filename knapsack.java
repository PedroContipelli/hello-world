public class knapsack {
	
public static int knap(int n , int[] W , int[] V , int maxW) {

if (n == 0 || maxW == 0)
	return 0;

if (maxW - W[n - 1] < 0)
	return knap(n - 1 , W , V , maxW);

return Math.max(V[n - 1] + knap(n - 1 , W , V , maxW - W[n - 1]) , knap(n - 1 , W , V , maxW));

}

public static void main(String[] args) {

/*
int n = 8;
int[] W = {1 , 3 , 4 , 3 , 3  , 1 , 5 , 10};
int[] V = {2 , 9 , 3 , 8 , 10 , 6 , 4 , 10};
int maxW = 15;

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

long start = System.currentTimeMillis();
System.out.println(knap(n , W , V , maxW));
long end = System.currentTimeMillis();

System.out.println((end - start) / 1000.0 + " seconds");
*/
	
	}
}