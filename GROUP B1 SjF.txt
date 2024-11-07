import java.util.Scanner;

class Sjf {
    public static void main(String[] args) {
        int id[] = new int[20];
        int etime[] = new int[20]; 
        int stime[] = new int[20];
        int wtime[] = new int[20];
        int btime[] = new int[20];
        int ctime[] = new int[20];
        int flag[] = new int[20];
        int ta[] = new int[20];
        
        int total = 0;
        int st = 0;
        int temp, i;
        float avgwt = 0, avgta = 0;
        
        Scanner sn = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = sn.nextInt();

        // Input process IDs and execution times
        for (i = 0; i < n; i++) {
            System.out.println();
            System.out.print("Enter the process ID of process " + (i + 1) + ": ");
            id[i] = sn.nextInt();
            System.out.print("Enter the execution time of process " + (i + 1) + ": ");
            etime[i] = sn.nextInt();
            btime[i] = etime[i];
            flag[i] = 0; // Process not yet completed
        }

        // Implementing SJF algorithm
        while (true) {
            int min = 9999, c = n;
            if (total == n) break;

            // Finding the process with the smallest execution time that has arrived
            for (i = 0; i < n; i++) {
                if ((id[i] <= st) && (etime[i] < min) && (flag[i] == 0)) {
                    min = etime[i];
                    c = i;
                }
            }

            if (c == n) {
                st++;
            } else {
                etime[c]--;
                st++;
                if (etime[c] == 0) {
                    ctime[c] = st;
                    flag[c] = 1;
                    total++;
                }
            }
        }

        // Calculating turnaround time and waiting time for each process
        for (i = 0; i < n; i++) {
            ta[i] = ctime[i] - id[i];       // Turnaround time
            wtime[i] = ta[i] - btime[i];    // Waiting time
            avgwt += wtime[i];
            avgta += ta[i];
        }

        // Displaying results
        System.out.println("\nArrival Time\tExecution Time\tCompletion Time\tWait Time\tTurnaround Time");
        for (i = 0; i < n; i++) {
            System.out.println(id[i] + "\t\t" + btime[i] + "\t\t" + ctime[i] + "\t\t" + wtime[i] + "\t\t" + ta[i]);
        }

        System.out.printf("\nAverage wait time: %.2f", (avgwt / n));
        System.out.printf("\nAverage turnaround time: %.2f", (avgta / n));
        
        sn.close();
    }
}
