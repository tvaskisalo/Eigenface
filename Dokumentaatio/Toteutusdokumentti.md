# Toteutusdokumentti

Ohjelma koostuu kolmesta osasta: ui, dao ja logic.

Osassa ui on kaikki käyttöliittymän koodi, jossa jokaiselle näkymälle on oma luokkansa

Osassa logic on kaikki ohjelman logiikka, sekä hieman ui:n kanssa kommunikoivaa koodia.

Osassa dao on koodi matriisin kirjoittamiseen ja lukemiseen .csv-tiedostosta.

Ohjelma ottaa harjoitusdatakseen kuvia. Kuvat muutetaan matriiseiksi ja sitten riveittäin vektoreiksi. 
Näistä vektoreista vähennetään keskiarvovektori kuvien väliltä ja sitten ne asetetaan matriisin siten, että matriisin sarakkeet ovat kyseiset vektorit.
Sitten lasketaan eräällä kikalla matriisin kovarianssimatriisin ominaisarvot ja -vektorit. 
Näiden avulla voidaan tunnistaa syötteenä annetuista kuvista, että onko niissä kasvot vai ei.
Kasvojentunnistuksessa annettu kuva muutetaan matriisiksi ja taas riveittän vektoriksi. Sitten vektorista vähennetään yllä mainittu keskiarvovektori.
Tämän jälkeen kerrotaan vektori kovarianssimatriisin ominaisvektreilla, jotta saadaan jokaisen ominaisvektorin paino.
Sitten painoilla kerrotaan ominaisvektorit ja ne summataan yhteen.
Nyt vähennetään saatu vektori alkuperäisestä vektorista, josta on vähennettu keskiarvovektori.
Tämän vektorin pituutta verrataan kynnysarvoon, jolla määritellään, onko kuvassa kasvot vai ei.

## Aika- ja tilavaatimus
### Eigenface generointi

Olkoon n kuvien määrä ja m kuvan kannan koko. 
Oletetaan, että nämä ovat positiivisia kokonaislukuja

Ohjelma tekee seuraavat asiat järjestyksessä:

#### 1. Kuvien prosessointi:
Kuvien prosessoinnissa ohjelma käy läpi kaikki kuvat kansiossa ja muuttaa ne kooksi m x m ja mustavalkoiseksi.
En tiedä kuinka kauan Image-luokan oma metodi vie, mutta empiirisen testauksen perusteella kauan. En ota tätä huomioon laskelmissa, sillä en tiedä tarkkaa aikavaativuutta.
Täten kuvien prosessointiin kestää O(nm^2)

#### 2. Kuvien muuttaminen matriiseiksi
Ohjelma käy kaikkien kuvien pikselit läpi ja asettaa ne matriiseihin, joiden koko on m x m, joten aikaa kuluu O(nm^2)

#### 3. Matriisien sijoittaminen riveittäin isoksi matriisiksi
Ohjelma käy kaikkien matriisien alkiot läpi ja asettaa ne matriisiksi, joten aikaa kuluu O(nm^2)

#### 4. Matriisin rivikeskiarvon laskeminen
Ohjelma käy ison matriisin läpi ja laskee keskiarvon riveittäin. Koska ison matriisin koko on m^2 x n, eli aikaa kuluu O(nm^2)

#### 5. Rivikeskiarvon vähentäminen matriisista.
Ohjelma käy ison matriisin läpi ja vähentää keskiarvon riveittäin. Metodi käy siis läpi ison matriisin ja keskiarvo-vektorin, jonka koko on m^2 x 1.
Aikaa menee siis O(nm^2 + m^2), eli O(nm^2), sillä nm^2>m^2.

#### 6. Ison matriisin transpoosin ja matriisin tulo
Ison matriisin transponoinnissa käydään kaikki ison matriisin alkiot läpi, joten aikaa menee O(nm^2)
Kertolaskussa, kerrotaan oikean puoleisen matriisin sarakkeita ensimmäisella matriisilla. Ensimmäisen matriisin ja vektorin läpikäyntiin menee aikaa O(m^2n) ja se tehdään n kertaa. Aikaa siis kuluu O(m^2n^2)
Näin ollen tuloon menee aikaa O(n^2 m^2), eli yhteensä O(n^2 m^2 + nm^2). Sillä n^2 m^2 > nm^2, niin aikavaativuus on O(n^2 m^2).
Kutsutaan tätä tulomatriisia sisämatriisiksi, jonka dimensiot on n x n.

#### 7. Sisämatriisin ominaisparit
Nyt lasketaan sisämatriisin ensimmäiset n ominaisparia. Jokaisessa ominaisparin laskennassa käytetään niin sanottua power-iterointia, joka löytää aina ominaisparin, jonka ominaisarvon itseisarvo on suurin. 
Power-iteraatio alkaa kertomalla ykkösvektori, jonka pituus on n. Vektori normalisoidaan, jossa käydään kahdesti vektori läpi, joten aikavaativuus siihen on O(n).
Nyt kerrotaan normalisoitu vektori sisämatriisilla, johon kuluu aikaa O(n^2). Tässä myös tarkistetaaan, kuinka paljon vektori on muuttunut. Siinä vähennetään saatu vektori edellisen iteraation vektorilla ja tutkitaan sen pituutta. Vähennyksessä käydään molemmat vektori saman aikaisesti  läpi, kuin myös pituuden laskemisessa. Aikaa siis menee O(n)
Nyt algoritmit taas normalisoi vektorin ja tekee saman toiston, kunnes saadun vektorin ja edellisen vektorin erotuksen pituus on 0.00001 tai kun iteraatioita on tehty 1000.
Eli aikaa pahimmassa tapauksessa kuluu 1000*(4O(n) + O(n^2), jotta saadaan ensimmäinen ominaisvektori.
Tämän jälkeen lasketaan ominaisarvo.
Ominaisarvo lasketaan kertomalla ominaisvektorin transpoosilla sisämatriisi, josta lasketaan pistetulo ominaisvektorin kanssa.
Kertomiseen menee taas aikaa O(n^2) ja vektorien pistetuloon menee aikaa O(n), sillä siinä käydään vain vektorit läpi samaan aikaan.
Joten yhden ominaisparin aikavaativuus on 1000(4O(n) + O(n^2))+O(n^2)+O(n), eli O(n^2) (tosin tässä on todella suuret vakiokertoimet).

Ominaisparin laskemisen jälkeen sisämatriisista vähennetään ominaisarvon, ominaisvektorin ja ominaisvektorin transpoosin tulo. Vähentäminen on tehty niin, että tulon laskeminen tapahtuu samalla. Näin ollen käydään samalla matriisi ja ominaisvektort läpi. Matriisin koko on m x m, joten aikaa kuluu O(m^2). Tällä vähennetään suurimman ominaisparin vaikutusta, jotta se ei ilmene uudelleen power-iteraatiossa.

Tämän jälkeen voidaan toistaa power-iteraatio, jotta löydetään seuraaksi itesisarvoltaan suurin ominaisarvo ja sen ominaisvektori. Kaikki ominaisvektorit asetetaan matriisiin riveittäin.

Sillä yhden ominaisparin laskemiseen kuluu aikaa 1000(4O(n) + O(n^2))+O(n^2)+O(n) ja ominaisparin vaikutuksen vähentämiseen kuluu aikaa O(n^2), niin n ominaisparin laskemiseen kuluu aikaa n(1000(4O(n)+O(n^2)+O(n))+O(n^2)) = 1000nO(n^2)+nO(n^2) = O(n^3) (Tässäkin on oikeasti suuret vakiokertoimet)

##### 8. Kovarianssimatriisin ominaisparit
Syy miksi laskettiin sisämatriisin ominaisparit on, että ei tarvitse laskea kovarianssimatriisin ominaispareja suoraan. Huomataan edellisestä kohdasta, että aikaa kuluu noin O(neliömatriisin kanta^3). Nyt sisämatriisin koko on n x n, mutta kovarianssimatriisin koko on m^2 x m^2. Aikaa siis suoraan kovarianssimatriisin ominaisparien laskentaan kuluisi O(m^2^3) eli O(m^6), joka on aivan liikaa. Mutta tiedetään, että kertomalla sisämatriisin ominaisvektorit isolla matriisilla, saadaan kovarianssimatriisin ominaisvektorit. Näiden ominaisvektoreiden ominaisarvot ovat samat kuin sisämatriisilla.

Ison matriisin ja ominaisvektorimatriisin kertomiseen kuluu aikaa O(n^2m^2), sillä ensimmäisessä matriisissa on m^2 riviä ja n saraketta. Toisessa on n riviä ja n saraketta. Kertolaskussa, kuten jo mainittu, kerrotaan oikean puoleisen matriisin sarakkeita ensimmäisella matriisilla. Ensimmäisen matriisin ja vektorin läpikäyntiin menee aikaa O(m^2n) ja se tehdään n kertaa. Aikaa siis kuluu O(m^2n^2).
Kaikki kovarianssimatriisin ominaisvektorimatriisin rivit normitetaan. Normitukseen kuluu aikaa vektorin pituuden verran. Rivit ovat m^2 pitkiä ja niitä on n verran, joten aikaa kuluu O(m^2n)

##### 9. Vaikuttavimpien ominaisparien laskeminen
Lasketaan kuinka monta suurinta ominaisparia tarvitaan, että suurimpien ominaisarvojen itseisarvojen summa on 0.95 kaikkien ominaisarvojen summasta.
Nyt summataan ensin kaikki ominaisarvot, joita on n. Tähän siis kuluu aikaa O(n). 
Sillä ominaisparit löytyvät suuruusjäjrestyksessä, niin niitä ei tarvitse järjestää. Nyt lasketaan itseisarvoja yhteen, kunnes itseisarvojen summa jaettuma kokonaissummalla on 0.95. Tähän menee aikaa O(p), jossa p on se määrä kuinka monta ominaisarvoa otetaan mukaan.
Nyt karsitaan kovarianssimatriisin ominaisvektorien matriisista ne rivit, joiden indeksi on enemmän tai yhtäsuuri kuin p.
Nyt Java kopioi arvot matriisista p riviltä ja m^2 sarakkeelta.
Aikaa kuluu siis O(pm^2)

Generointiin kuluu siis aikaa O(pm^2) + O(m^2n) + O(n^3) + O(n^2m^2) + 4O(nm^2). Tiedetään, että p on vähemmän tai yhtäsuuri kuin n, joten
aikavaativuus on O(n^3)+O(n^2m^2)

### Kasvojentunnistus

#### Prosessointi
Olkoon e tunnistettavien kuvien määrä. Nyt niille toistetaan eigenface generoinnin vaiheet 1,2,3,5, sillä rivikeskiarvo on tunnettu. Tähän kuluu aikaa O(em^2)

#### Painovektorit
Jokainen tunnistettavan kuvan vektori kerrotaan vaikuttavimpien ominaisvektoreiden matriisin transpoosilla. Transpoosinlaskemiseen kuluu aikaa O(pm^2) ja kertolaskuun kuluu aikaa O(pm^2e). 

#### Lopput prosessista
Nyt kerrotaan ominaisvektorit jokaisella painovektorin alkiolla, jotta nähdään sen projektio ominaisvektoreiden virittämällä avaruudella. Tässä käydään kaikki ominaisvektorit läpi, joita on p. Tähän kuluu aikaa O(pm^2). Vähennetään saatu vektori kuvavektorista, josta on vähennetty rivikeskiarvo ja lasketaan tämän pituus.
Vähennykseen kuluu aikaa O(m^2), sillä käydään vektoreiden alkiot samaan aikaan läpi. Pituuden laskemisessa myös käydään vektoreiden alkiot samaan aikaan läpi.
Pituus jaetaan pituudella m, jotta se skaalaantuu oikein. Tämän pituuden avulla voidaan tunnistaa onko kuvassa kasvot vai ei.
Tämä toistetaan e kertaa, joten aikaa kuluu O(epm^2)

Aikavaatimus kasvojentunnistukselle on siis O(epm^2)

### Puutteet ja parannukset

Parannettavaa on jonkin verran. Esimerkiksi nopeampi ominaisarvojen laskeminen nopeuttaisi projektin generointia paljon. Myös tehokkaammasta matriisikertolaskun algoritmista olisi hyötyä. Kuitenkin koen, että ohjelma toimii hyvin, jos ottaa huomioon, että kaikki logiikan algoritmit ovat tuotettu itse. Käyttöliittymä voisi myös olla kauniimpi. Myös laajemmat testit, sekä parempi dokumentaatio olisi hyvästä.

### Lähteet:

[Manfred Zabarauskas](http://blog.manfredas.com/eigenfaces-tutorial/)

[Eigenvaluedecomposition](https://medium.com/@louisdevitry/intuitive-tutorial-on-eigenvalue-decomposition-in-numpy-af0062a4929b)

[Wikipedia](https://en.wikipedia.org/wiki/Eigenface)

