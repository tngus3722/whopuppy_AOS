package com.dicelab.whopuppy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicelab.whopuppy.R
import com.dicelab.whopuppy.base.recyclerview.BindingViewHolder
import com.dicelab.whopuppy.data.entity.AbandonedAnimalItem
import com.dicelab.whopuppy.databinding.ItemHomeAbandonedAnimalBinding
import com.dicelab.whopuppy.util.goToAbandonedAnimalDetailActivity

class AbandonAnimalAdapter :
    RecyclerView.Adapter<BindingViewHolder<ItemHomeAbandonedAnimalBinding>>() {
    private var abandonAnimals: ArrayList<AbandonedAnimalItem> = arrayListOf()

    fun updateItem(updateAbandonAnimals: ArrayList<AbandonedAnimalItem>) {
        val diffUtilCallback = AbandonListDiffCallback(abandonAnimals, updateAbandonAnimals)
        val differentItemResult = DiffUtil.calculateDiff(diffUtilCallback)
        abandonAnimals.clear()
        abandonAnimals.addAll(updateAbandonAnimals)
        differentItemResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemHomeAbandonedAnimalBinding> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_abandoned_animal, parent, false)
        return BindingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemHomeAbandonedAnimalBinding>,
        position: Int
    ) {
        val currentItem = abandonAnimals[position]
        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(currentItem.filename)
                .into(animalImageView)
            animalKindTextView.text = currentItem.kindCd
            animalAgeTextView.text = currentItem.age
        }
        holder.itemView.setOnClickListener {
            holder.itemView.context.goToAbandonedAnimalDetailActivity(currentItem.idx)
        }
    }

    override fun onViewRecycled(holder: BindingViewHolder<ItemHomeAbandonedAnimalBinding>) {
        super.onViewRecycled(holder)
        Glide.with(holder.itemView.context).clear(holder.binding.animalImageView)
    }

    override fun getItemCount(): Int = abandonAnimals.size
}
