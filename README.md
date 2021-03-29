 # JavaFX_Transfer

Ini merupakan project transfer data antar DB
yang rencana akan dapat digunakan secara fleksibel tanpa harus hard code lagi
urutan kerja aplikasi transfer ini :
- menghapus data tujuan
- menyiapkan data Source (select * from ...)
- menyiapkan query insert tujuan dari transfer data
- menyiapkan wadah berupa setObject
- melakukan upload data dengan proses batching
