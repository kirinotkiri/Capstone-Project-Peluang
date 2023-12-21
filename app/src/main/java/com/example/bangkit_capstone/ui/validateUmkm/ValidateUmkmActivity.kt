package com.example.bangkit_capstone.ui.validateUmkm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.databinding.ActivityValidateUmkmBinding
import com.example.bangkit_capstone.di.Injection
import com.example.bangkit_capstone.ui.ViewModelFactory
import com.example.bangkit_capstone.ui.auth.SignUpViewModel

class ValidateUmkmActivity : AppCompatActivity() {

    private lateinit var binding : ActivityValidateUmkmBinding
    private val districtData = mutableMapOf<String, List<String>>()
    private val urbanVillageData = mutableMapOf<String, List<String>>()

    private lateinit var viewModel: ValidateUmkmViewModel
    private var id : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValidateUmkmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val intent = intent
        val bundle = intent.extras
        val userId = bundle?.getString("id")
        id = userId.toString()

        val loginProviderToken = Injection.loginProvider(this).getToken().value?:""

        Injection.provideApplicationInfoMetadata(this)?.let { bundle ->
            bundle.getString("ENDPOINT_UMKM")?.let {
                viewModel = ViewModelProvider(
                    this,
                    ViewModelFactory.getInstance(this, it, loginProviderToken)
                )[ValidateUmkmViewModel::class.java]
            }
        }

        initSpinner()
        initializeData()

        // Populate city spinner
        val cityAdapter = ArrayAdapter.createFromResource(
            this, R.array.city, android.R.layout.simple_spinner_item
        )
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.city.adapter = cityAdapter

        // Set listener for city spinner
        binding.city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                // Update district spinner based on the selected city
                updateDistrictSpinner(binding.city.selectedItem.toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }

        initSpinner()

        binding.btnValidation.setOnClickListener {
            validate()
        }
    }

    private fun initializeData() {
        // Inisialisasi data kecamatan berdasarkan kota
        districtData["Kota Jakarta Pusat"] = resources.getStringArray(R.array.kecamatan_jakarta_pusat).toList()
        districtData["Kota Jakarta Barat"] = resources.getStringArray(R.array.kecamatan_jakarta_barat).toList()
        districtData["Kota Jakarta Selatan"] = resources.getStringArray(R.array.kecamatan_jakarta_selatan).toList()
        districtData["Kota Jakarta Timur"] = resources.getStringArray(R.array.kecamatan_jakarta_timur).toList()
        districtData["Kota Jakarta Utara"] = resources.getStringArray(R.array.kecamatan_jakarta_utara).toList()
        districtData["Kepulauan Seribu"] = resources.getStringArray(R.array.kecamatan_kepulauan_seribu).toList()

        // Jakarta Pusat
        urbanVillageData["Cempaka Putih"] = resources.getStringArray(R.array.kelurahan_cempaka_putih).toList()
        urbanVillageData["Gambir"] = resources.getStringArray(R.array.kelurahan_gambir).toList()
        urbanVillageData["Johar Baru"] = resources.getStringArray(R.array.kelurahan_johar_baru).toList()
        urbanVillageData["Kemayoran"] = resources.getStringArray(R.array.kelurahan_kemayoran).toList()
        urbanVillageData["Menteng"] = resources.getStringArray(R.array.kelurahan_menteng).toList()
        urbanVillageData["Sawah Besar"] = resources.getStringArray(R.array.kelurahan_sawah_besar).toList()
        urbanVillageData["Senen"] = resources.getStringArray(R.array.kelurahan_senen).toList()
        urbanVillageData["Tanah Abang"] = resources.getStringArray(R.array.kelurahan_tanah_abang).toList()

        // Jakarta Barat
        urbanVillageData["Cengkareng"] = resources.getStringArray(R.array.kelurahan_cengkareng).toList()
        urbanVillageData["Grogol Petamburan"] = resources.getStringArray(R.array.kelurahan_grogol_petamburan).toList()
        urbanVillageData["Taman Sari"] = resources.getStringArray(R.array.kelurahan_taman_sari).toList()
        urbanVillageData["Tambora"] = resources.getStringArray(R.array.kelurahan_tambora).toList()
        urbanVillageData["Kebon Jeruk"] = resources.getStringArray(R.array.kelurahan_kebon_jeruk).toList()
        urbanVillageData["Kalideres"] = resources.getStringArray(R.array.kelurahan_kalideres).toList()
        urbanVillageData["Palmerah"] = resources.getStringArray(R.array.kelurahan_palmerah).toList()
        urbanVillageData["Kembangan"] = resources.getStringArray(R.array.kelurahan_kembangan).toList()

        // Jakarta Selatan
        urbanVillageData["Cilandak"] = resources.getStringArray(R.array.kelurahan_cilandak).toList()
        urbanVillageData["Jagakarsa"] = resources.getStringArray(R.array.kelurahan_jagakarsa).toList()
        urbanVillageData["Kebayoran Baru"] = resources.getStringArray(R.array.kelurahan_kebayoran_baru).toList()
        urbanVillageData["Kebayoran Lama"] = resources.getStringArray(R.array.kelurahan_kebayoran_lama).toList()
        urbanVillageData["Mampang Prapatan"] = resources.getStringArray(R.array.kelurahan_mampang_prapatan).toList()
        urbanVillageData["Pancoran"] = resources.getStringArray(R.array.kelurahan_pancoran).toList()
        urbanVillageData["Pasar Minggu"] = resources.getStringArray(R.array.kelurahan_pasar_minggu).toList()
        urbanVillageData["Pesanggrahan"] = resources.getStringArray(R.array.kelurahan_pesanggrahan).toList()
        urbanVillageData["Setiabudi"] = resources.getStringArray(R.array.kelurahan_setiabudi).toList()
        urbanVillageData["Tebet"] = resources.getStringArray(R.array.kelurahan_tebet).toList()

        // Jakarta Timur
        urbanVillageData["Cakung"] = resources.getStringArray(R.array.kelurahan_cakung).toList()
        urbanVillageData["Cipayung"] = resources.getStringArray(R.array.kelurahan_cipayung).toList()
        urbanVillageData["Ciracas"] = resources.getStringArray(R.array.kelurahan_ciracas).toList()
        urbanVillageData["Duren Sawit"] = resources.getStringArray(R.array.kelurahan_duren_sawit).toList()
        urbanVillageData["Jatinegara"] = resources.getStringArray(R.array.kelurahan_jatinegara).toList()
        urbanVillageData["Kramat Jati"] = resources.getStringArray(R.array.kelurahan_kramat_jati).toList()
        urbanVillageData["Makasar"] = resources.getStringArray(R.array.kelurahan_makasar).toList()
        urbanVillageData["Matraman"] = resources.getStringArray(R.array.kelurahan_matraman).toList()
        urbanVillageData["Pasar Rebo"] = resources.getStringArray(R.array.kelurahan_pasar_rebo).toList()
        urbanVillageData["Pulo Gadung"] = resources.getStringArray(R.array.kelurahan_pulo_gadung).toList()

        // Jakarta Utara
        urbanVillageData["Cilincing"] = resources.getStringArray(R.array.kelurahan_cilincing).toList()
        urbanVillageData["Kelapa Gading"] = resources.getStringArray(R.array.kelurahan_kelapa_gading).toList()
        urbanVillageData["Koja"] = resources.getStringArray(R.array.kelurahan_koja).toList()
        urbanVillageData["Pademangan"] = resources.getStringArray(R.array.kelurahan_pademangan).toList()
        urbanVillageData["Penjaringan"] = resources.getStringArray(R.array.kelurahan_penjaringan).toList()
        urbanVillageData["Tanjung Priok"] = resources.getStringArray(R.array.kelurahan_tanjung_priok).toList()

        // Kepulauan Seribu
        urbanVillageData["Kepulauan Seribu Utara"] = resources.getStringArray(R.array.kelurahan_kepulauan_seribu_utara).toList()
        urbanVillageData["Kepulauan Seribut Selatan"] = resources.getStringArray(R.array.kelurahan_kepulauan_seribu_selatan).toList()
    }


    private fun updateDistrictSpinner(selectedCity: String) {
        // Dapatkan daftar kecamatan berdasarkan kota yang dipilih
        val districts = districtData[selectedCity]

        districts?.let {
            // Perbarui spinner kecamatan
            val districtAdapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, it
            )
            districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.district.adapter = districtAdapter

            // Set listener untuk spinner kecamatan
            binding.district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                    // Perbarui spinner kelurahan berdasarkan kecamatan yang dipilih
                    updateUrbanVillageSpinner(binding.district.selectedItem.toString())
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    // Do nothing here
                }
            }
        }
    }

    private fun updateUrbanVillageSpinner(selectedDistrict: String) {
        // Dapatkan daftar kelurahan berdasarkan kecamatan yang dipilih
        val urbanVillages = urbanVillageData[selectedDistrict]

        urbanVillages?.let {
            // Perbarui spinner kelurahan
            val urbanVillageAdapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, it
            )
            urbanVillageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.urbanVillage.adapter = urbanVillageAdapter
        }
    }

    private fun initSpinner() {
        // Spinner for industry
        ArrayAdapter.createFromResource(
            this,
            R.array.industry,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.industry.adapter = adapter
        }

        // Spinner for target market
        ArrayAdapter.createFromResource(
            this,
            R.array.target_market,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.targetMarket.adapter = adapter
        }
    }

    private fun validate() {
        val umkmName = binding.edBusinessName.text.toString()
        val industry = binding.industry.selectedItem.toString()
        val targetMarket = binding.targetMarket.selectedItem.toString()
        val city = binding.city.selectedItem.toString()
        val district = binding.district.selectedItem.toString()
        val urbanVillage = binding.urbanVillage.selectedItem.toString()
        viewModel.validateUmkm(id, umkmName, industry, targetMarket, city, district, urbanVillage)
        viewModel.message.observe(this) {
            message(it.toString())
        }
    }

    private fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}