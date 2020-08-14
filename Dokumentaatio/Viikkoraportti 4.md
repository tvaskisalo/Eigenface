## Viikkoraportti 4

Tällä viikolla en ehtinyt harmillisesti tekemään kovin paljoa. Lisäsin tällä viikolla lisäsin alkeellista käyttöliitymää, korjasin pari suurta virhettä, 
sekä lisäsin toiminnallisuuden kasvojentunnistukseen. 
Harmillisesti tällä viikolla alkoi eräs toinen kurssi, joka on vienyt todella paljon enemmän aikaa kun aluksi oletin, joten aikatauluni menivät mönkään.
Tästä syystä en ehtiny tehdä projektia niin paljoa eteenpäin, kuin olisin halunnut. Otin myös selvää, kuinka toteuttaisin itse ominaisarvojen ja omnaisvektorieden etsimisen.
Olen päätynyt siihen tulokseen, että käytän niin sanottua power-iterointia, jonka avulla voidaan löytää suurin ominaisarvo ja sen ominaisvektori. 
Sen jälkeen voidaan vähentää matriisista sen ominaisvektroin vaikutusta, jotta löydetään seuraavaksi suurin ominaisarvo ja sen ominaisvektori ja niin edelleen.
Tämä on kätevää siksi, että silloin ominaisarvot ja ominaisvektorit ovat jo oikeassa järjestyksessä, eikä niitä tarvitse järjestää uudelleen. Tämä optimoi nykyistä koodiani.

Ohjelmaan on nyt lisätty sisältöä käyttöliittymään liittyen. Nyt ohjelman suorittaessa se kysyy, että haluaako käyttäjä teksiliittymän vai graaffisen. 
Tekstikäyttöliittymä ei tee oikeasti mitään ja on tällä hetkellä vain satunnaista testaamista varten. Lisäsin myös uuden kansion, johon voidaan laittaa tunnistettavia kuvia.
Nyt graaffisen käyttöliittymän käynnistäessä se kysyy kuvien kokoa ja sen jälkeen "kouluttaa" itse itsensä ja sitten tarkistelee, onko annetuissa kuvissa kasvoja vai ei.
Testasin itse kouluttaa 500 kasvoilla, jotka ovat 100x100 pikseliä. Tällöin ohjelma tunnisti kuvista kasvot oikein noin 77% ajasta.

Tällä viikolla opin hahmottamaan paremmin projektin etenemistä ja mitä minun täytyy tehdä. Onnistuin myös vihdoin löytämään hyvän tavan laskea ominaisarvot ja -vektorit.
Mietin myös miten saisin testattua kuvankäsittelyä ja koen, että olen löytänyt siihen ratkaisun itse.  Opin myös hieman javafx-kirjaston käyttäytymisestä.

Tällä viikolla ei ole ollut juurikaan epäselvyyksiä. Ehkä enemmän sen takia, että olen keskittynyt enemmän "helppoihin" osioihin ajan- ja energianpuutteen takia. 
Olen myös saanut ratkaistua ongelmia, joita mainitsin edellisellä viikolla. Otan kuitenkin yhteyttä, jos koen että nämä ratkaisut eivät toimi.

Seuraavalla viikolla olisi tarkoitus aloittaa ominaisvektoreiden ja ominaisarvojen laskentaan tarvittava koodi. 
Päätavoite seuraavalla viikolla on saada kaavailtua hyvä tapa laskea ne ja mielellään kirjoittaa jo testejä ja koodia.
Myös on tarkoitus laajentaa käyttöliittymää paremmaksi, sekä lisätä testejä metodeille, joille en ehtinyt tällä viikolla lisäämään testejä.


### Tuntikirjanpito

9.8.2020 4h

12.4.2020 4h

14.4.2020 1.5h

Yht: 9.5h
