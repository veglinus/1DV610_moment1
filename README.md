# Tokenizer

How to get started:

1. Start a new java project, and add the Tokenizer folder to it. Or clone this entire repo and use the Tester.java file directly. If you're not using the Tester.java file or want to learn the basics of this Tokenizers, continue reading.

2. Create a new .java file, and import the Tokenizer with the following code:
```import Tokenizer.*;```

3. Create a new grammar, for example WordAndDotGrammar:
```Grammar WordAndDotGrammar = new Grammar();```

4. Fill the grammar with rules using new TokenType:
```
WordAndDotGrammar.add(new TokenType("WORD", "[A-ZÅÄÖa-zåäö]+"));
WordAndDotGrammar.add(new TokenType("DOT", "[.]"));
```

5. Create a new Tokenizer object, pass the grammar object as the first parameter and your input as the second parameter:
```Tokenizer tokenizer = new Tokenizer(WordAndDotGrammar, "Hello world.");```

6. Call ```tokenizer.next()``` to go to the next token or use ```tokenizer.back()``` to go back a token.

Examples of the different grammars from the assignment are included in the Tester.java file. Remember that the regex input cannot have trailing or leading "/" slash symbols. Backslashes also need to be typed as two, "\\" instead of "\" in your regex.

The tokenizer will throw an exception if you input something that cannot be recognized by the rules you inserted in the grammar.