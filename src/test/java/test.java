import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class test {

    @Test
    public static int test(String s){
        if (s==null||s.length()==0){
            return 0;
        }
        HashMap<Character,Integer> map=new HashMap<>();

        int res=0;
        for (int i = 0,j=0; j < s.length(); j++) {
            if (map.containsKey(s.charAt(j))){
                i=Math.max(i, map.get(s.charAt(j))+1);

            }
            map.put(s.charAt(j),j);
            res=Math.max(res,j-i+1);
        }
        return res;

    }
}
