using System;
using System.Text.RegularExpressions;

namespace _1DV610_moment1
{
    class Word {
        string value;

        public Word(string input)
        {
            this.value = input;
        }
    }

    class Dot {
        string value;

        public Dot(string input) {
            if (String.Equals(input, ".")) {
                this.value = input;
            } else {
                throw new ArgumentException("Cannot set " + input + "to class dot. Input value must be a dot of type string.");
            }

        }
    }

    class End {
        bool value;
    }
    class Tokenizer
    {
        public static void words(string input) {
            try
            {
                Regex pattern = new Regex(@"[A-ZÅÄÖa-zåäö]+|[.]",
                RegexOptions.Compiled | RegexOptions.IgnoreCase | RegexOptions.Multiline);
                MatchCollection matches = pattern.Matches(input);
                Console.WriteLine("Matches found: " + matches.Count);

                if (matches.Count > 0) { // If there are matches
                    foreach (Match item in matches)
                    {
                        GroupCollection groups = item.Groups;
                        string output = groups[0].ToString();

                        if (!String.IsNullOrWhiteSpace(output)) { // If string is a word

                            if (String.Equals(output, ".")) {
                                new Dot(output);
                                Console.WriteLine("DOT");
                            } else {
                                new Word(output);
                                Console.WriteLine("WORD: " + output);
                            }   
                        }
                    }

                    Console.WriteLine("END");
                    new End();
                } else {
                    Console.WriteLine("No matches found.");
                }
            }
            catch (System.Exception)
            {
                throw;
            }
        }
        static void Main(string[] args)
        {
            Console.WriteLine("Enter an input for the tokenizer:");
            //var input = Console.ReadLine(); // Get user input

            var input = "Hello world.";
            words(input);
        }
    }
}
