import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Pass2 {

    public static void main(String[] args) {
        try {
            // Initialize file readers for intermediate file, symbol table, and literal table
            BufferedReader intermediateReader = new BufferedReader(new FileReader("intermediate.txt"));
            BufferedReader symtabReader = new BufferedReader(new FileReader("symtab.txt"));
            BufferedReader littabReader = new BufferedReader(new FileReader("littab.txt"));
            FileWriter outputWriter = new FileWriter("Pass2.txt");

            // Load symbol and literal tables
            HashMap<Integer, String> symSymbol = loadSymbolTable(symtabReader);
            HashMap<Integer, String> litAddr = loadLiteralTable(littabReader);

            // Process intermediate code and write output
            processIntermediateCode(intermediateReader, outputWriter, symSymbol, litAddr);

            // Close resources
            intermediateReader.close();
            symtabReader.close();
            littabReader.close();
            outputWriter.close();
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    private static HashMap<Integer, String> loadSymbolTable(BufferedReader reader) throws IOException {
        HashMap<Integer, String> symSymbol = new HashMap<>();
        String line;
        int symtabPointer = 1;

        // Read each line in symtab and populate symSymbol
        while ((line = reader.readLine()) != null) {
            String[] word = line.split("\t\t\t");
            if (word.length > 1) {
                symSymbol.put(symtabPointer++, word[1]);
            }
        }
        return symSymbol;
    }

    private static HashMap<Integer, String> loadLiteralTable(BufferedReader reader) throws IOException {
        HashMap<Integer, String> litAddr = new HashMap<>();
        String line;
        int littabPointer = 1;

        // Read each line in littab and populate litAddr
        while ((line = reader.readLine()) != null) {
            String[] word = line.split("\t\t");
            if (word.length > 1) {
                litAddr.put(littabPointer++, word[1]);
            }
        }
        return litAddr;
    }

    private static void processIntermediateCode(BufferedReader intermediateReader, FileWriter writer,
                                                HashMap<Integer, String> symSymbol, HashMap<Integer, String> litAddr) throws IOException {
        String line;

        // Process each line in intermediate file
        while ((line = intermediateReader.readLine()) != null) {
            if (line.length() < 6) continue;  // Skip invalid lines

            if (line.substring(1, 6).equalsIgnoreCase("IS,00")) {
                writer.write("+ 00 0 000\n");
            } else if (line.substring(1, 3).equalsIgnoreCase("IS")) {
                processInstructionStatement(line, writer, symSymbol, litAddr);
            } else if (line.substring(1, 6).equalsIgnoreCase("DL,01")) {
                processDeclarativeStatement(line, writer);
            } else {
                writer.write("\n");  // Write empty line for unsupported operations
            }
        }
    }

    private static void processInstructionStatement(String line, FileWriter writer,
                                                    HashMap<Integer, String> symSymbol, HashMap<Integer, String> litAddr) throws IOException {
        writer.write("+ " + line.substring(4, 6) + " ");
        int offset = 0;

        // Check for register specification
        if (line.charAt(9) == ')') {
            writer.write(line.charAt(8) + " ");
            offset = 3;
        } else {
            writer.write("0 ");
        }

        // Check if symbol or literal and write address
        if (line.charAt(8 + offset) == 'S') {
            int symbolIndex = Integer.parseInt(line.substring(10 + offset, line.length() - 1));
            writer.write(symSymbol.getOrDefault(symbolIndex, "000") + "\n");
        } else {
            int literalIndex = Integer.parseInt(line.substring(10 + offset, line.length() - 1));
            writer.write(litAddr.getOrDefault(literalIndex, "000") + "\n");
        }
    }

    private static void processDeclarativeStatement(String line, FileWriter writer) throws IOException {
        String value = line.substring(10, line.length() - 1);
        String paddedValue = String.format("%03d", Integer.parseInt(value));
        writer.write("+ 00 0 " + paddedValue + "\n");
    }
}
