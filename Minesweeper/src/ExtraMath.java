public class ExtraMath {
    public static int GetDigitsIn(int i){
        int temp = 1;
        int count = 0;

        while(temp <= i)
        {
            count++;
            temp = temp*10;
        }

        return count;
    }
}
