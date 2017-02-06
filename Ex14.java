package Complexity;

public class Ex14 {

	/**
	 * Q1. a. This method returns the size of the largest sequence of indexes
	 * whose sum is divisible by 3 Q1. b. O(n^3) Q1. c. Q1.d. Time = O(n)
	 */
	public static int what(int[] a) {
		int length = a.length;
		int zeroesLeft = 0, zeroesRight = 0, sum = 0;
		int leftIndex = 0, rightIndex = a.length - 1;
		// sum of all remainders in the array
		for (int i = 0; i < a.length; i++) {
			sum+=a[i];
		}
		
		// we only care about the remainder of the sum
		if (sum<0)
			sum = 3 + sum%3;
		else
			sum = sum % 3;

		if (sum == 0)
			return a.length;
		else if (a.length < 2)
			return 0;

		// count the number of zeroes from the left
		for (int i = 0; i < a.length && a[i]%3 == 0; i++) { 
			zeroesLeft++;
			leftIndex++;
		}

		// count the number of zeroes from the right
		for (int j = a.length - 1; j >= 0 && a[j]%3 == 0; j--) { 
			zeroesRight++;
			rightIndex--;
		}
		
		// first non zero from both sides is equal to the sum remainder
		if (a[leftIndex]%3 == sum && a[rightIndex]%3 == sum) 
			return Math.max(length - zeroesLeft - 1, length - zeroesRight - 1); 
		
		// left side first non zero is equal to remainder
		else if (a[leftIndex]%3 == sum)
			return Math.max(countFromRight(a, rightIndex, sum), length - zeroesLeft - 1);
		
		// right side first non zero is equal to remainder
		else if (a[rightIndex]%3 == sum)
			return Math.max(countFromLeft(a, leftIndex, sum), length - zeroesRight - 1);
		
		 // none first non zeroes are equal to remainder, in this case removing both will give us the needed array
		else{
			return Math.max(countFromLeft(a, leftIndex, sum),
					Math.max(countFromRight(a, rightIndex, sum), length - zeroesLeft - zeroesRight - 2));
		}
	}

	private static int countFromRight(int[] arr, int startingIndex, int sum) {
		// Return the length of the array we get by removing elements only from
		// the left of the arr to achieve the sum
		int zeroesRight = arr.length - startingIndex - 1;
		int cellDeleteCount = 0;
		for (int i = startingIndex; i >= 0 && sum != 0; i--) {
			if (arr[i]<0)
				sum = sum - (3+arr[i]%3);
			else
				sum = sum - arr[i]%3;
			if (sum<0)
				sum = 3 - sum;
			cellDeleteCount++;
		}
		return arr.length - zeroesRight - cellDeleteCount - 1;
	}

	private static int countFromLeft(int[] arr, int startingIndex, int sum) {
		// Return the length of the array we get by removing elements only from
		// the right of the arr to achieve the sum
		int zeroesLeft = startingIndex;
		int cellDeleteCount = 0;
		for (int i = startingIndex; i < arr.length && sum != 0; i++) {
			if (arr[i]<0)
				sum = sum - (3+arr[i]%3);
			else
				sum = sum - arr[i]%3;
			if (sum<0)
				sum = 3 - sum;
			cellDeleteCount++;
		}
		return arr.length - zeroesLeft - cellDeleteCount - 1;
	}

	/**
	 * Q2 Time = O(n)
	 */
	public static void zeroDistance(int[] a) // total complexity = O(n)
	{
		int firstO = a.length - 1;
		int lastO = 0;
		//find first and last 0
		for (int i = 0; i < a.length; i++) {
			if (a[i] == 0) {
				if (i < firstO)
					firstO = i;
				if (i > lastO)
					lastO = i;
			}
		}

		int rDistance = 0;
		// Goes over from left to right and changes the values to be the
		// distance from last zero
		for (int i = 0; i < a.length; i++) {
			// Other loop will cover the distance between start of the array and the first 0
			if (i > firstO) {
				if (a[i] == 0)
					rDistance = 0;
				else {
					rDistance++;
					a[i] = rDistance;
				}
			}

		}
		int lDistance = 0;
		// Goes over from right to left and changes the values to be the distance from last zero
		// only if smaller than distance as set by previous loop
		for (int i = a.length - 1; i >= 0; i--) {
			//other loop already covered the distance between last 0 and end
			if (i < lastO) {
				if (a[i] == 0)
					lDistance = 0;
				else {
					lDistance++;
					// will change the value only if it's smaller than current value, unless we passed the first 0. 
					// then we want to count the distance from that 0 and not from the beginning as previous loop does.
					if (lDistance < a[i] || i < firstO)
						a[i] = lDistance;
				}
			}
		}
	}

	/** Q3 */
	public static boolean isTrans(String s, String t) {
		int i = 0;
		int j = 0;
		boolean iWasChecked = false;
		return transChecker(s, t, i, j, iWasChecked);

	}

	private static boolean transChecker(String s, String t, int i, int j, boolean iWasChecked) {
		// if j is shorter than s it can't contain all chars in s
		if (t.length() < s.length())
			return false;
		// we have checked all of t and nothing was false
		if (j == t.length()) {
			// in case last char repeats itself in s
			if (i < s.length() - 1) {
				i++;
				iWasChecked = false;
				if (s.charAt(i) == s.charAt(i - 1))
					iWasChecked = true;
				return transChecker(s, t, i, j, iWasChecked);
			}
			// the last char in s was checked
			else if (iWasChecked)
				return true;
			return false;
		}
		// indexes are equal, moving on to next j and marking i as checked
		else if (t.charAt(j) == s.charAt(i)) {
			j++;
			iWasChecked = true;
			return transChecker(s, t, i, j, iWasChecked);
		}
		// s[i] and t[j] are not equal, but s[i] was checked at least once
		// before that so we move on to next s[i]
		else if (iWasChecked && i < (s.length() - 1)) {
			i++;
			iWasChecked = false;
			// in case s[i] apears more than once
			if (s.charAt(i) == s.charAt(i - 1))
				iWasChecked = true;
			return transChecker(s, t, i, j, iWasChecked);
		}
		return false;

	}

	/** Q4 */
	public static boolean match(int[] a, int[] pattern) {
		int i = 0;
		int j = 0;
		return checker(a, pattern, i, j);

	}

	// computes the number of digits in an intger
	private static int digits(int n) {
		if ((-10 < n) && (n < 10))
			return 1;
		else
			return 1 + digits(n / 10);
	}

	private static boolean checker(int[] a, int[] pattern, int i, int j) {
		if (j == pattern.length)
			// we have reached the end of pattern without j being reset
			return true;
		if (i == a.length)
			// we have reached the end of a without finding the pattern
			return false;
		else if ((digits(a[i])) == pattern[j] || pattern[j] == 0) {
			// a[i] fits pattern[j], checking next index in pattern and a
			j++;
			i++;
			return checker(a, pattern, i, j);
		}

		else if (i < a.length - pattern.length) {
			// if i > a.length - pattern.length there's no point in checking anymore
			// since pattern won't fit in the remaining array we reset j and check from the beginning.
			j = 0;
			if (digits(a[i]) == pattern[j]) {
				// if current i fits j=0 we continue checking next index as before
				j++;
				if (i < a.length) {
					i++;
					return checker(a, pattern, i, j);
				} else
					return checker(a, pattern, i, j);
			}
			else {
				// if not we keep j=0 and move on to next i
				i++;
				return checker(a, pattern, i, j);
			}
		}
		return false;
		// pattern can't fit anymore - false
	}
}
