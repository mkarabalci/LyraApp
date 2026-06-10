#d decisions.md

>Projede verilen bütün mimarisel-teknik kararları ve karar geçmişini içeren dökümantasyondur.

---

### Dependency Injection Kütüphanesi

- Seçim*: **Hilt**

- Son Güncelleme Tarihi*: 04.06.2026

- Alternatifler: **Koin**

- Sebep: **Opsiyonel**


### Navigasyon

- Seçim: **Compose Navigation**

- Son Güncelleme Tarihi: 04.06.2026

### Material Icons Extended

- Seçim: **androidx.compose.material:material-icons-extended**

- Son Güncelleme Tarihi: 10.06.2026

- Alternatifler: Harici SVG dosyaları, custom `ImageVector` tanımları

- Sebep: Login ekranında kullanılan `PhoneAndroid` ve `GraphicEq` ikonları, varsayılan (`material-icons-core`) ikon setinde yer almamaktadır. Extended set, Compose BOM tarafından yönetildiği için ek versiyon takibi gerektirmez.