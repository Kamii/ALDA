Detta �r en f�rsta version av filerna. den grundl�ggande funktionaliteten �r p� plats liksom alla sorteringsalgoritmer av intresse.
Buggfixar, uppsnyggning och ut�kningar kan f�rekomma, men det b�r inte vara n�got som p�verkar uppgiften i n�gon st�rre grad.

SortingAlgorithmsTester kan verka skr�mmande, men normalt beh�ver man inte bry sig om n�got f�rutom de konstanter som st�r �verst i klassen. 

Det huvudsakliga utdata fr�n programmet �r en fil som inneh�ller information om samtliga test som genomf�rts. Dessa �r organiserade i rader p� f�ljande format:

InsertionSorterIntegerGenerator	LinkedListCreator	RandomList	FewDuplicatesList	100	0.342861

Den f�rsta kolumnen ber�ttar vilken sorteringsalgoritm som anv�nts f�r testet, insertion sort i detta fall
Den andra kolumnen ber�ttar vilken typ av datastruktur som sorterats, en LinkedList i detta fall
Den tredje kolumnen ber�ttar hur listan var organiserad fr�n b�rjan. Alternativen �r slumpm�ssig (som ovan), sorterad, eller omv�nt sorterad
Den fj�rde kolumnen ber�ttar hur m�nga dubletter som finns i listan: inga, n�gra f�, eller m�nga
Den femte kolumnen ber�ttar hur m�nga v�rden som fanns i listan i just detta test
Den sj�tte kolumnen ger tiden f�r testet i sekunder. Om ett test inte k�rdes blir detta v�rde -1.0