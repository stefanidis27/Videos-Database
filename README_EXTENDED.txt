In pachetul fileio:
- in clasa ActorInputData am adaugat trei campuri in care retin numerele
cerute la query dupa media tuturor filmelor in care a jucat un actor,
dupa numarul de premii ale acestuia si dupa numarul de cuvinte gasite in
descriere.
- in clasa MovieInputData am adaugat doua campuri, anume o lista double
cu ratingurile date filmului si o lista de String-uri cu numele urilizatorilor
care au dat rating.
- in clasa SerialInputData am adaugat un camp, anume numarul care reprezinta
durata totala a unui serial, adica suma duratelor sezoanelor
- in clasa ShowInput, clasa parinte pentru cele doua de mai sus, am adaugat
trei campuri, unul pentru numarul total de vizualizari al showului, unul
pentru ratingul total al showului si unul pentru numarul total de adaugari
in lista de favorite al unui user.
- in clasa UserInputData am adaugat doua campuri, unul pentru numarul total de
ratinguri oferite de utlizator si unul pentru o lista de filme favorite
retinute ca String-uri intr-o lista.
 
In pachetul main, clasa Main, metoda action: am realizat parcurgerea
tututror actiunilor din fisierul de intrare. Pentru fiecare comanda am
verificat tipul acesteia si am apelat metoda specifica. Metodele specifice
fiecarei actiuni au fost implementate in cate o clasa fiecare. Aceste clase
sunt descrise mai jos, toate au specificatorii public final, contin o singura
metoda cu specificatorii public static ce intoarce void si contin un
constructor private.

In pachetul commands am implementat clasele specifice comenzilor:
- clasa ViewCommand: are o singura metoda command care verifica daca
in UserInputData a fost vazut filmul/serialul cu numele specificat
in actiune. Daca filmul/serialul a fost vazut atunci metoda mareste numarul de
vizualizari ale userului, iar daca nu a fost vizualizat, metoda adauga
filmul/serialul respectiv in baza de date a userului, setand numarul de
vizulaizari la 1.

- clasa FavoriteCommand: are o singura metoda command care verifica daca un
film/serial se afla in lista de show-uri vazute sau in lista de show-uri
favorite din UserINnputData. In acest caz, metoda scrie un mesaj de eroare.
In caz contrar, metoda adauga filmul/serialul in lista de show-uri favorite a
userului.

- clasa RatingCommnad: are o singura metoda command care adauga un rating unui
sezon/film punand in MovieInputData/Season atat noul rating, cat
si numele utilizatorului. Totodata, metoda creste in baza de date a userului
numarul de ratinguri date, anume campul givenRatings din UserInputData.

In pachetul queriesactrors am implementat clasele specifice query-urilor
pentru actori:
- clasa QueryActorsAverage: are o singura metoda queryActors care ia toate
ratingurile din MovieInputData si Season, face media pe film/serial si media
pe toate filmele si serialele si o aduga in campul average din ActorInputData.
Metoda sorteaza lista de actori din Input (o copie a ei) si scrie in fisier
actorul cerut. Sortarea se face folosind un comparator implementat in clasele
SortActorsByAverageAsc si SortActorsByAverageDesc.

- clasa QueryActorsAwards: are o singura metoda queryActors care trece prin
baza de date din Input, aduna pentru fiecare actor numarul de premii
obtinute si face o suma ce reprezinta numarului total de premii obtinute pe
care o pune in ActorInputData, in campul queryAwards. Daca actorul respectiv
nu are premiile date in actiune, atunci campul queryAwards se reseteaza la 0.
Metoda sorteaza lista de actori din Input (o copie a ei) si scrie actorul
cerut. Metoda sorteaza lista de actori din Input (o copie a ei) si scrie
actorul cerut. Sortarea se face folosind un comparator implementat in clasele
SortActorsByAwardsAsc si SortActorsByAwardseDesc.

- clasa QueryActorsAwards: are o singura metoda queryActors care trece prin
baza de date a fiecarui actor din Input si cauta in descriere lor fiecare
cuvant dat in actiune si pune numarul de cuvinte gasite in campul queryWords
din ActorInputData. Daca nu s-au gasit toate cuvintele din actiune, se
reseteaza campul queryWords. Metoda sorteaza lista de actori din Input (o copie
a ei) si scrie actorul cerut. Sortarea se face folosind un comparator
implementat in clasele SortActorsByNameAsc si SortActorsByNameDesc.

In pachetele queriesmovies si queries actors am implementat clasele specifice
query-urilor pentru file/seriale. Clasele au functionalitati asemanatoare, asa
ca le voi scrie impreuna:
- clasa QueryMoviesDuration/QuerySerialsDuration: contine o singura metoda
queryMovies/querySerials care sorteaza filmele/serialele (o copie a listei de
file/seriale) dupa durata totala dupa campul de durata aflat in MovieInputData
/SerialInputData (singura diferenta la seriale e ca am calculat mai intai
durata totala adunand durata din fiecare clasa Season si am pus-o in clasa
SerialInputData). Sortarea se face folosind un comparator implementat in
clasele SortMovies(Serials)ByLongestAsc si SortMovies(Serials)ByLongestDesc.
Metoda scrie filmul/serialul cerut dupa aplicarea filtrelor de an si gen.

- clasa QueryMoviesFavorite/QuerySerialsFavorite: contine o singura metoda
queryMovies/querySerials care parcurge toti utilizatorii si adauga un 1 la
campul totalFavorite din ShowInput daca in UserInputData se afla acel
film/serial in lista de filme/seriale favorite. Metoda aplica filtrele de
an si gen si sorteaza o copie a listei de filme/seriale si scrie
filmul/serialul cerut. Sortarea se face folosind un comparator implementat in
clasele SortMovies(Serials)ByFavoritesAsc si
SortMovies(Serials)ByFavoritesDesc.

- clasa QueryMoviesViews/QuerySerialsViews: contine o singura metoda
queryMovies/querySerials care parcurge toti utilizatorii si adauga un 1 la
campul totalViews din ShowInput daca in UserInputData se afla acel
film/serial in lista de filme/seriale vazute. Metoda aplica filtrele de
an si gen si sorteaza o copie a listei de filme/seriale si scrie
filmul/serialul cerut. Sortarea se face folosind un comparator implementat in
clasele SortMovies(Serials)ByViewsAsc si SortMovies(Serials)ByViewsDesc.

- clasa QueryMoviesRating/QuerySerialsViews: contine o singura metoda
queryMovies/querySerials care calculeaza ratingul total pentru film/serial
facand media ratingurilor din lista de ratinguri din MovieInputData/Season (la
seriale se face media pe sezon si apoi iar pe tot serialul). Ratingul total
este pus in campul totalRating din ShowInput. Metoda sorteaza apoi o copie
a listei de filme/seriale. Sortarea se face folosind un comparator
implementat in clasele SortMovies(Serials)ByRatingAsc si
SortMovies(Serials)ByRatingDesc.

In pachetul queriesusers este implementat query-ul pentru utilizatori:
- clasa QueryUsers: contine o singura metoda queryUsers care sorteaza o copie a
listei de utilizatori. Sortarea se face in functie de campul givenRatins din
UserInputData folosind un comparator implementat in clasele
SortUsersByGivenRatingsAsc si SortUsersByGivenRatingsDesc. Metoda scrie in
fisier utilizatorul cautat.

In pachetul recommendationsall sunt implementate recomandarile pentru toti
utilizatorii:
- clasa RecommendStandard: contine o singura metoda recommendAll care cauta
prin baza de date Input si gaseste primul serial/film nevazut de user, adica
care nu se afla in istoricul din UserInputData.
- clasa RecommendBestUnseen: contine o singura metoda recommendAll care
calculeaza ratingul total pentru fiecare film si sezon (facand media
ratingurulor din MovieInputData si Season) si il pune in campul totalRating
din ShowInput. Metoda sorteaza toate show-urile dupa rating descrescator si
scrie show-ul cerut. Sortarea se face folosind un comparator implementat in
clasa SortShowsByRatingNoNameDesc.

In pachetul recommendationspremium sunt implementate recomandarile pentru
utilizatorii premium:
- clasa RecommendFavorite: contine o singura metoda recommendPremium care
caluleaza pentru fiecare show (pune in campul totalFavorite din ShowInput) de
cate ori a fost trecut in lista de favorite ale unui utilizator, trecand prin
toti utilizatorii si verificand lista de favorite din UserInputData. Metoda
sorteaza toate show-urile dupa totalFavorite descrescator si scrie show-ul
cerut. Sortarea se face folosind un comparator implementat in clasa
SortShowsByFavoriteNoNameDesc.
- clasa RecommendPopular: contine o singura metoda recommendPremium care
construieste un map cu numele tuturor genurilor si numarul total de
vizualizari ale tuturor show-urilor care sunt de genul respectiv. Map-ul este
sortat cu metoda sortByValue din clasa SortHashMap implementata in pachetul
sort. Metoda apoi ia genurile sortate pe rand si cauta in Input primul show
nevazut de acel gen.
- clasa RecommendSearch: contine o singura metoda recommendPremium care
calculeza ca mai sus numarul total de rating pentru fiecare show si sorteaza
show-urile dupa campul totalRating. Metoda scrie apoi show-urile care contin
gen dat in actiune. Sortarea se face folosind un comparator implementat in
clasa SortShowsByRatingAsc. 

In pachetul sort sunt implementati toti comparatorii mentionati mai sus
impreuna cu clasa SortHashMap mentionata si ea mai sus.

In pachetul utils, in clasa Utils am lasat totul la fel, dar am adaugat o
metoda genreNameToString care transforma un String cu denumirea unui gen asa
cum se afla in enumul Genre, pachetul enttertainment, intr-un String cu
denumirea genului asa cum se afla in fisierele de input. Aceasta metoda este
apelata in clasa RecommendPopular.
