# Mall för inlämning laboration 1, 1dv610

## Checklista
  - [x] I min tokeniserare finns inga tokentyper eller reg-exp. Dessa finns i mitt testprojekt eftersom de skapas utav användaren.
  - [x] Jag har skrivit all kod och reflektioner själv. Jag har inte använt mig av andras kod för att lösa uppgiften.
  - [x] Mina testresultat är skrivna utifrån utförd testning ( och inte teoretiskt, det bör fungera :) )
  - [x] De enda statiska metoder eller funktioner utanför klasser som jag har är för att starta upp min testapplikation ex main(java).
  - [x] De enda bibliotek och färdiga klasser som används är sådana som måste användas (eller som används för att testa modulen).

## Egenskattning och mål
  - [ ] Jag är inte klar eftersom jag vet att jag saknar något. Då skall du inte lämna in!
  - [x] Jag eftersträvar med denna inlämning godkänt betyg (E-D)
    - [x] De flesta testfall fungerar
    - [x] Koden är förberedd på Återanvändning
    - [x] All kod samt historik finns i git 
    - [x] Kodkvaliterskraven är ifyllda
    - [x] Reflektion är skriven
  - [ ] Jag eftersträvar med denna inlämning högre betyg (C-B) och anser mig uppfylla alla extra krav för detta. 
    - [ ] Samtliga testfall är skrivna
    - [ ] Egna testfall för Maximal munch och kantfall
    - [ ] Testfall är automatiserade
    - [ ] Det finns en tydlig beskrivning i hur modulen skall användas (i git)
    - [ ] Kodkvalitetskraven är varierade 
  - [ ] Jag eftersträvar med denna inlämning högsta betyg (A) 

Förtydligande: Examinator kommer sätta betyg oberoende på vad ni anser. 

## Återanvändning
Instruktioner finns i README.md. Jag har försökt dela upp allt i mindre funktioner än vad jag brukar, för att följa regeln att funktioner ska göra en sak väl.

## Beskrivning av min kod
Beskriv din kod på en hög abstraktionsnivå. En kort beskrivning av dina viktigaste klasser och metoder. Skapa gärna ett klassdiagram som bild. 

Token - En typ och ett värde. 2 strängar.
TokenType - användarens egenvalda grammatik, alltså till exempel "WORD", och Regex för den.
Grammar - Fyller man med TokenTypes.
Tokenizer - Ger man sin grammar och en input sträng.

Tokenizern "går vidare" genom funktionen next, vars jobb är att kolla om man är i slutet av inputen eller inte. Om inte, så går vi vidare till tokenize().

Tokenize är huvudfunktionen. Den använder sig av grammar och input. Man går igenom input strängen för varje regel i en for-loop, och lägger till matchningar i ArrayList matches. Matchningen sker genom funktionen regexMatch, som tar regelnamn, regel och input och applicerar regexregeln mot input.

Om det bara finns en matchning i ArrayList matches returnerar vi tokenen.

Annars om vi hittar flera matchningar, så ska vi jämföra tokens i compareTokens. Vi lägger till occurances i listan indexlist och matchingTokens, för att hitta den första förekomsten av en regel. Vi vill ta en token i taget, alltså hitta den första.

Vi kollar sedan om flera regler förekommer på samma plats, till exempel om vi har input "3.14" och regler INTEGER samt FLOAT med hjälp av checkDuplicateValues. Om det förekommer, så kör vi funktionen maximalMunch, som hittar det "längsta" resultatet.

Sist men inte minst så tar vi bort vad vi hittade från input strängen och lägger till tokenen i vår ArrayList tokens, så vi senare kan gå tillbaks till den om användaren vill genom funktionen back(). Detta händer alltid.


## Hur jag testat

Jag har kört igenom testfall TC1-16 med hjälp av den regex som står i uppgiften.

### Testfall
Lista de enskilda testfallen. **Fetmarkera** sådant som du själv fyllt i. En rad per testfall.

|Namn| Grammatik         | Sträng                | Sekvens | Förväntat Aktivt Token | PASS/FAIL |
|----|-------------------|-----------------------|---------|---------------|-----|
|TC1 | WordAndDotGrammar | "a"                   | []      | WORD("a") | PASS |
|TC2 | WordAndDotGrammar | "a aa"                | [>]     | WORD("aa") | PASS |
|TC3 | WordAndDotGrammar | "a.b"                 | [>]     | DOT(".") | PASS |
|TC4 | WordAndDotGrammar | "a.b"                 | [>>]    | **WORD("b")** | PASS |
|TC5 | WordAndDotGrammar | "aa. b"               | **[>>]**| WORD("b") | PASS |
|TC6 | WordAndDotGrammar | "a .b"                | [>><]   | DOT(".") | PASS |
|TC7 | WordAndDotGrammar | ""                    | []      | END | PASS |
|TC8 | WordAndDotGrammar | " "                   | []      | END | PASS |
|TC9 | WordAndDotGrammar | "a"                   | **[>]** | END | PASS |
|TC10| WordAndDotGrammar | "a"                   | [<]     | **Exception** | PASS |
|TC11| WordAndDotGrammar | "!"                   | []      | Exception | PASS |
|TC12| ArithmeticGrammar | "3"                   | []      | NUMBER("3") | PASS |
|TC13| ArithmeticGrammar | "3.14"                | []      | NUMBER("3.14") | PASS |
|TC14| ArithmeticGrammar | "3 + 54 * 4"          | [>>>]   | MUL("*") | PASS |
|TC15| ArithmeticGrammar | "3+5 # 4"             | [>>>]   | **Exception** | PASS |
|TC16| ArithmeticGrammar | "3.0+54.1     + 4.2"  | [><>>>] | **ADD(+)** | PASS |

## Kodkvalitetskrav

### Namngivning

**Use Intention Revealing Names**
Funktioner som checkForEndofInput, som kollar om inmatningssträngen är slut. Lång, men förklara väl i flödet vad det är vi kollar efter. Om den returnerar sant så går vi även till en funktion som kollar efter end token, som heter handleEndToken.

I tokenize använder jag även funktionnamn som regexMatch och listan av matchningar som vi lägger till matchningar i heter rakt av matches. Då blir det tydligt vad vi gör när vi använder saker som matches.size() och matches.get(0). 

**Use Pronounceable Names**
Jag tror att nästan alla variabler och funktioner går att uttala, vilket är viktigt för mig.

**Use Searchable Names**
Svår att uppfylla, vissa namn är lite sämre så som tokens och matches, men sedan finns det bra namn så som lastToken, activeToken med flera. 

**Method names**
Allt har verb förutom maximalMunch, funktionerna börjar med ord så som remove, check, compare osv. De publika funktionerna är ju next och back, vilket är lite på gränsen, men utifrån kontexten tokenizer.next så känns det ändå logiskt.

### Funktioner

tokenize - **Do One Thing** är ju denna funktionen lite dålig på, eftersom det är den största och det är mycket som ska hanteras. Det är ju egentligen typ som en main() funktion. Men man tokenizerar ju, så den är ju sann till sitt namn som **Method Names** ur CC säger.

compareTokens - **Do One Thing** den ljuger lite, eftersom att den även callar removeFromInput och lägger till tokenen i Tokens. Den gör det den ska, jämför tokens ja, men även lite andra dolda saker för att det är ett "lätt" ställe att ha allt på. **Avoid Mental Mapping** känns bra här, eftersom att jag försöker döpa allt på ett sätt att man ska ändå försöka hänga med, och inte behöva hålla koll på massa saker.

handleEndToken - Printar även end token vilket är vilseledande. Men den hanterar end-tokens, och utifrån vart funktionen callas så fattar man vilket syfte den har.
matchRegex - Gör enbart en sak. Dock tar den hela 3 argument, vilket kanske inte hade behövts. 

maximalMunch - Tar en lista av tokens och returnerar rätt token utifrån regeln maximal munch. **Do one Thing** och **Funciton Argument** eftersom den bara tar ett argument. Dock kanske användaren inte vet vad maximal munch är.

Allt i sig är ganska dåligt när det gäller **Use Searchable Names**, eftersom att det är mycket samma ord som används. Match, matches, token, tokens, tokentype tokenizer, osv. Samma prefix liksom. **Don't be cute** känner jag ändå att jag följt rätt bra.

## Laborationsreflektion

Att jobba med hjälp av bokens råd och regler har varit generellt ganska jobbigt. I grunden så har jag lärt mig på ett sätt hur jag ska göra och det handar oftast om att snabbt komma till ett resultat, sedan skita i hur fult eller fint det resultatet är. If it works, don't break it liksom. När jag sitter och programmerar så är det lätt för mig att förlora mitt flow om jag måste sitta med småpill så som "vad ska jag döpa detta till?". 

Därför känner jag att för att utvecklas inom detta området får jag snarare skriva min kod, sedan kritisera den i efterhand och försöka göra den bättre. Det som jag åtminstonde känner är att koncepten i boken går väldigt hand i hand med att skriva saker väldigt objektorienterade, vilket jag är generellt rätt ovan vid. Men nu när jag har jobbat mycket med objektorienterat(sen starten av terminen) ser jag att det är lättare att applya logiken.

Något jag starkt gillar ur boken är att saker ska helst bara göra en sak och vara bra på bara den saken, saker ska helst bara ha en funktion. När något då inte fungerar så kan man gå till exakt en funktion som inte agerar rätt, snarare än en kodrad i en 100 rader lång kodsnutt. Det kände jag direkt att "aha, men det är ju logiskt" och såg hur det kunde förbättra min kodning, så det är något jag redan har försökt jobba med så mycket det går.

Under tiden jag skrev denna filen så har jag nog insett de största problemen med koden, och det har blivit ett bra tillfälle att reflektera, så jag har gjort vissa förändringar:
checkForEnd är nu checkForEndofInput, längre men mer beskrivande. Behövs då ingen kommentar.
endOfInput som hanterade End token heter nu handleEndToken. För funktionen HANTERAR end tokenen, kollar om den är där osv.
Tokenize som är huvudfunktionen i hela tokenizern hette från början resolve, vilket bara var ett snabbt "kladdnamn" jag skapade. Den heter nu bara tokenize, för det är ju vad den gör. Den tokenizerar.
Sen har det skett flera andra förändringar jag inte skrivit med här, som finns med i mina commits.

I tokenize hade jag kunnat dra ut flera funktioner, som att göra listan av matches hade kunnat vara en egen funktion. I compareTokens skulle man kunna göra samma sak med for-loopen, och att logiken i sig är en egen funktion. handleEndToken hade kunnat vara snyggare, den är lite av en mess som bara blev för att jag fick en bug jag ville lösa asap.

Denna uppgiften har ju verkligen gett perspektiv på vilka vanor man har och vad som man hade kunnat förbättra. Jag tror egentligen att jag aldrig har tänkt "hur gör jag snyggare kod", och heller inte vad som är "bra kod". Men nu kan jag börja besvara dessa frågor lite, en bra kod för mig är beskrivande och säger vad den gör. I en bra kod går det att följa flödet och hänge med på varje rad. Jag skriver rätt ofta pseudokod om jag har svårt att hantera ett koncept, och koden jag nu skriver vill jag ska kännas som pseudokoden jag brukar skriva, alltså väldigt förståelig helt enkelt.

