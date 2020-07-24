# **Määrittelydokumentti**

## **Vaaditut tietorakenteet**
* Matriisi

## **Vaaditut algoritmit**

* 1. Kuvan muuttaminen matriisiksi
* 2. Värillisen kuvan muuttaminen mustavalkoiseksi
* 3. Matriisi kertolaskun algoritmi (esimerkiksi Strassen algoritmi)
* 4. Matriisin uudelleen muotoiluun algoritmi
* 5. Matriisin sarakekeskiarvojen laskeminen
* 6. Matriisien erotuksen laskeminen
* 7. Algoritmi SVD:n laskemiseen.

## **Ongelma**

Ongelmana projektissa on kasvojentunnistus. 
Ongelman ratkaisuun käytetään eigenface-lähestymistapaa,
jossa käytetään matriisilaskentaa. Tämän takia tarvitaan matriisi,
sekä osa sen laskutoimituksista. 

## **Syötteet**

Ohjelmalle annetaan syötteenä kuvia, joita se käyttää testidatana.
Tämän jälkeen ohjelmalle annetaan kuva ja ohjelma pyrkii kertomaan,
onko kuvassa kasvot vai ei.

## **Aika- ja tilavaatimus**

Jaan aikavaatimuksen osioihin algoritmien perusteella (Järjestysnumerot vastaa algoritmeja):
1. O(n^2)
2. O(n^2)
3. O(n^2.8074)
4. O(n^2)
5. O(n^2)
6. O(n^2)
7. O(mn^2 +m^2 n), jossa m on matriisin rivien määrä ja n sarakkeiden määrä

Joten koko sovelluksen aikavaatimus pitäisi olla tasoa
O(mn^2 +m^2 n), joka on suurella syötteellä O(n^3)

Tilavaatimus on luokkaa O(mn^2), jossa m on kuvien määrä ja n on nxn kuvan kanta.

Lähteet:

https://en.wikipedia.org/wiki/Eigenface#Connection_with_SVD
http://blog.manfredas.com/eigenfaces-tutorial/
https://en.wikipedia.org/wiki/Computational_complexity_of_mathematical_operations
