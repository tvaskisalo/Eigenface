## Testausdokumentti

### Mitä ja miten on testattu?

Ohjelmassa on testattu kaikki matriisilaskennan metodit. Nämä ovat testattu käyttämällä yksinkertaisia syötteitä, jotka voisivat aiheuttaa helposti vääriä vastauksia.
On myös testattu, että metodit heittävät oikeissa tapauksissa poikkeuksia. Ominaisparien laskentaan on tehty tarkistukset pienille ominaisarvoille, mutta ei suurille. Tämä on siitä syystä, että toteutettu algoritmi palauttaa approksimaation ominaisvektoreista, joten on vaikeaa testata tarkasti, mitä ominaisvektoreita se palauttaa ja ovatko ne ominaisvektoreita. 

UiLogic-luokkaa ei ole testattu, sillä se suurimmilta osilta kutsuu vain toisia metodeita, eikä oikein tee mitään. Se tosin tekee kasvojentunnistusta, mutta sen testaamista ei ole tehty, sillä se on vaikeaa, koska kaikki matriisit ja vektorit täytisi ottaa ylös. Nämä ovat erittäin suuria ja testien toteuttaminen ei ole helppoa. Myös aika ei riittänyt tehdä näitä testejä. Myös dao-luokka jäi testaamatta, mutta se ei kuitenkaan kovin kummoisia tee. 

### Testit voidaan toistaa komennolla mvn test

### Ohjelman toiminnan testaus

Olen lisännyt repositorioon kansion "Analyysia", jossa on kuvaajia, sekä r-koodi, jossa on kuvien kasvojentunnistuksesta tulleita arvoja, eigenface-generoinnin aikoja eri harjoitusdatan koilla, sekä kasvojentunnistukseen kuluneita aikoja eri syötteillä.
