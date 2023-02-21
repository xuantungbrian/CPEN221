package cpen221.mp1.cryptanalysis;

public abstract class Untangler {

    /**
     * Determine if {@code superposition} is a result of
     * a tangling of {@code src1} and {@code src2}.
     *
     * requires that parameters are not null.
     * @param superposition the possibly tangled signal
     * @param src1 the first signal
     * @param src2 the second signal
     *
     * @return true if {@code superposition} is a
     * tangling of {@code src1} and {@code src2} and false otherwise.
     */
    public static boolean areTangled(String superposition, String src1, String src2) {
        if (superposition.isEmpty() || src1.isEmpty() || src2.isEmpty() || !containtsFirstBites(superposition,src1,src2)){
            return false;
        }
        if (stringCheck(superposition,src1,src2)){
            return true;
        }
        if (stringCheck(superposition,src2,src1)){
            return true;
        }
        return false;
    }

    /**
     * Determine by taking account str1 first if {@code superPos} is a result of
     * a tangling of {@code str1} and {@code str2}.
     *
     * requires that parameters are not null.
     * @param superPos the possibly tangled signal
     * @param str1 the first signal
     * @param str2 the second signal
     * @return true if by taking account str1 first {@code superPos} is a result of
     *  a tangling of {@code str1} and {@code str2} otherwise return false.
     */
    private static boolean stringCheck(String superPos, String str1,String str2){
        int index1 = 0;
        int index2 = 0;
        for(char c : superPos.toCharArray()){
            if (index1==str1.length()){index1=0;}

            if (index2==str2.length()){index2=0;}

            while (true){
                if (c==str1.charAt(index1)){
                    index1++;
                    break;
                }
                else if(c==str2.charAt(index2)){
                    index2++;
                    break;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determine if {@code superposition} contains the
     * first  bites of {@code src1} and {@code src2}.
     *
     * requires that parameters are not null.
     * @param superPosition the possibly tangled signal
     * @param str1 the first signal
     * @param str2 the second signal
     * @return true is {@code superPosition} contains first bites
     * of {@code str1} and {@code str2} and false otherwise.
     */
    private static boolean containtsFirstBites(String superPosition,String str1,String str2) {
        int index = 0;
        for (char c : superPosition.toCharArray()) {
            if (c == str1.charAt(0)) {
                index++;
                break;
            }
        }
        for (char c : superPosition.toCharArray()) {
            if (c == str2.charAt(0)) {
                index++;
                break;
            }
        }
        if (index == 2) {
            return true;
        }
        return false;
    }
}
