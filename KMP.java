import java.util.ArrayList;
import java.util.List;

public class KMP {
    
    private int[] makePiTable(String pat) {
        int s = pat.length();
        int[] lps = new int[s];
        lps[0] = 0;

        int len = 0, i = 1;
        while (i < s) {
            if (pat.charAt(len) == pat.charAt(i)) {
                ++len;
                lps[i] = len;
                ++i;
            }
            else {
                if (len != 0) {
                    len = lps[len - 1];
                }
                else {  // len == 0
                    lps[i] = 0;
                    ++i;
                }
            }
        }
        return lps;
    }    

    public List<Integer> KMPSearch(String txt, String pat) {
        int txtSize = txt.length();
        int patSize = pat.length();

        int[] piTable = makePiTable(pat);

        List<Integer> res = new ArrayList<>();

        int i = 0, j = 0;
        while (i < txtSize) {
            // char ti = txt.charAt(i);
            // char pj = pat.charAt(j);
            if (txt.charAt(i) == pat.charAt(j)) {
                ++i;
                ++j;
            }

            if (j == patSize) {
                res.add(i - j);
                j = piTable[j - 1];
            }
            else if (i < txtSize && txt.charAt(i) != pat.charAt(j)) {  // mismatch
                if (j != 0) {
                    j = piTable[j - 1];
                }
                else {
                    ++i;
                }
            }
        }
        return res;
    }
    
}