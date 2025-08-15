Apartman/site yönetimi mobil uygulaması — backend Spring Boot 3.5.x (Java 24), mobil React Native, veritabanı MongoDB — minimum maliyetle çalışacak şekilde nihai plan:

⸻

 Mobil Uygulama (React Native)
	•	Platformlar: iOS + Android
	•	Ekranlar:
	1.	Giriş/Kayıt (e-posta/şifre veya telefon numarası)
	2.	Duyurular Listesi (yönetici ekleyebilecek, sakinler görüntüleyebilecek)
	3.	Aidat/Borç Listesi (ay bazında borç dökümü)
	4.	Arıza Bildirimleri (sakinler arıza ekleyebilir, yönetici durum değiştirebilir)
	5.	Borç Ödeme Belgesi Yükleme
	•	Banka dekontu, EFT/havale ekran görüntüsü vb.
	•	Fotoğraf çekerek veya dosyadan seçerek yükleme
	6.	Profil & Ayarlar (parola değişimi, iletişim bilgileri)

⸻

 Backend (Spring Boot 3.5.x, Java 24)
 gradle, lombok
	•	Katmanlı Mimari:
	•	Controller → Service → Repository → MongoDB
	•	DTO & MapStruct ile veri dönüşümleri
	•	Modüller:
	1.	Auth & User Management
	•	JWT ile kimlik doğrulama
	•	Roller: Admin (yönetici), Resident (sakin)
	2.	Announcements (Duyurular)
	•	Yönetici: ekle, güncelle, sil
	•	Sakin: listele, görüntüle
	3.	Payments (Aidat/Borç)
	•	Borç kayıtları (yönetici ekler)
	•	Sakin borcunu görüntüler
	•	Sakin ödediği borç için ödeme yöntemi seçer banka, elden gibi
	•	Belge yükleme (Cloud storage linki DB’de saklanır)
	4.	Issues (Arıza Bildirimleri)
	•	Sakin: yeni bildirim (yöneticiye push notification düşer)
	•	Yönetici: durum güncelleme (beklemede, çözülüyor, çözüldü)
	5.	File Upload Service
	•	Borç ödeme belgeleri veya arıza fotoğrafları için
	•	Depolama tercihi: Ücretsiz veya en ucuz servis

⸻

Veritabanı
	•	MongoDB Atlas (free tier 512 MB)
	•	JSON tabanlı, hızlı geliştirme için ideal
	•	Koleksiyonlar:
	•	users
	•	announcements
	•	payments
	•	issues
	•	files

⸻

☁ Depolama ve Diğer Hizmetler (En Uygun/Ücretsiz)
	•	Depolama Alternatifleri:
	1.	Cloudinary (Free plan — 25 GB ayda)
	•	Dosya + fotoğraf yükleme
	•	CDN üzerinden hızlı erişim
	2.	Firebase Storage (Free tier 1 GB)
	•	Kolay React Native entegrasyonu
	3.	Supabase Storage (Free plan 1 GB)
	•	S3 benzeri API
	•	Push Bildirimleri:
	•	Firebase Cloud Messaging (FCM) — tamamen ücretsiz
	•	Barındırma (Backend):
	•	Render.com (Free plan)
	•	veya Railway.app (Free plan)

⸻

 Nihai Özellik Listesi

Duyurular
Aidat/Borç görüntüleme
Arıza bildirimi
Belge yükleme (banka dekontu vb.)
Push bildirimleri
Ücretsiz cloud & veritabanı kullanımı
Hem iOS hem Android uyumu


//  export JWT_SECRET="2NSYO0aVKUDtZFIKUM4yFTzz6tpZoEyeMu6NybVyfjA=" && export MONGODB_URI="mongodb+srv://ytahirkose-deneme:pEPCCqHIpFNkLhQc@site-management.0xlhocx.mongodb.net/site_management?retryWrites=true&w=majority&appName=site-management" && export MONGODB_DATABASE="site_management" && ./gradlew bootRun