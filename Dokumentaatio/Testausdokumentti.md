## Testausdokumentti

### Mitä ja miten on testattu?

Ohjelmassa on testattu kaikki matriisilaskennan metodit. Nämä ovat testattu käyttämällä yksinkertaisia syötteitä, jotka voisivat aiheuttaa helposti vääriä vastauksia.
On myös testattu, että metodit heittävät oikeissa tapauksissa poikkeuksia. Ominaisparien laskentaan on tehty tarkistukset pienille ominaisarvoille, mutta ei suurille. Tämä on siitä syystä, että toteutettu algoritmi palauttaa approksimaation ominaisvektoreista, joten on vaikeaa testata tarkasti, mitä ominaisvektoreita se palauttaa ja ovatko ne ominaisvektoreita. 

UiLogic-luokkaa ei ole testattu, sillä se suurimmilta osilta kutsuu vain toisia metodeita, eikä oikein tee mitään. Se tosin tekee kasvojentunnistusta, mutta sen testaamista ei ole tehty, sillä se on vaikeaa, koska kaikki matriisit ja vektorit täytisi ottaa ylös. Nämä ovat erittäin suuria ja testien toteuttaminen ei ole helppoa. Myös aika ei riittänyt tehdä näitä testejä. Myös dao-luokka jäi testaamatta, mutta se ei kuitenkaan kovin kummoisia tee. 

### Testit voidaan toistaa komennolla mvn test

### Ohjelman toiminnan testaus

Olen lisännyt repositorioon kansion "Analyysia", jossa on kuvaajia, sekä r-koodi, jossa on kuvien kasvojentunnistuksesta tulleita arvoja, eigenface-generoinnin aikoja eri harjoitusdatan koilla, sekä kasvojentunnistukseen kuluneita aikoja eri syötteillä.

#### Nopeus
Nopeudet on listattu tiedostossa Analysis.R, joka on kansiossa analysis.
Sen nojalla huomataan, että kun harjoitusdatan koko on 100 ja kuvan kanta on 100, aikaa kuluu noin 1.66 sekuntia
Kun harjoitusdatan koko on 200 ja kuvan kanta on 100, aikaa kuluu noin 10.17
Kun harjoitusdatan koko on 400 ja kuvan kanta on 100, aikaa kuluu noin 106.71.
Huomataan siis, että harjoitusdatan kaksinkertaistuessa aikavaatimus 8-10 kertaistuu. Tämä viittaisi siihen, että aikavaatimus on välillä O(n^3) ja O(n^3.3). Toteutusdokumentissa todettiin, että aikavaativuus on noin O(n^3)+O(n^2m^2), jossa n on kuvien määrä ja m kuvien kanta. Aineiston pohjalta tämä vaikuttaisi todelta.

Kasvojentunnistukseen kulunut aika riippui generoidun kuvien koosta ja generointiin käytettyjen kuvien määrästä, kuten toteutusdokumentissa oletettiin. 
Kun generointiin käytettiin 100 kuvaa ja kuvan kanta on 100 aikaa kului 1.66. Kun generointiin käytettiin 400 kuvaa ja kuvan kanta on 100 aikaa kului 4.58. Tämä viittaisi, että kasvojentunnistukseen kulunut aika on lineaarisesti riippuvainen generointiin käytetystä kuvien määrästä. Kuvan kanta vaikuttaa myös aineiston nojalla hieman. 
Jos kuvan kanta on 100 ja generointiin käytettyjen kuvien määrä on 100, niin:
50 kuvan tunnistamiseen kului 0.71 s
100 kuvan tunnistamiseen kului 0.84 s 
200 kuvan tunnistamiseen kului 1.66 s
400 kuvan tunnistamiseen kului 3.43 s

Kasvojentunnistukseen kulunut aika on myös riippuvainen tunnistettavien kuvien määräästä. 
Toteutusdokumentissä odotettiin aikavaatimuksen olevan O(epm^2), jossa p on tunnistettavien kuvien määrä, m on kuvan kanta ja e on käytettävien ominaisvektoreiden määrä.
Tämä näyttäisi olevan tosi. Tästä ei ole kuvaajaa, sillä siihen vaikuttaa niin monet asiat.

#### Tunnistaminen
Ohjelman kasvojentunnistuksen onnistuminen riippuu täysin rajasta, jolla hylätään tai hyväksytään kuva.
Algoritmilla, joka on tiedostossa Analysis.R, pisteyttää rajaa niin, että se kasvaa yhdellä kun kuva on oikeast kasvot ja vähentää yhden kun ne eivät ole.
Algortmille syötetään vektorit, josssa on kasvojen arvoja ja toinen jossa on muiden kuin kasvojen arvoja ja se palauttaa arvot jokaiselle rajalle.
Nämä ovat piirretty kuvaajalle, jossa y-akselilla on arvot ja x-akselilla rajat.

![Graph](https://github.com/tvaskisalo/Eigenface/blob/master/Analyysia/Kuvaajia/PisteetKanta100.png)

Halutaan asettaa raja niin, että se ottaa kaikki arvot ennen suurinta arvoa, mutta ei mitään sen jälkeen. Sillä kasvoista arvo kasvaa, huomataan, että kasvojen arvot ovat pienempiä kuin muiden kuin ei kasvojen. Annettu R-koodi laskee, missä kohtaa on kuvaajan maksimi.
Tämä on noin sama generointiin käytetyn kuvien määrästä riippumatta ja arvot skaalataan kuvan kannan mukaan ja ne ovat silti samat. Tästä osoitus eri kuvaajissa.

R-koodin avulla raja on laitettu noin 66:ksi.
Tällä rajalla oman testauksen mukaan algoritmi arvaa oikein onko kuvassa kasvot vai ei noin 60-65 prosenttia ajasta.
Algoritmi huomaa helposti kasvot, se tunnistaa kuvasta, jossa on kasvot, noin 90 prosenttia ajasta.
Kuitenkin noin puolet ajasta se hämääntyy kuvista, jossa ei ole kasvoja ja sanoo niitä kasvoiksi.
