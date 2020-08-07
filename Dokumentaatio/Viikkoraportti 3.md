## Viikkoraportti 3
Koen, että sain tehtyä tällä viikolla paljon, vaikkakin vähän viime tinkaan. Pyrin aluksi tekemään testivetoisesti projektia eteenpäin, mutta en ollut täysin varma mitä kannattaisi tehdä, niin testit jäivät aluksi tekemättä, kun hahmottelin koodia. Koodin laatu myös kärsi tästä jonkin verran. Testivetoinen koodaaminen helpottaa pitämään metodien pituudet aisoissa. Nyt tuli kirjoitettua pari metodia, jotka tekevät montaa eri asiaa ja ne ovat sotukuisia. Katsoin myös paljon, kuinka ominaisarvoja ja ominaisvektoreita saadaan laskettua, mutta en päässyt tässä kovin pitkälle. Suurin onnistuminen tällä viikolla on ensimmäisten eigenface-kuvien generointi. Tuntui hyvältä kun vihdoin näki oman työn tulosta. Olen lisännyt pari ensimmäistä kuvaa tänne: [Eigenfaces](https://github.com/tvaskisalo/Eigenface/tree/master/Eigenface/images/ProcessedImages). Kuvat ovat pieniä, koska koodi on vielä hidasta.

Ohjelmaan on nyt lisätty paljon eri metodeita. Esimerkkeinä on muun muassa kuvan muuttaminen matriisiksi, matriisin transponointi, vektorin normalisointi, sekä matriisin muuttaminen kuvaksi. Lisäsin myös testit niille metodeille, jotka eivät tarvitse refaktorointia. En koe refaktoroitavien metodien testausta järkeväksi, sillä ne voivat muuttua hyvin paljon. Myös kaikki metodit omaavat javadocin ja osa myös kommentteja. Kaikki checkstyle-virheet, jotka eivät koske refaktorointia, on korjattu.

Tällä viikolla opin paljon. Luin monen monta tuntia, kuinka lasketaan matriisin ominaisvektoreita ja ominaisarvoja. Opin myös ymmärtämään paremmin, minkä takia eigenface toimii. Kuvienkäsittely javalla on tullut selkeämmäksi ja helpommaksi kuin ennen. Opin myös hahmottamaan paremmin javan matriiseja ja käyttämään niitä. Sain myös kerrattua paljon matriisilaskentaa, sekä muutamia matriisien hajotelmia.

Minulla on ollut hieman vaikeuksia, kuinka teen testejä kuvankäsittelyn metodeille. En koe osaavani luoda testejä niille, enkä osaa lähestyä niiden testaamista. Vai kannattaako testaaminen jättää niiltä pois? Myös algoritmi ominaisarvojen ja ominaisvektoreiden laskemiseen on tuottanut ongelmia. Olen tutkinut asiaa jonkin verran, mutta en ole saanut hyvää ideaa, miten ne kannattaisi laskea ja millä algorimilla. Minulla on myös ollut vaikeuksia löytää pseudokoodi versioita algorimeista. 

Aluksi myös hämäsi, kun BufferedImage-luokasta kopioi pikseleiden arvot samassa järjestyksessä matriisiksi, niin saatu matriisi on rivimatriisi, jossa on sarakematriiseja. Mutta taas jama-kirjastossa, kun muuttaa tämän matriisin jama-kirjaston omaksi matriisiksi, niin se olettaa, että annettu matriisi on sarakematriisi, jossa on rivimatriiseja. Olen kuitenkin päättänyt, että matriisit ovat projektissa rivimatriiseja, jossa on sarakematriiseja. Transponoin aina matriisit ennen jama-kirjaston käyttöä ja sen jälkeen, joten se ei tuo haittaa. Muutenkin tarkoituksena on tehdä kaikki tarvitsemani jama-kirjaston metodit itse.

Seuravalla viikolla aion vihdoin ja viimein tehdä käyttöliittymäkoodia, kun vihdoin logiikka on jo joksenkin kunnossa. Tarkoitus on myös refaktoroida paljon huonoja metodeita, sekä tehdä kunnon järjestämisalgoritmin ominaisarvojen järjestämistä varten. Tarkoituksena on myös aloittaa algoritmin teko ominaisarvojen ja ominaisvektoreiden laskemista varten. Jos aika vielä tästä riittää, niin myös aloittaa kasvojentunnistus-osa ohjelmasta, johon käyttäjä voi syöttää kuvia ja ohjelma pyrkii sanomaan, onko siinä ihminen vai ei. 


### Viikon tuntikirjanpito

6.8.2020: 6h

7.8.2020: 7h

Yht: 13 h
