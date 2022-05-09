package com.amir.todoapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.amir.todoapp.R
import com.amir.todoapp.data.models.Priority
import com.amir.todoapp.data.models.ToDoData
import com.amir.todoapp.data.viewModel.ToDoViewModel
import com.amir.todoapp.databinding.FragmentUpdateBinding
import com.amir.todoapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        val view = binding.root
        //set menu
        setHasOptionsMenu(true)
        /*
        binding.currentTitleEt.setText(args.currentItem.title)
        binding.currentDescriptionEt.setText(args.currentItem.description)
        binding.currentPrioritiesSpinner.setSelection(mSharedViewModel.parsePriority(args.currentItem.priority))
*/
        //todo spinner item selected listener
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener


        // Inflate the layout for this fragment
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    //update
    private fun updateItem() {
        val title = binding.currentTitleEt.text.toString()
        val description = binding.currentDescriptionEt.text.toString()
        val getPriority = binding.currentPrioritiesSpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            //update
            //start
            val updateItem = ToDoData(
                args.currentItem.id, title, mSharedViewModel.parsePriority(getPriority), description
            )
            mToDoViewModel.updateData(updateItem)

            Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_LONG).show()
            //finish
            //navigate back to list
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Title and Description are not allowed to be empty",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    //delete
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete ${args.currentItem.title}?")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'")

        builder.setPositiveButton("YES") { _, _ ->
            mToDoViewModel.deleteItem(args.currentItem)

            Toast.makeText(
                requireContext(),
                "Successfully removed: '${args.currentItem.title}'",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("NO") { _, _ -> }
        builder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}