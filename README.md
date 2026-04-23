# 🛍️ Product List - Frontend (Mobile App)

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5+-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material%203-Latest-006C4C?style=for-the-badge&logo=materialdesign&logoColor=white)](https://m3.material.io/)

Aplikasi Android modern untuk manajemen daftar produk, dibangun menggunakan **Jetpack Compose** dan arsitektur **MVVM**. Aplikasi ini terintegrasi dengan REST API yang dapat ditemukan di [Product List - Backend (BE)](https://github.com/rafliaditya0125/Product-List-BE.git).

---

## ✨ Fitur Utama

- 🔐 **Autentikasi Aman**: Registrasi akun baru dan Login pengguna.
- 📦 **Daftar Produk**: Menampilkan katalog produk dengan detail harga dan stok.
- 🖼️ **Image Loading**: Integrasi Coil untuk pemuatan gambar produk secara asinkron.
- 🏗️ **Modern UI**: Antarmuka berbasis Material 3 dengan dukungan Mode Gelap (Dark Mode).
- 🚀 **Reactive UX**: State management menggunakan ViewModel dan StateFlow untuk pengalaman pengguna yang mulus.

---

## 🛠️ Stack Teknologi

- **Bahasa**: Kotlin
- **UI Framework**: Jetpack Compose (Declarative UI)
- **Networking**: Retrofit & OkHttp
- **JSON Parsing**: GSON
- **Image Loader**: Coil
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency**: Version Catalog (libs.versions.toml)

---

## 📁 Struktur Proyek

```text
app/src/main/java/id/my/rafliaditya/login/
├── api/             # Konfigurasi Retrofit & Interface API
├── data/            # Repository & Data Models
├── ui/              # Compose Screens & Components
│   └── theme/       # Design System (Color, Type, Theme)
├── viewmodel/       # Logic bisnis & State Management
└── MainActivity.kt  # Entry point aplikasi
```

---

## 🚀 Cara Menjalankan Project

### Prasyarat
- Android Studio Ladybug atau versi terbaru.
- JDK 11 atau yang lebih tinggi.
- [Product List - Backend](https://github.com/rafliaditya0125/Product-List-BE.git) sudah berjalan atau dapat diakses.
- Koneksi internet untuk sinkronisasi Gradle dan API.

### Langkah Instalasi
1. **Clone Repository**
   ```bash
   git clone https://github.com/rafliaditya0125/Product-List-FE.git
   ```
2. **Buka di Android Studio**
   Tunggu hingga proses sinkronisasi Gradle selesai.
3. **Konfigurasi API**
   Pastikan URL API di `build.gradle.kts` sudah benar:
   ```kotlin
   buildConfigField("String", "BASE_URL", "\"[URL Backend]\"")
   ```
4. **Run**
   Klik tombol **Run** (Play) dan pilih Emulator atau Perangkat Fisik.

---

## 📸 Tampilan Aplikasi (Screenshots)

| Login Screen | Register Screen | Product List |
|:---:|:---:|:---:|
| (Coming Soon) | (Coming Soon) | (Coming Soon) |

---

## 📝 Kontribusi
Kontribusi selalu terbuka! Silakan lakukan fork dan kirim Pull Request.

## 📄 Lisensi
Distribusi di bawah Lisensi MIT. Lihat `LICENSE` untuk informasi lebih lanjut.