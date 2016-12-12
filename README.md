# Bricks
Program napisany w ramach laboratoriów z Algortymów i Struktur Danych, jako program sędziujący grę, będącą tematem przewodnim
drugiej połowy semestru tegoż przedmiotu.
##Zasady
Na planszy n x n pól, gdzie n jest naturalną nieparzystą liczbą z zakresu od 5 do 255 gracze stawiają klocki w rozmiarach 2 x 1
lub 1x2 pól. Wygrywa gracz który jako ostatni będzie miał możliwość wykonania ruchu. Algortymy grająca mają 1 sekundę czasu na
wykonanie ruchu. Przekroczenie tego czasu, lub wykonanie błędnego ruchu powoduje przegraną danego programu.
##Protokół Komunikacji
s - sędzia  
p1 - program pierwszy  
p2 - program drugi     
1. Ping (s) -> (p1)   
2. Pong (p1) -> (s)  
3. Ping (s) -> (p2)  
4. Pong (p2) -> (s)  
5. rozmiar planszy (przykład: "5") (s) -> (p1)  
6. rozmiar planszy (przykład: "5") (s) -> (p2)  
7. "Zaczynaj" (s) -> (p1)  
8. Ruch (przykład: 0 0 1 0) (p1) - > (s)  
9. Poprzedni ruch (przykład: 0 0 1 0) (s) -> (p2)
10. Ruch (p2) - > (s)  
11. Poprzedni ruch (s) - > (p1)  
12. Powrót do punktu 8  
Programy grające nie otrzymują żadnego sygnału końca, uruchamia je i zamyka program sędziujący  
Dozwolone języki programowania: Każdy, obsługujący standardowe wejście i wyjście (w ustawieniach programu sędziującego można 
podać mu instrukcję jak uruchomić program o danym rozszerzeniu).
##Przyszłe zmiany
Możliwość masowego uruchamiania gier (użytkownik podaje ile gier chce uruchomić, podaje rozmiary plansz). Program wyświetla ile gier wygrał który program. (Skrócony opis, opcji będzie nieco więcej).
