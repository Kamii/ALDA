public class AlgAn{
    public static void main(String []args){
        final int n = 1000;
        long startTime = 0; 
        long estimatedTime = 0; 
        // Exempel 1
        startTime = System.nanoTime();
        int sum = 0 ;
        for (int i = 0 ; i < n ; i++)
            sum++;
        estimatedTime = System.nanoTime() - startTime;
        System.out.print("1: ");
        System.out.println(sum +"\t\t"+estimatedTime);

        // Exempel 2
        startTime = System.nanoTime();
        int sum2 = 0 ;
        for (int i = 0 ; i < n ; i ++){
            for (int j = 0 ; j < n ; j ++)
                sum2++;  
        }
        estimatedTime = System.nanoTime() - startTime;
        System.out.print("2: ");
        System.out.println(sum2 +"\t"+estimatedTime);

        // Exempel 3
        startTime = System.nanoTime();
        long sum3 = 0 ;
        for (int i = 0 ; i < n ; i++){
            for (int j = 0 ; j < (n * n) ; j++)
                sum3++;
        }
        estimatedTime = System.nanoTime() - startTime;
        System.out.print("3: ");
        System.out.println(sum3 +"\t"+estimatedTime);

        // Exempel 4
        startTime = System.nanoTime();
        long sum4 = 0 ;
        int a4;
        int b4;
        for (int i=0 ; i<n ; i++){
            //System.out.println(":"+sum4);
            for (long j=0 ; j<i ; j++)
                sum4++;
        }
        estimatedTime = System.nanoTime() - startTime;
        System.out.print("4: ");
        System.out.println(sum4 +"\t\t"+estimatedTime);

        // Exempel 5
        startTime = System.nanoTime();
        long sum5 = 0 ;
        for (long i = 0 ; i < n ; i++) //n
            for (long j = 0 ; j < i * i ; j++)//n^2
                for (long k = 0 ; k < j ; k++)//n^2
                    sum5++;
        estimatedTime = System.nanoTime() - startTime;
        System.out.print("5: ");
        System.out.println(sum5 +"\t"+estimatedTime);

        // Exempel 6
        startTime = System.nanoTime();
        long sum6 = 0;
        int count=0;
        for (int i = 1 ; i < n ; i++){//n
            count++;
            for (long j = 1 ; j < i * i ; j++){//n^2
                count++;
                if ( j % i == 0 ){
                    count++;
                    for (int k = 0 ; k < j ; k++){//n^2
                        count++;
                        sum6++;
                    }
                }
            }
        }
        estimatedTime = System.nanoTime() - startTime;
        System.out.print("6: ");
        System.out.println(sum6 +"\t"+estimatedTime);
    }
}
