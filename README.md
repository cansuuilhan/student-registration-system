# Öğrenci Ders Kayıt Sistemi

Bu proje, öğrencilerin ders şubelerini görüntüleyebildiği, derslere kayıt olabildiği ve kayıtlı derslerini yönetebildiği
konsol tabanlı bir Öğrenci Ders Kayıt Sistemidir.

## Özellikler
- Şube listesi görüntüleme
- Derse kayıt olma
- Aynı derse tekrar kayıt engeli
- Kontenjan kontrolü
- Zaman çakışması kontrolü
- Ders çıkarma
- Kayıtlı dersleri görüntüleme
- Dosyaya kayıt alma (kalıcı kayıt)

## Kullanılan Teknolojiler
- Java
- Maven
- JUnit 5
- Git & GitHub

## UML Diyagramları
- Use Case UML: `uml/Öğrenci kayıt sistemi use-case uml.png`
- Class UML: `uml/Öğrenci kayıt sistemi class uml.png`

## Testler
- RegistrationService sınıfı için JUnit testleri yazılmıştır.
- Kayıt, tekrar kayıt, kontenjan doluluğu ve zaman çakışması senaryoları test edilmiştir.

## Çalıştırma
Proje konsol üzerinden çalışmaktadır.  
`Main.java` sınıfı çalıştırılarak sistem başlatılır.
