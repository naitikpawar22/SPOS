import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  

public class OptimalReplacement {  
    public static void main(String[] args) throws IOException {  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  

        // Initialize variables
        int frames, pointer = 0, hit = 0, fault = 0, ref_len;  
        boolean isFull = false;  
        int buffer[];  
        int reference[];  
        int mem_layout[][];  

        // Input for number of frames
        System.out.println("Please enter the number of Frames: ");  
        frames = Integer.parseInt(br.readLine());  

        // Input for length of the reference string
        System.out.println("Please enter the length of the Reference string: ");  
        ref_len = Integer.parseInt(br.readLine());  

        // Initialize arrays
        reference = new int[ref_len];  
        mem_layout = new int[ref_len][frames];  
        buffer = new int[frames];  

        // Fill buffer initially with -1 (indicating empty slots)
        for (int j = 0; j < frames; j++)  
            buffer[j] = -1;  

        // Input the reference string
        System.out.println("Please enter the reference string: ");  
        for (int i = 0; i < ref_len; i++) {  
            reference[i] = Integer.parseInt(br.readLine());  
        }  
        System.out.println();  

        // Optimal Page Replacement Algorithm
        for (int i = 0; i < ref_len; i++) {  
            int search = -1;  

            // Check if the page is already in the buffer (hit)
            for (int j = 0; j < frames; j++) {  
                if (buffer[j] == reference[i]) {  
                    search = j;  
                    hit++;  
                    break;  
                }   
            }  

            // If page is not in buffer (fault), find a replacement
            if (search == -1) {  
                if (isFull) {  
                    int index[] = new int[frames];  
                    boolean index_flag[] = new boolean[frames];  

                    // Initialize index_flag to track future usage of pages
                    for (int j = 0; j < frames; j++)  
                        index_flag[j] = false;  

                    // Check future references to determine the optimal page to replace
                    for (int j = i + 1; j < ref_len; j++) {  
                        for (int k = 0; k < frames; k++) {  
                            if ((reference[j] == buffer[k]) && (!index_flag[k])) {  
                                index[k] = j;  
                                index_flag[k] = true;  
                                break;  
                            }  
                        }  
                    }  

                    // Find the page with the farthest future use or not used at all
                    int max = index[0];  
                    pointer = 0;  
                    if (max == 0)  
                        max = ref_len + 1;  
                    for (int j = 0; j < frames; j++) {  
                        if (index[j] == 0)  
                            index[j] = ref_len + 1;  
                        if (index[j] > max) {  
                            max = index[j];  
                            pointer = j;  
                        }  
                    }  
                }  

                // Replace the page in the buffer
                buffer[pointer] = reference[i];  
                fault++;  

                // Update pointer and isFull flag
                if (!isFull) {  
                    pointer++;  
                    if (pointer == frames) {  
                        pointer = 0;  
                        isFull = true;  
                    }  
                }  
            }  

            // Update memory layout for each step
            for (int j = 0; j < frames; j++)  
                mem_layout[i][j] = buffer[j];  
        }  

        // Print memory layout
        System.out.println("\nMemory Layout:");
        for (int i = 0; i < frames; i++) {  
            for (int j = 0; j < ref_len; j++)  
                System.out.printf("%3d ", mem_layout[j][i]);  
            System.out.println();  
        }  

        // Display hits, hit ratio, and faults
        System.out.println("The number of Hits: " + hit);  
        System.out.println("Hit Ratio: " + (float) hit / ref_len);  
        System.out.println("The number of Faults: " + fault);  
    }  
}
