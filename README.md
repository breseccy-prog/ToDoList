# TaskMate - Aplikasi Manajemen Tugas Harian

TaskMate adalah aplikasi Android sederhana untuk membantu pengguna mencatat dan mengelola tugas harian. Aplikasi ini dikembangkan dari project ToDoList sebelumnya menjadi aplikasi manajemen tugas yang memiliki fitur CRUD, kategori tugas, prioritas, deadline, status tugas, dan timer fokus.

## Deskripsi Aplikasi

Aplikasi ini dibuat sebagai proyek pengembangan aplikasi Android menggunakan Kotlin sebagai bahasa pemrograman dan XML sebagai desain antarmuka. TaskMate dapat digunakan untuk mencatat tugas, melihat daftar tugas, mengubah data tugas, menghapus tugas, serta menandai tugas yang sudah selesai.

## Fitur Aplikasi

- Menambahkan tugas baru
- Menampilkan daftar tugas
- Melihat detail tugas
- Mengedit data tugas
- Menghapus tugas
- Menandai tugas selesai atau belum selesai
- Memberikan kategori tugas
- Menentukan prioritas tugas
- Menambahkan deadline tugas
- Mencari tugas berdasarkan judul, kategori, atau deskripsi
- Filter tugas berdasarkan status
- Timer fokus untuk membantu pengguna belajar atau bekerja

## Teknologi yang Digunakan

- Bahasa Pemrograman: Kotlin
- Desain Antarmuka: XML
- Database Lokal: SQLite
- IDE: Android Studio
- Komponen Android:
  - Activity
  - RecyclerView
  - CardView
  - SQLiteOpenHelper
  - Intent
  - Shared UI Layout XML

## Struktur Project

```text
app/
 └── src/
     └── main/
         ├── AndroidManifest.xml
         ├── java/com/example/todolist/
         │   ├── MainActivity.kt
         │   ├── AddEditTaskActivity.kt
         │   ├── DetailTaskActivity.kt
         │   ├── FocusTimerActivity.kt
         │   ├── data/
         │   └── adapter/
         └── res/
             ├── layout/
             ├── drawable/
             └── values/
