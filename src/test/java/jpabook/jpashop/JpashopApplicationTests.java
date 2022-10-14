package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

@SpringBootTest
class JpashopApplicationTests {
	static int max = 0;
	static boolean[] visited;
	static int[][] dp;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		visited = new boolean[N];

		int[] W = new int[N+1];
		int[] V = new int[N+1];

		for(int i=1; i<=N;i++){
			W[i]=sc.nextInt();
			V[i]=sc.nextInt();
		}
		dp =  new int[N+1][K+1];


		//무게
		for(int k=1;k<=K;k++){
			for(int n=1;n<=N;n++){ //물품
				dp[n][k] = dp[n-1][k];
				if(k>=W[n])dp[n][k] = Math.max(dp[n-1][k],V[n]+dp[n-1][k-W[n]]);

			}
		}

		/*for(int k=1;k<=K;k++){//무게
			for(int i=1; i<=N;i++){ //item
				dp[i][k] = dp[i-1][k];
				if(k-W[i]>=0)dp[i][k] = Math.max(dp[i-1][k],V[i]+dp[i-1][k-W[i]]);
			}

		}*/
		System.out.println(dp[N][K]);
	}

}

