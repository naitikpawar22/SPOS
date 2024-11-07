import java.io.*;  

public class FIFO {  
    public static void main(String[] args) throws IOException {  
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
        
        // Initialize variables
        int frames, pointer = 0, hit = 0, fault = 0, ref_len;  
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

        // Fill buffer initially with -1 (empty state)
        for (int j = 0; j < frames; j++)  
            buffer[j] = -1;  

        // Input the reference string
        System.out.println("Please enter the reference string: ");  
        for (int i = 0; i < ref_len; i++) {  
            reference[i] = Integer.parseInt(br.readLine());       
        }  
        
        // FIFO Page Replacement Algorithm
        for (int i = 0; i < ref_len; i++) {  
            int search = -1;  
            
            // Check if the reference is already in the buffer (hit)
            for (int j = 0; j < frames; j++) {  
                if (buffer[j] == reference[i]) {  
                    search = j;  
                    hit++;  
                    break;  
                }  
            }  

            // If not in buffer (fault), replace the oldest page
            if (search == -1) {  
                buffer[pointer] = reference[i];  
                fault++;  
                pointer++;  
                
                // Reset pointer if it exceeds frame limit
                if (pointer == frames)  
                    pointer = 0;  
            }  

            // Update memory layout for this reference
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
        System.out.println("Hit Ratio: " + (float)hit / ref_len);  
        System.out.println("The number of Faults: " + fault);  
    }     
}  
