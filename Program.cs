using System;
using System.Text.RegularExpressions;

namespace _1DV610_moment1
{
    class Tokenizer
    {
        public static void words(string input) {
            try
            {
                Regex pattern = new Regex(@"[A-Za-z]*",
                RegexOptions.Compiled | RegexOptions.IgnoreCase | RegexOptions.Multiline);
                //Regex dots = new Regex(@"^\.", RegexOptions.Compiled);
                MatchCollection matches = pattern.Matches(input);

                
                Console.WriteLine("Matches found: " + matches.Count);
                if (matches.Count > 0) {
                    foreach (Match item in matches)
                    {
                        GroupCollection groups = item.Groups;
                        string output = groups[0].ToString();

                        if (!String.IsNullOrWhiteSpace(output)) { // If string is a word
                            Console.WriteLine(output);
                        }
                        
                    }
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

            var input = "Hello world hello!";
            words(input);
        }
    }
}
