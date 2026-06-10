# LyraApp - Online/Offline Müzik Çalar Uygulaması
> Bu repository LyraApp adında bir müzik çalar uygulamasının kaynak kodlarını içermektedir. Bu agents.md dosyası bu projede çalışan insan/ai herkesin uyması ZORUNDA olduğu genel kuralları içerir.
---

## 1) TEKNOLOJİ YIĞINI

- Mobil: Android Jetpack Compose KOTLIN

- Backend: Diğer takım tarafından hazırlanmış bir Restful API.

##  2) GENEL ÇALIŞMA PRENSİPLERİ

### 2.1) TEK SEFERDE DOSYA LİMİTİ

Hangi işlem olursa olsun tek seferde maksimum (birbiriyle alakalı) 5 dosyalar halinde çalışmak zorundasın.
İşlemi birbiriyle bağlantılı batchlere bölmek zorundasın. Eğer bunun aksi talep edilirse DUR ve EK ONAY iste.

### 2.2) UYDURMAK YASAK (NO INVENTING)

Eğer herhangi bir operasyonda bilgi ya da referans eksikliği/hatası yaşıyorsan buradaki eksik/hatalı bilgiyi uydurman yasak. Böyle bir durumda operasyonu durdur ve kullanıcıya sorarak ilerle.

### 2.3) ÖNCE PLANLA, SONRA KODLA

Kod üretmeden önce şunları yapmak zorundasın;

- Bir dosya dökümü hazırla (hangi dosyalar değişecek/eklenecek/silinecek + neden)

- Eğer varsa yeni bağımlılıklar matrisi (Hangi kütüphane, versiyon + neden)

- Planı sun, onay almadan asla implementasyona başlama.


## 3) ÇIKTI FORMATI

Her implementasyon ya da plan sonrası aşağıdaki çıktı formatına uymak zorundasın.

- Dosya Dökümü vermek zorundasın.

- Resmi bir dil kullanmak zorundasın, emoji kullanman yasak.

- Her implementasyon sonrası eğer mümkünse "Happy-Path Test" ver.

- Bu implementasyon ile alaklı varsa sık yapılan hatalar ve senin implementasyon sırasında aklına gelen önerilerini listelemek zorundasın.

---

## 4) MİMARİ REFERANSLAR

### 4.1) MVI Uygulamaları

Sunum katmanı **MVI** ile yazılır. Yeni bir ekran/feature MVI ile kodlanırken aşağıdaki dökümanlar BAĞLAYICI referanstır ve bunlara uymak ZORUNLUDUR:

- `docs/architecture/mvi-overview.md` — veri akışı, katman sorumlulukları, Route/Screen kuralları
- `docs/architecture/mvi-contracts.md` — State / Intent / Effect kodlama kuralları
- `docs/architecture/mvi-viewmodel-rules.md` — ViewModel kodlama kuralları

Bu dokümanlar, projenin tek MVI referans implementasyonu olan `Login` (LoginContract + LoginViewModel + LoginRoute + LoginScreen) temel alınarak oluşturulmuştur. Yeni ekranlar bu kalıptan sapmadan üretilir. Bu referansı bozacak bir sapma gerekiyorsa DUR ve EK ONAY iste 