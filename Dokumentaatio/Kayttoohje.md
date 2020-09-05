# Käyttöohje

Lataa repositorio:
[Repositorio](https://github.com/tvaskisalo/Eigenface)

Pura ladattu ZIP-tiedosto.

Aseta tunnistettavat kuvat kansioon Eigenface/images/DetectFaces/ ja mahdollinen harjoitusdata kansioon Eigenface/images/InputImages.
Tunnistettavat kuvat kannattaa olla sellaisia, jotka eivät ole suuria (Full HD-kuvien prosessointi vie kauan) ja kasvojen suu ja silmät tätyisi olla noin samassa kohtaa kuin testiaineiston. 

Käynnistä sovellus komennolla mvn compile exec:java -Dexec.mainClass=eigenface.Main tai netbeansilla painamalla run-näppäintä.

![menu](https://user-images.githubusercontent.com/61991314/92310192-915a7e00-efb4-11ea-9c36-5c5ae1f9198b.PNG)

Sovellus kysyy haluatko generoida uudet eigenface-kasvot vai käyttää valmista .csv-tiedostoa.
Repositoriossa on valmiiksi generoituja .csv-tiedostoja kuvan kannoille 25, 50, 75 ja 100 pikseliä.
Jos valitset valmiiksi generoidun tiedoston laita kuvan kannan pituus numerona. Huom. Kansiossa Eigenface/images/DetectFaces/ täytyy olla ainakin yksi kuva, jotta ohjelma toimii.

Jos generoit itse eigenfacet pääset seuraavaan näkymään:

![Generate](https://user-images.githubusercontent.com/61991314/92310179-7daf1780-efb4-11ea-8e29-370d494c1bae.PNG)

Laita tähän haluamasi kuvan kannan koko, jonka ei tarvitse olla sama kuin harjoitusdatan kuvien koko.

Sitten ohjelma tunnistaa kuvista kasvot ja antaa näkymän, jossa on dataa ohjelman suorituksesta, sekä pieni dia-esitys, jossa on kaikki tunnistetut kuvat


PS. En harmillisesti saanut tehtyä compileä, sillä tuli yllättävä mutka matkaan projektissa, eikä aika riittänyt. Toivottavasti tämä ei pilaa kaikkea
