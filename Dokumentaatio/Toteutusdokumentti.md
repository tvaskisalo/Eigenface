## Toteutusdokumentti

Ohjelma koostuu kahdesta osasta: ui ja logic.

Osassa ui on kaikki käyttöliittymän koodi, jossa jokaiselle näkymälle on oma luokkansa

Osassa logic on kaikki ohjelman logiikka, sekä hieman ui:n kanssa kommunikoivaa koodia.

Ohjelma ottaa harjoitusdatakseen kuvia. Kuvat muutetaan matriiseiksi ja sitten riveittäin vektoreiksi. 
Näistä vektoreista vähennetään keskiarvo vektori kuvien väliltä ja sitten ne asetetaan matriisin siten, että matriisin sarakkeet ovat kyseiset vektorit.
Sitten lasketaan eräällä kikalla matriisin kovarianssimatriisin ominaisarvot ja -vektorit. 
Näiden avulla voidaan tunnistaa syötteenä annetuista kuvista, että onko niissä kasvot vai ei

### Aika- ja tilavaatimus
#### Eigenface generointi

Olkoon n kuvien määrä ja m kuvan kannan koko. 
Oletetaan, että nämä ovat positiivisia kokonaislukuja

Ohjelma tekee seuraavat asiat järjestyksessä:

##### Kuvien prosessointi:
Kuvien prosessoinnissa ohjelma käy läpi kaikki kuvat kansiossa ja muuttaa ne kooksi m x m ja mustavalkoiseksi.
En tiedä kuinka kauan Image-luokan oma metodi vie, mutta tämä todennäikösesti tapahtuu ajassa O(m^2).
Täten kuvien prosessointiin kestää O(nm^2)

##### Kuvien muuttaminen matriiseiksi
Ohjelma käy kaikkien kuvien pikselit läpi ja asettaa ne matriiseihin, joiden koko on m x m, joten aikaa kuluu O(nm^2)

##### Matriisien sijoittaminen riveittäin isoksi matriisiksi
Ohjelma käy kaikkien matriisien alkiot läpi ja asettaa ne matriisiksi, joten aikaa kuluu O(nm^2)

##### Matriisin rivikeskiarvon laskeminen
Ohjelma käy ison matriisin läpi ja laskee keskiarvon riveittäin. Koska ison matriisin koko on m^2 x n, eli aikaa kuluu O(nm^2)

##### Rivikeskiarvon vähentäminen matriisista.
Ohjelma käy ison matriisin läpi ja vähentää keskiarvon riveittäin. Metodi käy siis läpi ison matriisin ja keskiarvo-vektorin, jonka koko on m^2 x 1.
Aikaa menee siis O(nm^2 + m^2), eli O(nm^2), sillä nm^2>m^2.

##### Ison matriisin transpoosin ja matriisin tulo
Ison matriisin transponoinnissa käydään kaikki ison matriisin alkiot läpi, joten aikaa menee O(nm^2)
Matriisi kertolaskussa käydään kaikki transpoosin rivit, joita on n ja kaikki ison matriisin sarakkeet, joita on n ja kaikki rivit, joita on m^2
Näin ollen tuloon menee aikaa O(n^2 m^2), eli yhteensä O(n^2 m^2 + nm^2). Sillä n^2 m^2 > nm^2, niin aikavaativuus on O(n^2 m^2).
Kutsutaan tätä tulomatriisia sisämatriisiksi.

Dokumentti on kesken


### Puutteet ja parnnukset

Ohjelmaa voidaan optimoida vielä paljon. Lisään tarkempaa tietoa myöhemmin.

### Lähteet:

[Manfred Zabarauskas](http://blog.manfredas.com/eigenfaces-tutorial/)

Lähteitä on enemmänkin, mutta en ole ehtinyt niitä laittamaan vielä
