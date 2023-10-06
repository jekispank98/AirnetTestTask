package com.example.airnettesttask.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.airnettesttask.MainViewModel
import com.example.airnettesttask.R
import com.example.airnettesttask.databinding.FragmentEnterAdressBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnterAddressFragment : Fragment() {
    private lateinit var _binding: FragmentEnterAdressBinding
    private lateinit var streetAdapter: ArrayAdapter<String>
    private lateinit var houseAdapter: ArrayAdapter<String>
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterAdressBinding.inflate(inflater, container, false)
        viewModel.getAllStreets()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.streetsData.observe(viewLifecycleOwner) { listOfStreets ->
            listOfStreets?.let {
                streetAdapter =
                    ArrayAdapter(requireContext(), R.layout.item_spinner, it.map { it.street })
                _binding.chooseStreetEditText.setAdapter(streetAdapter)
            }
        }
        _binding.chooseStreetEditText.apply {
            onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, id ->
                    val selectedStreet = streetAdapter.getItem(position)
                    val streetId = viewModel.getStreetId(selectedStreet)
                    streetId?.let { viewModel.getAllHousesOnStreet(it) }
                }
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.isBlank() == true) {
                        _binding.chooseHouseEditText.text = null
                        setBuildingAndApartmentVisibility(true)
                        _binding.btSend.isActivated = isAllDataFilled()
                    }
                }
            })
        }

        viewModel.housesData.observe(viewLifecycleOwner) { listOfHouses ->
            listOfHouses?.let {
                houseAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_spinner,
                    sortHouseNumbers(it.map { it.house })
                )
                if (listOfHouses.isNotEmpty()) {
                    _binding.chooseHouseEditText.apply {
                        visibility = View.VISIBLE
                        showDropDown()
                        setAdapter(houseAdapter)
                    }
                    setBuildingAndApartmentVisibility(false)
                }
            }
        }

        _binding.chooseHouseEditText.apply {
            onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, id ->
                    setBuildingAndApartmentVisibility(false)
                }
            setOnClickListener {
                showDropDown()
            }
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    _binding.btSend.isActivated = isAllDataFilled()
                }
            })
        }

        _binding.enterApartmentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                _binding.btSend.isActivated = isAllDataFilled()
            }
        })

        _binding.btSend.setOnClickListener {
            if (isAllDataFilled()) {
                val message = getToastMessage()
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getToastMessage(): String {
        var result = ""
        with(_binding) {

            val streetId = viewModel.getStreetId(chooseStreetEditText.text.toString())
            val street = if (streetId == null) chooseStreetEditText.text.toString()
            else "ID улицы - $streetId"

            val house = if (chooseHouseEditText.text.isNotBlank()) "ID дома - ${
                viewModel.getHouseId(chooseHouseEditText.text.toString())
            }, " else "дом - ${enterHouseEditText.text},"
            val building = if (enterBuildingEditText.text?.isNotBlank() == true) {
                " корпус - ${enterBuildingEditText.text},"
            } else ""

            val apartment = enterApartmentEditText.text.toString()

            result = "$street, $house $building квартира - $apartment"
        }

        return result
    }


    private fun isAllDataFilled(): Boolean {
        var filled: Boolean = false
        with(_binding) {
            filled = if (chooseStreetEditText.text.isNotBlank() &&
                enterApartmentEditText.text?.isNotBlank() == true &&
                chooseHouseEditText.text?.isNotBlank() == true
            ) {
                true
            } else chooseStreetEditText.text.isNotBlank() &&
                    enterApartmentEditText.text?.isNotBlank() == true &&
                    enterHouseEditText.text?.isNotBlank() == true
        }
        return filled
    }

    private fun setBuildingAndApartmentVisibility(visible: Boolean) {
        if (visible) {
            _binding.apply {
                chooseHouseEditText.visibility = View.GONE
                enterHouseEditText.visibility = View.VISIBLE
                enterBuildingEditText.visibility = View.VISIBLE
            }
        } else {
            _binding.apply {
                chooseHouseEditText.visibility = View.VISIBLE
                enterHouseEditText.visibility = View.GONE
                enterBuildingEditText.visibility = View.GONE
            }
        }
    }

    private fun sortHouseNumbers(houseNumbers: List<String>): List<String> {
        val regex = "^(\\d+\\D*)|(\\D+\\d+).*".toRegex()

        val sortedHouseNumbers = houseNumbers
            .sortedWith(Comparator { a, b ->
                val numberA = regex.find(a)?.value?.replace("\\D".toRegex(), "")?.toIntOrNull() ?: 0
                val numberB = regex.find(b)?.value?.replace("\\D".toRegex(), "")?.toIntOrNull() ?: 0

                numberA.compareTo(numberB)
            })
        return sortedHouseNumbers
    }
}