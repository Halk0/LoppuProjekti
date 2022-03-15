# Koulun opiskelijoiden ja kurssien hallinta REST-rajapinta
### Tämä projekti on toteutettu OAMK:in java kurssin lopputyönä

## Data mallit

### com.project.loppuproj.data.Course
- kurssin luokka, jossa on toteutettuna kurssi objektin logiikka
   - Sisältää kurssin yksityiskohtaiset tiedot, sekä listan kurssille osallistuneista oppilaista
### com.project.loppuproj.data.CourseMeta
- Kurssin metadata luokka, joka määrittelee mitä tietoja kurssista vaaditaan REST-api:n kautta, kun uutta kurssia luodaan
### com.project.loppuproj.data.Student
- Opiskelija luokka, jossa on toteutettuna opiskelija objektin logiikka
  - Sisältään opiskelijan yksityiskohtaiset tiedot, sekä listan kursseista joille opiskelija on osallistunut
### com.project.loppuproj.data.StudentMeta
- Opiskelijan metadata luokka, joka määrittelee mitä tietoja opiskelijasta vaaditaan REST-apin:n kautta, kun uutta opiskelijaa luodaan

## Servicet

### com.project.loppuproj.service.CourseService
- Hoitaa kurssi luokan käsittelyn ja siitä objektien luomisen REST-api:n käskyttämänä.
### com.project.loppuproj.service.StudentService
- Hoitaa opiskelija luokan käsittelyn ja siitä objektien luomisen REST-api:n käskyttämänä.
### com.project.loppuproj.service.StateService
- Hoitaa tilan lukemisen ja tallentamisen levylle
  - Tila tallenetaan KurssiState.json ja StudentState.json nimisiin tiedostoihin JSON muodossa
  - Käynnistäessä edellinen tila haetaan lukemalla se yllä mainituista tiedostoista

## Controller
### com.project.loppuproj.service.MooRestController
- Käskyttää Service:jä luomalla REST-rajapinnan ja käsittelemällä sieltä saatua dataa ja tarjoamalla nykyistä dataa pyydettäessä

## REST rajapinta

### Course controll documentation
https://documenter.getpostman.com/view/18525714/UVsLRRhK

### Student controll documentation
https://documenter.getpostman.com/view/18525714/UVsLRRmo

