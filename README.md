# Bricks
Program napisany w ramach laboratoriów z Algortymów i Struktur Danych, jako program sędziujący grę, będącą tematem przewodnim
drugiej połowy semestru tegoż przedmiotu. Do uruchomienia programu wymagana jest java se 8 runtime environment.
##Zasady
Na planszy n x n pól, gdzie n jest naturalną nieparzystą liczbą z zakresu od 5 do 255 gracze stawiają klocki w rozmiarach 2 x 1
lub 1x2 pól. Wygrywa gracz który jako ostatni będzie miał możliwość wykonania ruchu. Algortymy grająca mają 1 sekundę czasu na
wykonanie ruchu. Przekroczenie tego czasu, lub wykonanie błędnego ruchu powoduje przegraną danego programu.
##Protokół Komunikacji  
Uwaga: Wszelki wysyłany tekst pisany jest dużymi literami, a numeracja pól planszy mieści się w zakresie od 1 do n, a nie od 0 do n-1.  
s - sędzia  
p1 - program pierwszy  
p2 - program drugi     
1. PING (s) -> (p1)   
2. PONG (p1) -> (s)  
3. PING (s) -> (p2)  
4. PONG (p2) -> (s)  
5. rozmiar planszy (przykład: "5") (s) -> (p1)  
6. rozmiar planszy (przykład: "5") (s) -> (p2)  
7. "ZACZYNAJ" (s) -> (p1)  
8. Ruch (przykład: 1 1 1 2) (p1) - > (s)  
9. Poprzedni ruch (przykład: 1 1 1 2) (s) -> (p2)  
10. Ruch (p2) - > (s)  
11. Poprzedni ruch (s) - > (p1)  
12. Powrót do punktu 8  
13. WYGRALES (s) -> zwyciezca  
14. PRZEGRALES (s) -> przegrany  
Dozwolone języki programowania: Każdy, obsługujący standardowe wejście i wyjście (w ustawieniach programu sędziującego można 
podać mu instrukcję jak uruchomić program o danym rozszerzeniu).
#### Uwaga  
Wysyłanie tekstu przez programy grające musi odbywać się "jednym ciągiem", przez komendę typu "println" (ze znakiem nowej linii na końcu). Wysyłanie kolejnych liczb po jednej, nawet jeśli wszytkie zmieszczą się w terminie jednej sekundy i nawet, jeśli dodawmy na końcu samodzielnie znak nowej linii, uznane będzie za niezgodne z protokołem i program sędziujący uzna to za ruch niepoprawny.
##Zmiany (od wersji pierwotenej)  
-Naprawa kilkunastu błędów (w tym już niemal całkowita poprawa w kwestiach kompatybilności z protokołem)  
-Dodanie partii automatycznych (rozgrywek), wraz z zapisywaniem ich wyników  
-Zmiana wyglądu programu, przepisanie do javyfx, dodanie alternatywnego stylu graficznego programu
-Dodanie możliwości wykonywania automatycznych gier pomiędzy definiowanymi programami
-Dodanie możliwości prezentowania wyników w tabelach i na wykresach
