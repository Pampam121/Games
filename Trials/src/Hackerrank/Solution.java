package Hackerrank;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

	// Complete the activityNotifications function below.
	static int activityNotifications(int[] expenditure, int d) {
		int res = 0;
		int n = expenditure.length;
		boolean even = d % 2 == 0;
		int[] count = new int[201];
		for (int i = 0; i < d; i++) {
			count[expenditure[i]]++;
		}
		int pos = -1, pos2 = 0;
		int sum = 0;
		
		for (int i = d; i < n; i++) {
			while (sum < (d + 1) / 2) {
				pos++;
				sum += count[pos];
			}
			if (even) {
				pos2 = pos;
				if (sum == d / 2) {
					do {
						pos2++;
					} while (count[pos2] == 0);

				}
			}
			int medi;
			if (even) {
				medi = pos + pos2;
			} else {
				medi = pos * 2;
			}
			if (expenditure[i] >= medi) {
				res++;
			}
			sum -= count[pos];
			count[expenditure[i]]++;
			if(expenditure[i]<pos) {
				sum++;
			}
			count[expenditure[i-d]]--;
			if(expenditure[i-d]<pos) {
				sum--;
			}
			pos= pos<1 ? 0 : pos-1;
			if(sum == (d+1)/2) {
				while(count[pos]==0) {
					pos--;
				}
			}
			
			
		}
		return res;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
//		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

		String[] nd = scanner.nextLine().split(" ");

		int n = Integer.parseInt(nd[0]);

		int d = Integer.parseInt(nd[1]);

		int[] expenditure = new int[n];

		String[] expenditureItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int expenditureItem = Integer.parseInt(expenditureItems[i]);
			expenditure[i] = expenditureItem;
		}

		int result = activityNotifications(expenditure, d);

		System.out.println(result);

		scanner.close();
	}
}
