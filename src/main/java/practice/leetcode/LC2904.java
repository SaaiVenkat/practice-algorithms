package practice.leetcode;

public class LC2904 {

    static class Solution {

        private String ans;

        public String shortestBeautifulSubstring(String s, int k) {
            int left = 0, right = 0, winCount = 0;
            boolean found = false, isBreak = false;
            boolean[] visited = new boolean[s.length()];
            ans = s;

            while (left <= right) {
                if (winCount < k && !visited[right] && s.charAt(right) == '1') {
                    winCount++;
                }
                visited[right] = true;

                if (winCount == k) {
                    found = true;
                    String winStr = getSubstring(s, left, right);
                    if (isSmallestBeautiful(winStr)) {
                        ans = winStr;
                    }

                    if (s.charAt(left) == '1') {
                        left++;
                        winCount--;
                    }
                    while (left <= right) {
                        if (s.charAt(left) == '1') {
                            isBreak = true;
                            break;
                        } else {
                            left++;
                        }
                    }
                }

                if (isBreak) {
                    isBreak = false;
                    continue;
                }

                if (right < s.length() - 1) {
                    right++;
                } else {
                    left++;
                }
            }

            return found ? ans : "";
        }

        private String getSubstring(String s, int start, int end) {
            StringBuilder sb = new StringBuilder();
            for (int i = start; i <= end && end < s.length(); i++) {
                sb.append(s.charAt(i));
            }
            return sb.toString();
        }

        private boolean isSmallestBeautiful(String winStr) {
            return (winStr.length() < this.ans.length())
                    || ((winStr.length() == this.ans.length())
                    && winStr.compareTo(ans) < 0);
        }
    }

    public static void main(String[] args) {
        String s = "1011";
        int k = 2;
        Solution solution = new Solution();
        System.out.println(solution.shortestBeautifulSubstring(s, k));
    }
}