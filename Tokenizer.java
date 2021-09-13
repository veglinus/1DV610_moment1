import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Word {
    String value;

    public Word(String input)
    {
        this.value = input;
    }

    public String toString() {
        return "WORD(" + this.value + ")";
    }
}

class End {
    Boolean value;

    public String toString() {
        return "END()";
    }
}

class Dot {
    String value;

    public Dot(String input) {
        if (input.contains(".")) {
            this.value = input;
        } else {
            throw new IllegalArgumentException("Cannot set " + input + "to class dot. Input value must be a dot of type String.");
        }
    }

    public String toString() {
        return "DOT()";
    }
}

class Tokenizer {

    String input = "";

    Tokenizer(String type, String input) {
        this.input = input;

        if (type == "WordAndDotGrammar") {
            WordAndDotGrammar(this.input);
        } else if (type == "ArithmeticGrammar") {
            ArithmeticGrammar(this.input);
        }
    }

    static void WordAndDotGrammar(String input) {
        try
        {
            Pattern pattern = Pattern.compile("[A-ZÅÄÖa-zåäö]+|[.]", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            boolean matches = matcher.find();

            if (matches) {

                do {
                    String output = matcher.group(0);
                    //System.out.println(matcher.group(0));

                    if (!output.contains(" ")) { // If String is a word

                        if (output.contains(".")) {
                            //new Dot(output);
                            System.out.println("DOT");
                        } else {
                            //new Word(output);
                            System.out.println("WORD: " + output);
                        }   
                    }
                } while (matcher.find());
                    
                System.out.println("END");
                
            } else {
                System.out.println("No matches found.");
            }

        }
        catch (Exception e)
        {
            throw e;
        }
    }

    static void ArithmeticGrammar(String input) {
        try {

            Pattern pattern = Pattern.compile("[0-9]+(\\.([0-9])+)?|[*]|[+]");
            Matcher matcher = pattern.matcher(input);
            boolean matches = matcher.find();

            if (matches) {

                do {
                    String output = matcher.group(0);

                    if (output.contains("+")) {         // Addition
                        System.out.println("ADD");
                    } else if (output.contains("*")) {  // Multiplication
                        System.out.println("MUL");
                    } else {                            // Number
                        System.out.println("NUMBER: " + output);
                    }   

                } while (matcher.find());
                    
                System.out.println("END");
                
            } else {
                System.out.println("No matches found.");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    static void MaximalMunchGrammar(String input) {
        Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]");
        Matcher matcher = pattern.matcher(input);
        boolean matches = matcher.find();

        Pattern pattern2 = Pattern.compile("[0-9]+");
        Matcher matcher2 = pattern2.matcher(input);
        boolean matches2 = matcher2.find();


        


    }
}
