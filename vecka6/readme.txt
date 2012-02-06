Detta är en första version av filerna. den grundläggande funktionaliteten är på plats liksom alla sorteringsalgoritmer av intresse.
Buggfixar, uppsnyggning och utökningar kan förekomma, men det bör inte vara något som påverkar uppgiften i någon större grad.

SortingAlgorithmsTester kan verka skrämmande, men normalt behöver man inte bry sig om något förutom de konstanter som står överst i klassen. 

Det huvudsakliga utdata från programmet är en fil som innehåller information om samtliga test som genomförts. Dessa är organiserade i rader på följande format:

InsertionSorterIntegerGenerator	LinkedListCreator	RandomList	FewDuplicatesList	100	0.342861

Den första kolumnen berättar vilken sorteringsalgoritm som använts för testet, insertion sort i detta fall
Den andra kolumnen berättar vilken typ av datastruktur som sorterats, en LinkedList i detta fall
Den tredje kolumnen berättar hur listan var organiserad från början. Alternativen är slumpmässig (som ovan), sorterad, eller omvänt sorterad
Den fjärde kolumnen berättar hur många dubletter som finns i listan: inga, några få, eller många
Den femte kolumnen berättar hur många värden som fanns i listan i just detta test
Den sjätte kolumnen ger tiden för testet i sekunder. Om ett test inte kördes blir detta värde -1.0