## Viikkoraportti 2
Tällä viikolla keskityin eniten matriisilaskutoimituksiin. Tein matriisilaskutoimituksia testivetoisena, joka helpotti eri metodien hahmottelua. Tietenkin meni jonkin aikaa, että opin hahmoittamaan kehitystä tällä tyylillä, sillä se on vieras. Toistaiseksi kaikki matriisien laskutoimitukset ovat yksinkertaisia, eikä tehokkaimpia. Esimerkiksi matriisikertolasku on tarkoituksena optimoida. Tein matriiseille aluksi myös oman "equals"-tyylisen metodin, sillä en osannut hahmottaa assertEqualsin toimintaa matriiseille. Voi olla, että kyseinen metodi poistetaan myöhemmin, jos se jää tarpeettomaksi. Peruslaskutoimituksen lisäksi tein metodin, jolla muuttaa matriisit riveittäin vektoriksi, sekä metodin, jolla voi vähentää vektorin termeistä vektorin termien keskiarvon. Tällä viikolla olisin halunnut tehdä enemmän, mutta aikarajoitteelisista syistä en voinut.

Olisin halunnut aloittaa tälllä viikolla jo käyttöliittymän kaavailun, mutta muu logiikka ohjelmassa ei ole niin pitkällä, että se olisi järkevää. Olisin myös halunnut kuvankäsittelyä pidemmälle, mutta se osoittautui vaikeammaksi, kuin luulin. Kuvankäsittelyn opetteluun ja ymmärtämisessä javassa meni jonkin aikaa. Etsin tällä viikolla myös dataa ohjelmaa varten.

Lisäksi tällä viikolla lisäsin javadocin kaikille luokille ja metodeille, paitsi tyhjään Ui-luokkaan. Myös checkstyle on otettu käyttöön, testattu sen toimivuus, sekä korjattu virheet kaikkialta koodista. Testit ovat myös toteutettu matriisilaskutoimituksille ja testikattavuus on niiden osalta 100%. Testikattavuuden tutkimiseen olen ottanut käyttöön jacocon, jonka toimivuus on myös todettu. Javadoc, jacoco ja checkstyle on tuotu projektiin mavenin avulla. Komennot näiden suorittamiseen löytyy README:stä.

Opin tällä viikolla testivetoisesta ohjelmistotuotannosta, sekä kuvienkäsittelystä javalla. Molemmat aiheet ovat sellaisia, joissa en ole vielä kovin hyvä. Myös tiedonhakutaitoni parantuivat tällä viikolla.

Aluksi kuvienkäsittelyn kanssa löin päätäni seinään, kun en meinannut ymmärtää sen toimintaa. Sain lopulta kuitenkin asiasta kiinni, joten uskon pärjääväni. Myös javan kirjoittaminen tuotti tällä viikolla vaikeuksia, sillä en ole kirjoittanut javaa hetkeen, vaan olen kirjoittanut javascriptiä ja pythonia. Aluksi piti vähän muistutella, miten javassa kaikki toimii, mutta nyt se sujuu taas sutjakasti. 

Seuraavalla viikolla aion keskittyä siihen, että saan kuvienkäsittelyyn liittyvän koodin tehtyä. Aion myös aloittaa jonkinnäköisen käyttöliittymän kaavailun, sillä tällä viikolla se jäi tekemättä. Jos vain aika riittää, niin aion myös vähän katsoa SVD-jakauman toteuttamista. 


### Viikon tuntukirjanpito:
25.7.2020: 1h
26.7.2020: 3h
27.7.2020: 4.5h 
30.7.2020: 1h
Yht: 9.5h
