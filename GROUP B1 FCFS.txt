import java.util.Scanner;

class Fcfs {
    public static void main(String[] args) {
        int[] id = new int[20];          // Process IDs
        int[] etime = new int[20];      // Execution Times
        int[] stime = new int[20];      // Service Times
        int[] wtime = new int[20];      // Wait Times
        int[] tat = new int[20];        // Turnaround Times
        int totalWaitTime = 0;          // Total Wait Time
        int totalTurnaroundTime = 0;    // Total Turnaround Time
        float avgWaitTime, avgTurnaroundTime;

        Scanner sn = new Scanner(System.in);
        System.out.print("\nEnter the number of processes: ");
        int n = sn.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println();
            System.out.print("Enter the process ID of process " + (i + 1) + ": ");
            id[i] = sn.nextInt();
            System.out.print("Enter the execution time of process " + (i + 1) + ": ");
            etime[i] = sn.nextInt();
        }

        // Calculate service times
        stime[0] = 0;
        for (int i = 1; i < n; i++) {
            stime[i] = stime[i - 1] + etime[i - 1];
        }

        // Calculate wait times and total wait time
        for (int i = 0; i < n; i++) {
            wtime[i] = stime[i]; // Wait time is the service time for FCFS
            totalWaitTime += wtime[i];
        }

        // Calculate turnaround times and total turnaround time
        for (int i = 0; i < n; i++) {
            tat[i] = wtime[i] + etime[i];
            totalTurnaroundTime += tat[i];
        }

        // Calculate average times
        avgWaitTime = (float) totalWaitTime / n;
        avgTurnaroundTime = (float) totalTurnaroundTime / n;

        // Print the results
        System.out.println("\nProcess ID\tExecution Time\tService Time\tWait Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            System.out.println(id[i] + "\t\t" + etime[i] + "\t\t" + stime[i] + "\t\t" + wtime[i] + "\t\t" + tat[i]);
        }

        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Wait Time: " + avgWaitTime);
    }
}
