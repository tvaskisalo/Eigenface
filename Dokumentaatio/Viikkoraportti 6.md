## Viikkoraportti 6

Tällä viikolla ohjelma on edistynyt jonkin verran. Olen tällä viikolla tehnyt algorimit kaikkien ominaisparien laskentaan, eli en enää tarvitse ollenkaan matriisilaskentaan liittyviä javan kirjastoja. Edistin myös vähän kuvankäsittelyyn liittyvää testausta. Kaikille metodeille luokissa MatrixOperations ja ImageProcessing ei ole testejä, sillä osa metodeista on tullut turhiksi ja todennäköisesti poistetaan. Tämä harmillisesti vähentää testikattavuutta, sillä luokissa on turhia metodeita.  Lisäilin vähän myös käyttöliittymän koodia. Tein myös vertaisarvioinnin, sekä lisäsin hieman dokumentointia. Jouduin perjantain työskentelemään kannettavalla tietokoneella, jossa ei syystä taikka toisesta toimi netbeans kunnolla, niin ohjelmassa saattaa olla tällä hetkellä hieman bugeja.

Opin tällä viikolla yllättävän paljon teoriaa laskennasta. Jouduin lukemaan jonkin verran aineistoja, jotta ymmärsin miten voidaan löytää monta ominaisparia käyttäen power-iterointia. Löysin tähän vastauksen ja sain koodin toimimaan jotenkin. Opin myös vertaisarvioinnista, miten lukea toisen koodia. Mielestäni tällä kurssilla olisi saanut olla enemmän vertaisarviointeja, sillä saan ainakin itse niistä paljon irti. Tykkään myös saada palautetta, mikä motivoi koodaamaan. 

Tällä viikolla törmäsin suhteelisen isoon ongelmaan. Pyrin selittämään sen mahdollisimman selvästi, mutta en tiedä osaanko. Power-iteraatio löytää oikein suurimman ominaisparin, mutta minulla on ollut ongelmia muiden ominaisparien löytämisessä. Olkoon A matriisi, josta pyritään ratkaista ominaisparit. Power-iteraatio löytää kaikissa tapauksissa oikein ensimmäisen ominaisparin. Täytyy siis jotenkin vähnetää ominaisparin vaikutusta matriisissa, jotta voidaan löytää seuraava pari. Yksi vaihtoehto on vähentää ominaisarvo matriisista. Tämä toimii, mutta vain pienillä ominaisarvoilla. Kun vähennettävä ominaisarvo on suuri, niin power-iteraatio ei enää löydä seuraavia ominaispareja. Eli iso ominaisarvo vähentää kaikkien ominaisarvojen vaikutusta. Toinen vaihtoehto on seuraava: Olkoon A edelleen sama matriisi. Olkoon a ominaisarvo ja v sitä vastaava ominaisvektori. Jos vähennettään ominaisparin vaikutusta seuraavalla kaavalla: A-a\* v\* transpoosi(v). Tällöin power-iteraatio löytää aina seuraavan ominaisarvon. Kuitenkin tällöin a) algoritmi on hidas b) ominaisvektorit ovat suuntaa antavia, eivätkä tarkkoja. Kummatkaan vaihtoehdot eivät ole mielekkäitä, sillä tarvitaan kaikki ominaisparit, mutta ominaisvektorien täytyisi olla mahdollisimman oikein, sillä se vaikuttaa suoraan kasvojentunnistukseen. Tähän siis tarvisin jonkinlaista apua.

Seuraavaksi olisi tarkoitus tehdä järkevä käyttöliittymä, viimeistellä testit, korjata ominaisparien löytäminen, sekä saada dokumentointi kuntoon. Olisin halunnut saada tehtyä paremman matriisikertolaskun algoritmin, mutta ominaisparien laskeminen on koitunut sen verran haastavaksi, että en usko ajan riittävän. Saatan tehdä sen ihan oman hupini takia kurssin jälkeen, jos motivaatio riittää. Kuitenkin koen, että projektista tulee hyvä.

### Tuntikirjanpito

24.8.2020 1.5h

26.8.2020 3h

27.8.2020 1.5h

28.8.2020 4h

Yht: 10h
